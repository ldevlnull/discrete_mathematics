package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Graph {

    private final String name;
    private int vertexCount;
    private int edgeCount;

    public class Vertex {

        private int id = ++vertexCount;

        public Vertex() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            return id == vertex.id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        Vertex(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "id=" + id +
                    '}';
        }
    }

    protected class Edge implements Comparable{

        private final int id = ++edgeCount;
        private final Vertex vertex1;
        private Vertex vertex2;
        private double weight;

        Edge(Vertex vertex1, Vertex vertex2, double weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        Edge(Vertex vertex1, Vertex vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }

        private Edge(Vertex vertex1) {
            this.vertex1 = vertex1;
        }

        Vertex getVertex1() {
            return vertex1;
        }

        Vertex getVertex2() {
            return vertex2;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "vertex1=" + vertex1 +
                    ", vertex2=" + vertex2 +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            Edge edge = (Edge)o;
            return Double.compare(weight, edge.weight);
        }
    }

    private final List<Edge> edges;

    List<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "edges=" + edges +
                '}';
    }

    public String toStringCompact(){
        StringBuilder str = new StringBuilder();
        edges.forEach(e -> str.append("[").append(e.vertex1.id).append(", ").append(e.vertex2.id).append("]")
                .append(e.weight != 0 ? String.format(" (%.2f)\n", e.weight):"\n"));
        return String.format("%s:%n%s", name != null? name : "Graph", str);
    }

    public Graph(String name) {
        this.name = name;
        this.vertexCount = 0;
        this.edgeCount = 0;
        this.edges = new ArrayList<>();
    }

    private Vertex getOrCreateVertex(int id){
        Edge edge = edges.stream().filter(e -> e.vertex1.id == id || e.vertex2.id == id).findFirst()
                .orElseGet(() -> new Edge(new Vertex(id)));
        return edge.vertex1.id == id? edge.vertex1 : edge.vertex2.id == id ? edge.vertex2 : new Vertex(id);
    }

    public Edge addNewEdge(int id1, int id2){
        Vertex v1 = getOrCreateVertex(id1);
        Vertex v2 = getOrCreateVertex(id2);
        Edge edge = new Edge(v1, v2);
        edges.add(edge);
        return edge;
    }

    public void addNewEdge(int id1, int id2, double weight){
        Vertex v1 = getOrCreateVertex(id1);
        Vertex v2 = getOrCreateVertex(id2);
        Edge edge = new Edge(v1, v2, weight);
        edges.add(edge);
    }

    void sort(){
        edges.sort(Comparator.comparingDouble(e -> e.weight));
    }

    void addEdge(Edge edge){
        edges.add(edge);
    }

    String getName() {
        return name;
    }
}
