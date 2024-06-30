import java.util.*;

class HeapNode {
    int val;
    HeapNode par;
    HeapNode left;
    HeapNode right;

    public HeapNode() {
    }

    public HeapNode(int _val) {
        val = _val;
        par = null;
        left = null;
        right = null;
    }
}

public class MinHeap {
    HeapNode root;
    int size;

    public MinHeap() {
        root = null;
        size = 0;
    }

    public void pushHeap(int num) {
        if (size != 0) {
            String a = givBinary(size + 1);
            HeapNode create = new HeapNode(num);
            HeapNode current = root;
            if (size > 1) {
                for (int i = 1; i < a.length() - 1; i++) {
                    current = root;
                    if (a.charAt(i) == '1') {
                        current = current.right;
                    } else if (a.charAt(i) == '0') {
                        current = current.left;
                    }
                }
                if (a.charAt(a.length() - 1) == '0') {
                    current.left = create;
                    create.par = current;
                    current = current.left;
                } else {
                    current.right = create;
                    create.par = current;
                    current = current.right;
                }
            } else {
                root.left = create;
                create.par = root;
                current = current.left;
            }
            HeapNode heapify = current;
            while (heapify.par != null) {
                if (heapify.par.val < heapify.val) {
                    break;
                } else {
                    swapSimp(heapify.par, heapify);
                    heapify = heapify.par;
                }
            }
            size++;
        } else {
            HeapNode create = new HeapNode(num);
            root = create;
            size++;
        }
    }

    public int getMin() {
        return root.val;
    }

    public void pop() {
        HeapNode current = root;
        String a = givBinary(size);
        if (size > 1) {
            for (int i = 1; i < a.length(); i++) {
                if (a.charAt(i) == '1') {
                    current = current.right;
                } else if (a.charAt(i) == '0') {
                    current = current.left;
                }
            }
            swapSimp(root, current);
            if (!(current.par == null)) {
                if (current == current.par.right) {
                    current.par.right = null;
                } else {
                    current.par.left = null;
                }
            } else {
                root = null;
            }
            current = root;
            while (!(current.left == null && current.right == null)) {
                if (current.right != null && current.left != null) {
                    if (current.val < current.right.val && current.val < current.left.val) {
                        break;
                    } else if (current.right.val < current.left.val) {
                        swapSimp(current, current.right);
                        current = current.right;
                    } else {
                        swapSimp(current, current.left);
                        current = current.left;
                    }
                } else if (current.left != null) {
                    if (current.val < current.left.val) {
                        break;
                    }
                    swapSimp(current, current.left);
                    current = current.left;
                } else if (current.right != null) {
                    if (current.val < current.right.val) {
                        break;
                    }
                    swapSimp(current, current.right);
                    current = current.right;
                }
            }
        } else {
            root = null;
        }
        size--;
    }

    private void swapSimp(HeapNode i1, HeapNode i2) {
        int temp = i1.val;
        i1.val = i2.val;
        i2.val = temp;
    }

    private String givBinary(int i) {
        String s = "";
        while (i > 0) {
            int rem = i % 2;
            i = i / 2;
            s = Integer.toString(rem) + s;
        }
        return s;
    }

   /* public static void main(String[] args) {
        MinHeap a = new MinHeap();
        a.pushHeap(1);
        a.pushHeap(2);
        a.pushHeap(3);
        a.pushHeap(4);
        a.pushHeap(5);
        System.out.println(a.getMin());
        a.pop();
        System.out.println(a.getMin());
        a.pop();
        System.out.println(a.getMin());
        a.pop();
        System.out.println(a.getMin());
        a.pop();
        System.out.println(a.getMin());
    }*/
}
