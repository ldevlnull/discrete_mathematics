package graph;

import lib.SpanningTreeGenerationAlgorithm;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PrimsAlgorithm implements SpanningTreeGenerationAlgorithm {

    @Override
    public Graph createSpanningTreeFromGraph(Graph graph) {
        Graph spanningTree = new Graph("Spanning Tree of " + graph.getName() + " (Prim's Algorithm)");
        Set<Graph.Vertex> selectedVertices = new HashSet<>();
        Set<Graph.Edge> selectedEdges = new LinkedHashSet<>();
        selectedVertices.add(graph.getEdges().get(0).getVertex1());

        for (int i = 0; i < graph.getEdges().size(); i++) {
            Graph.Edge minEdge = edgesWithVertex(graph.getEdges(), selectedVertices).stream()
                    .sorted().findFirst().get();
            selectedVertices.addAll(Set.of(minEdge.getVertex1(), minEdge.getVertex2()));
            selectedEdges.add(minEdge);
            graph.getEdges().remove(minEdge);
        }
        selectedEdges.forEach(spanningTree::addEdge);
        return spanningTree;
    }

    private Set<Graph.Edge> edgesWithVertex(List<Graph.Edge> edges, Set<Graph.Vertex> vertices) {
        return new HashSet<>(){{
            for (Graph.Edge edge : edges)
                for (Graph.Vertex vertex : vertices)
                    if (edge.getVertex1().equals(vertex) || edge.getVertex2().equals(vertex))
                        add(edge);
        }};
    }
}
