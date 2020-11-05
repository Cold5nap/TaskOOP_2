package chineseCheckers.graph;

import java.util.*;

public class Graph {

    private HashMap<Vertex, List<Vertex>> adjVerticesMap;

    public Graph() {
        adjVerticesMap = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        adjVerticesMap.putIfAbsent(v, null);
    }

    public void removeVertex(Vertex v) {
        adjVerticesMap.values().stream().forEach(e -> e.remove(v));
        adjVerticesMap.remove(v);
    }

    /**
     * Return TRUE if vertex have an adjacent
     *
     * @param v1 - vertex 1
     * @param v2 - vertex 2
     * @return true if adjacent
     */
    public boolean addAdj(Vertex v1, Vertex v2) {
        boolean isAdded = false;
        if (v1.equals(v2)) {
            return false;
        }
        if (adjVerticesMap.containsKey(v1)) {
            addVertex(v1);
        }
        if (adjVerticesMap.containsKey(v2)) {
            addVertex(v2);
        }

        //для первой вершины заполняем связи
        if (adjVerticesMap.get(v1) == null) {
            List<Vertex> l = new ArrayList<>();
            l.add(v2);
            adjVerticesMap.putIfAbsent(v1, l);
            isAdded = true;
        } else {
            if (!adjVerticesMap.get(v1).contains(v2)) {
                isAdded = true;
                adjVerticesMap.get(v1).add(v2);
            }
        }
        //заполняем связи для второй вершины
        if (adjVerticesMap.get(v2) == null) {
            List<Vertex> l = new ArrayList<>();
            l.add(v1);
            adjVerticesMap.putIfAbsent(v2, l);
            isAdded = true;
        } else {
            if (!adjVerticesMap.get(v2).contains(v1)) {
                isAdded = true;
                adjVerticesMap.get(v2).add(v1);
            }
        }
        return isAdded;
    }

    //Remove adjacent between vertices
    public boolean removeAdj(Vertex v1, Vertex v2) {
        if (adjVerticesMap.get(v1) != null && adjVerticesMap.get(v2) != null) {
            adjVerticesMap.get(v1).remove(v2);
            adjVerticesMap.get(v2).remove(v1);
            return true;
        } else {
            return false;
        }
    }

    public boolean isAdjVertices(Vertex v, Vertex adjV) {
        return adjVerticesMap.get(v).contains(adjV);
    }

    public List<Vertex> adjVertices(Vertex v) {
        return adjVerticesMap.get(v);
    }

    /**
     * Return adjacent vertices of given vertex.
     * TYPE List<Vertex>
     *
     * @param v -vertex
     * @return adjVertices
     */
    public List<Vertex> getAdjVertices(Vertex v) {
        return adjVerticesMap.get(v);
    }

    public void printGraph() {
        for (Map.Entry entry : adjVerticesMap.entrySet()) {
            Vertex v = (Vertex) entry.getKey();
            System.out.println(v.label);
            List<Vertex> l = (List<Vertex>) entry.getValue();
            for (Vertex vertex : l) {
                System.out.println(" Value: " + vertex.label);
            }
        }
    }

    /*
    //Обход в глубину начинается с произвольной корневой вершины и
    // исследует вершины как можно глубже вдоль каждой ветви,
    // прежде чем исследовать вершины на том же уровне .
    public Set<String> depthFirstTraversal(Graph graph, String root) {
        Set<String> visited = new LinkedHashSet<>();
        Stack<String> stack = new Stack<>();
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
    public Set<String> breadthFirstTraversal(Graph graph, String root) {
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

     */



}
