package com.main;

public class Edge <T> {

    private int weight;
    private String name;

    private T node1;
    private T node2;

    public T getDestination(){return node2;}
    public int getWeight(){return weight;}
    public void setWeight(int weight){this.weight = weight;}
    public String getName(){return name;}
    public String toString(){return "";}
}
