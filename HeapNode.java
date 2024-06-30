public class HeapNode {
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

    // Destructor is not needed in Java as it has garbage collection.
}
