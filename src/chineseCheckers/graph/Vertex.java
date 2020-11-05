package chineseCheckers.graph;

import javafx.scene.shape.Shape;

import java.util.Objects;

public class Vertex  {

    public String label;
    public Vertex(String label) {
        this.label = label;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
