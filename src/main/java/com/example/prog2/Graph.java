package com.example.prog2;

import java.util.List;
import java.util.NoSuchElementException;

public interface Graph {
    public void add(Node n1, Node n2);
    public void remove(Node node);
    public void connect(Node n1, Node n2) throws NoSuchElementException,IllegalArgumentException,IllegalStateException;
    public void disconnect(Node n1, Node n2, String edgeName, int weight) throws NoSuchElementException, IllegalStateException;
    public void setConnectionWeight(Edge edge, int weight) throws NoSuchElementException, IllegalArgumentException;
    public Node getNodes();
    public Edge getEdgeFrom(Node node) throws NoSuchElementException;
    public Edge getEdgeBetween(Node n1, Node n2) throws NoSuchElementException;
    public String toString();
    public boolean pathExists(Node n1, Node n2);
    public List<Edge> getPath (Node n1, Node n2);
}
