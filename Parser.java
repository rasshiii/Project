import java.util.*;

public class Parser {
    SymbolTable symtable;
    List<ExprTreeNode> expr_trees;

    public Parser() {
        symtable = new SymbolTable();
        expr_trees = new ArrayList<>();
    }

    public void parse(List<String> code) {
        String a = code.get(1);
        ExprTreeNode root = new ExprTreeNode();
        root.type = "VAR";
        root.id = a;
        root.left = null;
        root.right = null;
        ExprTreeNode l = new ExprTreeNode();

        if (code.get(0).equals("del")) {
            l.type = "DEL";
            l.id = "DEL";
            root.left = l;
            ExprTreeNode r = new ExprTreeNode();
            r.type = "VAR";
            r.id = code.get(2);
            root.right = r;
        } else if (code.get(0).equals("ret")) {
            l.type = "RET";
            l.id = "RET";
            root.left = l;
            List<String> treeable = code.subList(2, code.size());
            root.right = maketree(treeable);
        } else {
            l.type = "VAR";
            l.id = code.get(0);
            root.left = l;
            List<String> treeable = code.subList(2, code.size());
            root.right = maketree(treeable);
        }

        expr_trees.add(root);
    }

    private ExprTreeNode maketree(List<String> token) {
        if (token.size() == 1) {
            String a = token.get(0);
            if (a.charAt(0) <= '9') {
                int v = Integer.parseInt(token.get(0));
                ExprTreeNode rot = new ExprTreeNode("VAL", v);
                return rot;
            } else {
                ExprTreeNode rot = new ExprTreeNode();
                rot.type = "VAR";
                rot.id = a;
                return rot;
            }
        } else {
            int open = 0;
            boolean done = false;
            for (int i = 0; i < token.size(); i++) {
                if (done) {
                    break;
                } else if (token.get(i).equals("(")) {
                    open++;
                } else if (token.get(i).equals(")")) {
                    open--;
                }
                if (open == 1 && (token.get(i).equals("+") || token.get(i).equals("-") || token.get(i).equals("*") || token.get(i).equals("/"))) {
                    done = true;
                    ExprTreeNode rot = new ExprTreeNode();
                    if (token.get(i).equals("+")) {
                        rot.type = "ADD";
                    } else if (token.get(i).equals("-")) {
                        rot.type = "SUB";
                    } else if (token.get(i).equals("*")) {
                        rot.type = "MUL";
                    } else {
                        rot.type = "DIV";
                    }
                    List<String> v = new ArrayList<>(token.subList(1, i));
                    List<String> v2 = new ArrayList<>(token.subList(i + 1, token.size() - 1));
                    rot.left = maketree(v);
                    rot.right = maketree(v2);
                    return rot;
                }
            }
        }
        return null; // In case there's no valid token, though ideally it should not reach here in a valid case.
    }

    public void generateTargetCode() {
        // Implement target code generation logic here if needed.
    }
}
