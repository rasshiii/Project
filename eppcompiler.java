import java.util.*;
import java.io.*;

class ExprTreeNode {
    String type;
    int num;
    String id;
    ExprTreeNode left;
    ExprTreeNode right;
}

class Parser {
    SymbolTable symtable;
    List<ExprTreeNode> expr_trees;

    Parser() {
        symtable = new SymbolTable();
        expr_trees = new ArrayList<>();
    }

    void parse(List<String> code) {
        // Implement parse logic
    }
}

class SymbolTable {
    Map<String, Integer> table;

    SymbolTable() {
        table = new HashMap<>();
    }

    int search(String id) {
        return table.getOrDefault(id, -1);
    }

    void insert(String id) {
        table.putIfAbsent(id, -1);
    }

    void assignAddress(String id, int index) {
        table.put(id, index);
    }

    void remove(String id) {
        table.remove(id);
    }
}

class MinHeap {
    PriorityQueue<Integer> heap;

    MinHeap() {
        heap = new PriorityQueue<>();
    }

    void pushHeap(int value) {
        heap.add(value);
    }

    int getMin() {
        return heap.peek();
    }

    void pop() {
        heap.poll();
    }
}

class EPPCompiler {
    int memory_size;
    String output_file;
    Parser targ;
    MinHeap least_mem_loc;

    EPPCompiler() {
    }

    EPPCompiler(String out_file, int mem_limit) {
        memory_size = mem_limit;
        output_file = out_file;
        targ = new Parser();
        least_mem_loc = new MinHeap();
        for (int i = 0; i < memory_size; i++) {
            least_mem_loc.pushHeap(i);
        }
    }

    void compile(List<List<String>> code) {
        int n = code.size();

        for (int i = 0; i < n; i++) {
            List<String> aux = new ArrayList<>();
            aux.add(code.get(i).get(0));
            aux.add(code.get(i).get(1));
            for (int j = 2; j < code.get(i).size(); j++) {
                String element = code.get(i).get(j);
                if (element.length() > 1 && !Character.isDigit(element.charAt(0))) {
                    for (int k = 0; k < element.length(); k++) {
                        aux.add(element.substring(k, k + 1));
                    }
                } else {
                    if (Character.isDigit(element.charAt(0)) && element.charAt(element.length() - 1) == ')') {
                        aux.add(element.substring(0, element.length() - 1));
                        aux.add(element.substring(element.length() - 1));
                    } else {
                        aux.add(element);
                    }
                }
            }

            List<String> gene;
            List<String> current = aux;
            targ.parse(current);
            if (current.get(0).equals("del")) {
                String var = current.get(2);
                int index = targ.symtable.search(var);
                least_mem_loc.pushHeap(index);
                gene = generateTargCommands();
            } else if (current.get(0).equals("ret")) {
                gene = generateTargCommands();
            } else {
                String var = current.get(0);
                targ.symtable.insert(var);
                if (targ.symtable.search(var) == -1) {
                    int index = least_mem_loc.getMin();
                    least_mem_loc.pop();
                    targ.symtable.assignAddress(var, index);
                }
                gene = generateTargCommands();
            }
            writeToFile(gene);
        }
    }

    List<String> generateTargCommands() {
        ExprTreeNode current = targ.expr_trees.get(targ.expr_trees.size() - 1);
        List<String> command = new ArrayList<>();
        if (current.left.type.equals("DEL")) {
            String var = current.right.id;
            int index = targ.symtable.search(var);
            String ok = "DEL = mem[" + index + "]";
            command.add(ok);
            targ.symtable.remove(var);
            return command;
        } else if (current.left.type.equals("RET")) {
            command = generator(current.right, targ);
            command.add("RET = POP");
            return command;
        } else {
            command = generator(current.right, targ);
            String var = current.left.id;
            int index = targ.symtable.search(var);
            String ok = "mem[" + index + "] = POP";
            command.add(ok);
            return command;
        }
    }

    String gen(ExprTreeNode n, Parser p) {
        if (n.type.equals("VAL")) {
            return "PUSH " + n.num;
        } else if (n.type.equals("VAR")) {
            int index = p.symtable.search(n.id);
            return "PUSH mem[" + index + "]";
        } else if (n.type.equals("ADD") || n.type.equals("SUB") || n.type.equals("MUL") || n.type.equals("DIV")) {
            return n.type;
        }
        return null;
    }

    List<String> generator(ExprTreeNode n, Parser p) {
        if (n == null) {
            return new ArrayList<>();
        } else {
            List<String> a, b, c;
            a = generator(n.right, p);
            b = generator(n.left, p);
            c = new ArrayList<>();
            c.addAll(a);
            c.addAll(b);
            c.add(gen(n, p));
            return c;
        }
    }

    void writeToFile(List<String> commands) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output_file, true))) {
            for (String command : commands) {
                writer.write(command);
                writer.newLine();
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
