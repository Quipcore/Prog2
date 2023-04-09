package main.graph;

public class Edge<T> {

    private int weight;
    private String name;

    private T fromNode;
    private T toNode;

    public Edge(T fromNode, T toNode, String edgeName, int weight) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.name = edgeName;
        setWeight(weight);
    }

    public T getDestination() {
        return toNode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return String.format("%s;%s;%s;%d", fromNode, toNode, name, weight);
    }
}
