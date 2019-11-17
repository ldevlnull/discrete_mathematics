import graph.Graph;
import graph.KruskalsAlgorithm;
import graph.PrimsAlgorithm;
import lib.SpanningTreeGenerationAlgorithm;

import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Graph graph;
        SpanningTreeGenerationAlgorithm algorithm;
        try(Scanner sc = new Scanner(System.in)) {
            System.out.println("Do you want to input new graph or use pre-generated? (1/2)");
            if (sc.nextInt() == 1) {
                graph = new Graph("User-Created Graph");
                System.out.println("input three numbers: vertex1 vertex2 and weight. when you finish, type: end");
                sc.nextLine();
                while (true) {
                    String input = sc.nextLine();
                    if (input.equalsIgnoreCase("end"))
                        break;
                    String[] values = input.split(" ");
                    try {
                        graph.addNewEdge(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Double.parseDouble(values[2]));
                    } catch (NumberFormatException e) {
                        System.err.println("Bad input!");
                    }
                }
            } else {
                graph = new Graph("Pre-Generated Graph");
                graph.addNewEdge(1, 2, 6);
                graph.addNewEdge(1, 3, 1);
                graph.addNewEdge(1, 4, 5);
                graph.addNewEdge(2, 3, 5);
                graph.addNewEdge(2, 5, 3);
                graph.addNewEdge(3, 4, 5);
                graph.addNewEdge(3, 5, 6);
                graph.addNewEdge(3, 6, 4);
                graph.addNewEdge(4, 6, 2);
                graph.addNewEdge(5, 6, 6);
            }
            System.out.println("Your graph is ready!\nNow select creational algorithm: Kruskal`s or Prim`s (1/2)");
            algorithm = switch(Integer.parseInt(sc.next())) {
                case 1: yield new KruskalsAlgorithm();
                case 2: yield new PrimsAlgorithm();
                default:
                    System.err.println("Wrong Input! Selected Kruskal`s Algorithm");
                    yield new KruskalsAlgorithm();
            };
        }
        System.out.println(graph.toStringCompact());
        System.out.println(algorithm.createSpanningTreeFromGraph(graph).toStringCompact());
    }
}
