package lib;

import graph.Graph;

import java.util.List;
import java.util.Set;


public interface Dsu {

    Set<Graph.Vertex> find(List<Set<Graph.Vertex>> components, Graph.Vertex v);
    Set<Graph.Vertex> merge(Set<Graph.Vertex> v1, Set<Graph.Vertex> v2);

}
