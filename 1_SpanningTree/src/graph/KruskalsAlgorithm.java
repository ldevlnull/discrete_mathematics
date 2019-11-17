package graph;

import graph.Graph;
import lib.Dsu;
import lib.SpanningTreeGenerationAlgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KruskalsAlgorithm implements SpanningTreeGenerationAlgorithm, Dsu {

    @Override
    public Graph createSpanningTreeFromGraph(Graph graph) {
        Graph spanningTree = new Graph("Spanning Tree of "+graph.getName()+" (Kruskal`s Algorithm)");
        graph.sort();
        List<Set<Graph.Vertex>> set = new ArrayList<>();
        Set<Graph.Vertex> vertices = new HashSet<>();
        graph.getEdges().forEach(e -> vertices.addAll(Set.of(e.getVertex1(), e.getVertex2())));
        vertices.forEach(v -> set.add(new HashSet<>(){{ add(v); }}));
        for(Graph.Edge edge: graph.getEdges()){
            Set<Graph.Vertex> comp1 = find(set, edge.getVertex1());
            Set<Graph.Vertex> comp2 = find(set, edge.getVertex2());
            if(!comp1.equals(comp2)) {
                spanningTree.addEdge(edge);
                set.remove(comp1);
                set.remove(comp2);
                set.add(merge(comp1, comp2));
            }
        }
        return spanningTree;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public Set<Graph.Vertex> find(List<Set<Graph.Vertex>> components, Graph.Vertex v) {
        return components.stream().filter(comp -> comp.contains(v)).findFirst().get();
    }

    @Override
    public Set<Graph.Vertex> merge(Set<Graph.Vertex> v1, Set<Graph.Vertex> v2) {
        return new HashSet<>(){{ addAll(v1); addAll(v2); }};
    }

}
