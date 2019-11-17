import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Heap {

    private final boolean DEBUG_MODE;

    private int size = 0;
    private List<Node> nodes;
    private Node root;

    private class Node implements Comparable, Cloneable {
        private int id = ++size;
        private double val;
        private int row;

        private Node parent;

        private Node(double val) {
            this.val = val;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        private Node(int id, double val, int row, Node parent) {
             this.id = id;
            this.val = val;
            this.row = row;
            this.parent = parent;
        }

        @Override
        protected Node clone() throws CloneNotSupportedException {
            return new Node(id, val, row, parent);
        }

        @Override
        public String toString() {
            Node max = Collections.max(nodes);
            return String.format("%0" + (int) (Math.log10(max.val) + 1) + ".0f", val);
        }

        @Override
        public int compareTo(Object o) {
            Node n = (Node) o;
            return Double.compare(val, n.val);
        }
    }

    public int size() {
        return size;
    }

    public Heap(double rootVal) {
        root = new Node(rootVal);
        nodes = new ArrayList<>() {{
            add(root);
        }};
        DEBUG_MODE = false;
    }

    public Heap(double rootVal, boolean DEBUG_MODE) {
        this.root = new Node(rootVal);
        nodes = new ArrayList<>() {{
            add(root);
        }};
        this.DEBUG_MODE = DEBUG_MODE;
    }

    public Node getRoot() {
        return root;
    }

    public void maxHeapify(double val){
        Node node = nodes.stream()
                .filter(n -> n.val == val)
                .findFirst().orElseThrow(() -> new NoSuchNodeException(val));

        int nodeIndex = nodes.indexOf(node);

        Node []children = (Node[]) nodes.stream().filter(n -> n.parent.equals(node)).toArray();
        int p = nodes.indexOf(children[0]);
        int q = nodes.indexOf(children[1]);
        int largest = 0;
        if(p <= size && nodes.get(p).val > node.val){
            largest = p;
        } else {
            largest = nodeIndex;
        }

        if(q <= size && nodes.get(q).val > nodes.get(largest).val){
            largest = q;
        }

        if(largest != nodeIndex){
            swap(nodes.get(nodeIndex), nodes.get(largest));
            maxHeapify(nodes.get(largest).val);
        }
    }

    public void add(double val) {
        Node node = new Node(val);
        int row = (int) (log2(node.id));
        node.row = row;
        node.parent = nodes.get(calcParent(nodes.size()));
        log("adding " + val + " parent is " + node.parent);
        nodes.add(node);

        while (node.parent != null && node.parent.compareTo(node) < 0) {
            swap(node, node.parent);
            node = node.parent;
        }
        log(this.toString());
    }

    public List<Node> sort(){
        for (int i = size-1; i <= 1; i--) {
            swap(nodes.get(0), nodes.get(i));
            maxHeapify(nodes.get(0).val);
        }
        return nodes;
    }

    private int calcParent(int val) {
        return (val - 1) / 2;
    }

    private void swap(Node parent, Node child) {
        double temp = parent.val;
        parent.val = child.val;
        child.val = temp;
        log(String.format("swap %.0f and %.0f%n", child.val, parent.val));
    }

    private double log2(int x) {
        return (Math.log(x)) / (Math.log(2));
    }

    private long MAX_ROW = 0;

    @Override
    public String toString() {
        MAX_ROW = nodes.get(nodes.size() - 1).row;
        StringBuilder res = new StringBuilder();
        int lastRow = -1;
        for (Node node : nodes) {
            if (Math.abs(lastRow - node.row) > 0) {
                res.append("\n\n");
                res.append(space(s(node.row)));
            }
            res.append(node).append(space(s(node.row - 1)));
            lastRow = node.row;
        }
        return "Heap:\n" + res;
    }

    private String space(long space) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < space * (int) (Math.log10(Collections.max(nodes).val) + 1); i++) {
            str.append(" ");
        }
        return str.toString();
    }

    private long s(long x) {
        log(String.format("%d %d%n", x, (long) (Math.pow(2, MAX_ROW - x) - 1)));
        return (long) (Math.pow(2, MAX_ROW - x) - 1);
    }

    private void log(String s) {
        if (DEBUG_MODE)
            System.out.println(s);
    }
}
