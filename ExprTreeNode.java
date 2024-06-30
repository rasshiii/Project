public class ExprTreeNode {
    String type;
    int num;
    String id; // This was used in the previous Java conversion for the gen method.
    ExprTreeNode left;
    ExprTreeNode right;

    public ExprTreeNode() {
    }

    public ExprTreeNode(String t, int v) {
        type = t;
        if (t.equals("VAL")) {
            num = v;
        }
    }

    // Destructor is not needed in Java as it has garbage collection.
}
