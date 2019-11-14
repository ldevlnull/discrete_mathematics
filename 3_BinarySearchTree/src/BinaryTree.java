import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BinaryTree {

    private class Node implements Comparable<Node> {
        private int value;
        private Node left;
        private Node right;

        private Node(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(Node n) {
            return Integer.compare(this.value, n.value);
        }

    }

    private Node root;

    public BinaryTree() {
    }

    public BinaryTree(int root) {
        this.put(root);
    }

    public BinaryTree(int ... values) {
        IntStream.of(values).forEach(this::put);
    }

    public void put(int value) {
        Node node = new Node(value);
        if (root == null) {
            root = node;
            return;
        }

        Node current = root;
        for (;;) {
            if (current.compareTo(node) == 0) {
              return;
            } else if (current.compareTo(node) < 0) {
                if (current.right == null) {
                    current.right = node;
                    break;
                } else {
                    current = current.right;
                }
            } else {
                if(current.left == null){
                    current.left = node;
                    break;
                } else {
                    current = current.left;
                }
            }
        }
    }

    public int min() throws ArithmeticException{
        if(root == null){
            throw new ArithmeticException("No root element!");
        }
        Node current = root;
        while (current.left != null){
            current = current.left;
        }
        return current.value;
    }

    public int max() throws ArithmeticException{
        if(root == null){
            throw new ArithmeticException("No root element!");
        }
        Node current = root;
        while (current.right != null){
            current = current.right;
        }
        return current.value;
    }

    public void sort()  {
        inorderRec(root);
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.value);
            inorderRec(root.right);
        }
    }
}
