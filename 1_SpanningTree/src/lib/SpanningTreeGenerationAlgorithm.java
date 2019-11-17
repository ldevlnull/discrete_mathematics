package lib;

import graph.Graph;

@FunctionalInterface
public interface SpanningTreeGenerationAlgorithm {

    Graph createSpanningTreeFromGraph(Graph graph);
}
