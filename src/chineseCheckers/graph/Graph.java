package chineseCheckers.graph;

import java.util.*;

public class Graph {
    private Map<Vertex, List<Vertex>> adjVertices;

    public Graph(Map<Vertex, List<Vertex>> adjVertices) {
        this.adjVertices = adjVertices;
    }
    // standard constructor, getters, setters

    //добавить вершину
    void  addVertex(String label) {
        adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());
    }
    //удалить вершину
    void removeVertex(String label) {
        Vertex v = new Vertex(label);
        adjVertices.values().stream().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(label));
    }
    //добавить ребро
    void addEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);

        adjVertices.get(v2).add(v1);
    }
    //Удалить ребро
    void removeEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);
        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);
    }

    //пример
    Graph createGraph() {
        Graph graph = new Graph(adjVertices);
        graph.addVertex("Bob");
        graph.addVertex("Alice");
        graph.addVertex("Mark");
        graph.addVertex("Rob");
        graph.addVertex("Maria");
        graph.addEdge("Bob", "Alice");
        graph.addEdge("Bob", "Rob");
        graph.addEdge("Alice", "Mark");
        graph.addEdge("Rob", "Mark");
        graph.addEdge("Alice", "Maria");
        graph.addEdge("Rob", "Maria");
        return graph;
    }
    //метод получения смежных вершин конкретной вершины
    List<Vertex> getAdjVertices(String label) {
        return adjVertices.get(new Vertex(label));
    }
    //Обход в глубину начинается с произвольной корневой вершины и
    // исследует вершины как можно глубже вдоль каждой ветви,
    // прежде чем исследовать вершины на том же уровне .
    Set<String> depthFirstTraversal(Graph graph, String root) {
        Set<String> visited = new LinkedHashSet<String>();
        Stack<String> stack = new Stack<String>();
        stack.push(root);
        while (!stack.isEmpty()) {
            String vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Vertex v : graph.getAdjVertices(vertex)) {
                    stack.push(v.label);
                }
            }
        }
        return visited;
    }
        //Для сравнения, обход в ширину начинается с произвольной корневой вершины
        // и исследует все соседние вершины на том же уровне,
        // прежде чем углубиться в граф.
        Set<String> breadthFirstTraversal(Graph graph, String root) {
            Set<String> visited = new LinkedHashSet<String>();
            Queue<String> queue = new LinkedList<String>();
            queue.add(root);
            visited.add(root);
            while (!queue.isEmpty()) {
                String vertex = queue.poll();
                for (Vertex v : graph.getAdjVertices(vertex)) {
                    if (!visited.contains(v.label)) {
                        visited.add(v.label);
                        queue.add(v.label);
                    }
                }
            }
            return visited;
        }

}
