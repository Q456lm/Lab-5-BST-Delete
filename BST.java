public class BST<E extends Comparable<E>> implements Tree<E> {

    // ── Inner node class ──────────────────────────────────────────────────
    protected static class TreeNode<E> {
        E element;
        TreeNode<E> left;
        TreeNode<E> right;

        TreeNode(E e) {
            element = e;
            left    = null;
            right   = null;
        }
    }

    // ── Fields ────────────────────────────────────────────────────────────
    protected TreeNode<E> root;
    protected int size;

    // ── Constructor ───────────────────────────────────────────────────────
    public BST() {
        root = null;
        size = 0;
    }

    // ── Search ────────────────────────────────────────────────────────────
    @Override
    public boolean search(E e) {
        // Follow the invariant from root.
        // Return false when current becomes null (fell off the tree).
        TreeNode<E> current = root;
        while (current != null){
            int cmp = e.compareTo(current.element);
            if (cmp < 0) current = current.left;
            else if (cmp > 0) current = current.right;
            else return true;
        }
        return false;
    }

    // ── Insert ────────────────────────────────────────────────────────────
    @Override
    public boolean insert(E e) {
        if (root == null){
            root = new TreeNode<>(e);
            size++;
            return true;
        }
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null){
            int cmp = e.compareTo(current.element);
            if (cmp < 0) { parent = current; current = current.left; }
            else if (cmp > 0) { parent = current; current = current.right; }
            else return false;
        }
        if (e.compareTo(parent.element) < 0)
            parent.left = new TreeNode<>(e);
        else{
            parent.right = new TreeNode<>(e);
        }
        size++;
        return true;
    }

    // ── Delete ────────────────────────────────────────────────────────────
    @Override
    public boolean delete(E e) {
        // Step 1: find the node -- same path as search, tracking parent
        TreeNode<E> parent  = null;
        TreeNode<E> current = root;
        TreeNode<E> superParent;

        while (current != null) {
            int cmp = e.compareTo(current.element);
            if      (cmp < 0) { parent = current; current = current.left; }
            else if (cmp > 0) { parent = current; current = current.right; }
            else break; // found
        }

        if (current == null) return false; // not found

        // Step 2: determine which case applies and handle it
        if (current.left == null && current.right == null){
            if (parent.left == current) {
                parent.left = null;
            }else {
                parent.right = null;
            }
        }

        else if (current.left == null || current.right == null){
            if (current.left == null){
                System.out.println("left");
                current.element = current.right.element;
                current.right = null;
            }else{
                current.element = current.left.element;
                current.left = null;
            }
        }

        else{
            TreeNode<E> successor;
            TreeNode<E> successor2;
            successor = current.right;
            successor2 = current;
            boolean done = false;
            while (successor.left != null){
                successor = successor.left;
                successor2 = successor;
                done = true;
            }
            current.element = successor.element;
            if (done){  
                successor2.left = null;
            }else{
                successor2.right = null;
            }
        }

        size--;
        return true;
    }

    // ── Inorder traversal ─────────────────────────────────────────────────
    @Override
    public void inorder() {
        inorder(root);
    }

    private void inorder(TreeNode<E> node) {
        if (node == null) return;
        inorder(node.left);
        System.out.print(node.element + " ");
        inorder(node.right);
    }

    // ── Preorder traversal ────────────────────────────────────────────────
    @Override
    public void preorder() {
        preorder(root);
    }

    private void preorder(TreeNode<E> node) {
        if (node == null) return;
        System.out.print(node.element + " ");
        preorder(node.left);
        preorder(node.right);
    }

    // ── Postorder traversal ───────────────────────────────────────────────
    @Override
    public void postorder() {
        postorder(root);
    }

    private void postorder(TreeNode<E> node) {
        if (node == null) return;
        postorder(node.left);
        postorder(node.right);
        System.out.print(node.element + " ");
    }

    // ── Size and empty ────────────────────────────────────────────────────
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // ── Test driver ───────────────────────────────────────────────────────
    public static void main(String[] args) {
        BST<Integer> tree = new BST<>();

        // Insert
        tree.insert(50);
        tree.insert(25);
        tree.insert(75);
        tree.insert(10);
        tree.insert(30);
        tree.insert(60);
        tree.insert(90);

        // Traversals -- predict the output before running
        System.out.print("Inorder:   "); tree.inorder();   System.out.println();
        System.out.print("Preorder:  "); tree.preorder();  System.out.println();
        System.out.print("Postorder: "); tree.postorder(); System.out.println();

        // Search
        System.out.println("Search 30: " + tree.search(30));  // true
        System.out.println("Search 40: " + tree.search(40));  // false

        // Delete leaf
        tree.delete(30);
        System.out.print("After delete 30: "); tree.inorder(); System.out.println();

        // Delete node with one child
        tree.delete(25);
        System.out.print("After delete 25: "); tree.inorder(); System.out.println();

        // Delete node with two children
        tree.delete(75);
        System.out.print("After delete 75: "); tree.inorder(); System.out.println();

        // Size
        System.out.println("Size: " + tree.getSize());  // 4
    }
}
