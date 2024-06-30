public class SymbolTable {
    private SymNode root;
    private int size;

    public SymbolTable() {
        root = null;
        size = 0;
    }

    private int heightcalc(SymNode n) {
        if (n.left == null && n.right == null) {
            return 0;
        } else if (n.left == null) {
            return 1 + heightcalc(n.right);
        } else if (n.right == null) {
            return 1 + heightcalc(n.left);
        } else {
            int a = heightcalc(n.left);
            int b = heightcalc(n.right);
            return 1 + Math.max(a, b);
        }
    }

    private int balanceFactor(SymNode n) {
        if (heightcalc(n) == 0) {
            return 0;
        } else if (n.left == null) {
            return -1 * heightcalc(n);
        } else if (n.right == null) {
            return heightcalc(n);
        } else {
            int lheight = 1 + heightcalc(n.left);
            int rheight = 1 + heightcalc(n.right);
            return lheight - rheight;
        }
    }

    public void insert(String k) {
        if (root == null) {
            SymNode a = new SymNode(k);
            root = a;
            a.par = null;
            a.height = 0;
            size++;
        } else {
            SymNode current = root;
            SymNode a = new SymNode(k);
            a.height = 0;
            boolean done = false;
            while (current != null && !done) {
                a.height += 1;
                if (current.key.compareTo(k) < 0) {
                    if (current.right == null) {
                        current.right = a;
                        a.par = current;
                        updateHeightAndBalance(a);
                        done = true;
                        size++;
                    } else {
                        current = current.right;
                    }
                } else if (current.key.compareTo(k) > 0) {
                    if (current.left == null) {
                        current.left = a;
                        a.par = current;
                        updateHeightAndBalance(a);
                        done = true;
                        size++;
                    } else {
                        current = current.left;
                    }
                } else {
                    break;
                }
            }
        }
    }

    private void updateHeightAndBalance(SymNode a) {
        SymNode change = a;
        change.height = heightcalc(change);
        while (change.par != null) {
            change = change.par;
            change.height = heightcalc(change);
        }
        SymNode rotate = a;
        while (balanceFactor(rotate) <= 1 && balanceFactor(rotate) >= -1) {
            if (rotate == root) {
                break;
            }
            rotate = rotate.par;
        }
        if (balanceFactor(rotate) >= 2) {
            SymNode beta = rotate.left;
            if (balanceFactor(beta) >= 0) {
                root = rotate.LeftLeftRotation();
            } else {
                root = rotate.LeftRightRotation();
            }
        } else if (balanceFactor(rotate) <= -2) {
            SymNode beta = rotate.right;
            if (balanceFactor(beta) <= 0) {
                root = rotate.RightRightRotation();
            } else {
                root = rotate.RightLeftRotation();
            }
        }
    }

    public void remove(String k) {
        if (k.equals(root.key)) {
            if (root.left == null && root.right == null) {
                root = null;
            } else if (root.left == null) {
                root.right.par = null;
                root = root.right;
            } else if (root.right == null) {
                root.par.left = null;
                root = root.left;
            } else {
                SymNode node = root.right;
                while (node.left != null) {
                    node = node.left;
                }
                String temps = node.key;
                int tempv = node.address;
                remove(node.key);
                root.key = temps;
                root.address = tempv;
            }
        } else {
            boolean l;
            SymNode current;
            if (k.compareTo(root.key) < 0) {
                current = root.left;
                l = true;
            } else {
                current = root.right;
                l = false;
            }
            while (!current.key.equals(k)) {
                if (current.key.compareTo(k) < 0) {
                    current = current.right;
                    l = false;
                } else {
                    current = current.left;
                    l = true;
                }
            }
            removeNode(current, l);
        }
        size--;
    }

    private void removeNode(SymNode current, boolean l) {
        if (current.left == null && current.right == null) {
            if (l) {
                current.par.left = null;
            } else {
                current.par.right = null;
            }
        } else if (current.left == null) {
            if (l) {
                current.par.left = current.right;
                current.right.par = current.par;
            } else {
                current.par.right = current.right;
                current.right.par = current.par;
            }
        } else if (current.right == null) {
            if (l) {
                current.par.left = current.left;
                current.left.par = current.par;
            } else {
                current.par.right = current.left;
                current.left.par = current.par;
            }
        } else {
            SymNode replace = current;
            current = current.right;
            while (current.left != null) {
                current = current.left;
            }
            String temps = current.key;
            int tempv = current.address;
            remove(current.key);
            replace.key = temps;
            replace.address = tempv;
        }
        updateHeightAndBalance(current.par);
    }

    public int search(String k) {
        SymNode current = root;
        while (!current.key.equals(k)) {
            if (current.key.compareTo(k) < 0) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return current.address;
    }

    public void assignAddress(String k, int idx) {
        SymNode current = root;
        while (!current.key.equals(k)) {
            if (current.key.compareTo(k) > 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        current.address = idx;
    }

    public int getSize() {
        return size;
    }

    public SymNode getRoot() {
        return root;
    }

    public void finalize() {
        deleteNode(root);
    }

    private void deleteNode(SymNode node) {
        if (node != null) {
            deleteNode(node.left);
            deleteNode(node.right);
            node = null;
        }
    }
}

class SymNode {
    String key;
    int address;
    SymNode left, right, par;
    int height;

    public SymNode(String k) {
        key = k;
        address = 0;
        left = right = par = null;
        height = 0;
    }

    public SymNode LeftLeftRotation() {
        SymNode newRoot = this.left;
        this.left = newRoot.right;
        if (newRoot.right != null) {
            newRoot.right.par = this;
        }
        newRoot.right = this;
        newRoot.par = this.par;
        this.par = newRoot;
        if (newRoot.par != null) {
            if (newRoot.par.left == this) {
                newRoot.par.left = newRoot;
            } else {
                newRoot.par.right = newRoot;
            }
        }
        this.height = heightcalc(this);
        newRoot.height = heightcalc(newRoot);
        return newRoot;
    }

    public SymNode LeftRightRotation() {
        this.left = this.left.RightRightRotation();
        return this.LeftLeftRotation();
    }

    public SymNode RightRightRotation() {
        SymNode newRoot = this.right;
        this.right = newRoot.left;
        if (newRoot.left != null) {
            newRoot.left.par = this;
        }
        newRoot.left = this;
        newRoot.par = this.par;
        this.par = newRoot;
        if (newRoot.par != null) {
            if (newRoot.par.left == this) {
                newRoot.par.left = newRoot;
            } else {
                newRoot.par.right = newRoot;
            }
        }
        this.height = heightcalc(this);
        newRoot.height = heightcalc(newRoot);
        return newRoot;
    }

    public SymNode RightLeftRotation() {
        this.right = this.right.LeftLeftRotation();
        return this.RightRightRotation();
    }

    private int heightcalc(SymNode n) {
        if (n.left == null && n.right == null) {
            return 0;
        } else if (n.left == null) {
            return 1 + heightcalc(n.right);
        } else if (n.right == null) {
            return 1 + heightcalc(n.left);
        } else {
            int a = heightcalc(n.left);
            int b = heightcalc(n.right);
            return 1 + Math.max(a, b);
        }
    }
}
