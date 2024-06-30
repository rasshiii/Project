public class SymNode {
    String key;
    SymNode left;
    SymNode right;
    SymNode par;
    int height;

    public SymNode() {
        this.left = null;
        this.right = null;
        this.par = null;
    }

    public SymNode(String k) {
        this.key = k;
        this.left = null;
        this.right = null;
        this.par = null;
    }

    public static int heightcal(SymNode n) {
        if (n.left == null && n.right == null) {
            return 0;
        } else if (n.left == null) {
            return 1 + heightcal(n.right);
        } else if (n.right == null) {
            return 1 + heightcal(n.left);
        } else {
            int a = heightcal(n.left);
            int b = heightcal(n.right);
            if (a >= b) {
                return 1 + a;
            } else {
                return 1 + b;
            }
        }
    }

    public static void setHeight(SymNode n) {
        SymNode change = n;
        change.height = heightcal(change);
        while (change.par != null) {
            change = change.par;
            change.height = heightcal(change);
        }
    }

    public SymNode leftLeftRotation() {
        SymNode curr = this.left;
        if (this.par != null && this.par.left == this) {
            this.par.left = curr;
        } else if (this.par != null && this.par.right == this) {
            this.par.right = curr;
        }
        SymNode r = curr.right;
        curr.right = this;
        curr.par = this.par;
        this.par = curr;
        this.left = r;
        if (r != null) {
            r.par = this;
        }
        setHeight(this);
        setHeight(curr);
        SymNode root = this;
        while (root.par != null) {
            root = root.par;
        }
        return root;
    }

    public SymNode rightRightRotation() {
        SymNode curr = this.right;
        if (this.par != null && this.par.left == this) {
            this.par.left = curr;
        } else if (this.par != null && this.par.right == this) {
            this.par.right = curr;
        }
        SymNode l = curr.left;
        curr.left = this;
        curr.par = this.par;
        this.par = curr;
        this.right = l;
        if (l != null) {
            l.par = this;
        }
        setHeight(this);
        setHeight(curr);
        SymNode root = this;
        while (root.par != null) {
            root = root.par;
        }
        return root;
    }

    public SymNode leftRightRotation() {
        SymNode currl = this.left;
        SymNode curr = currl.right;
        if (this.par != null && this.par.left == this) {
            this.par.left = curr;
        } else if (this.par != null && this.par.right == this) {
            this.par.right = curr;
        }
        SymNode l = curr.left;
        SymNode r = curr.right;
        curr.par = this.par;
        curr.left = currl;
        curr.right = this;
        currl.par = curr;
        this.par = curr;
        currl.right = l;
        this.left = r;
        if (r != null) {
            r.par = this;
        }
        if (l != null) {
            l.par = currl;
        }
        setHeight(this);
        setHeight(currl);
        setHeight(curr);
        SymNode root = this;
        while (root.par != null) {
            root = root.par;
        }
        return root;
    }

    public SymNode rightLeftRotation() {
        SymNode currr = this.right;
        SymNode curr = currr.left;
        if (this.par != null && this.par.left == this) {
            this.par.left = curr;
        } else if (this.par != null && this.par.right == this) {
            this.par.right = curr;
        }
        SymNode l = curr.left;
        SymNode r = curr.right;
        curr.par = this.par;
        curr.right = currr;
        curr.left = this;
        currr.par = curr;
        this.par = curr;
        currr.left = r;
        this.right = l;
        if (r != null) {
            r.par = currr;
        }
        if (l != null) {
            l.par = this;
        }
        setHeight(this);
        setHeight(currr);
        setHeight(curr);
        SymNode root = this;
        while (root.par != null) {
            root = root.par;
        }
        return root;
    }

    @Override
    protected void finalize() throws Throwable {
        // Cleanup code if necessary
    }
}
