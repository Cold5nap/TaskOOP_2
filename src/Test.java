import chineseCheckers.graph.*;

public class Test {
    public static void main(String[] args) {
        start();

    }

    static void start() {
        Graph g = new Graph();
        Vertex v1= new Vertex("1key"),v2= new Vertex("2key"),v3= new Vertex("3key");
        g.addAdj(v1,v2);
        g.addAdj(v1,v3);
        g.addAdj(v3,v1);
        g.addAdj(v3,v3);
        g.addAdj(v3,v2);
        g.addAdj(v3,v2);
        g.addAdj(v2,v2);
        g.removeAdj(v1, v2);
        g.printGraph();
    }
}
