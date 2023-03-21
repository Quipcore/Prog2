package com.example.prog2;

import java.util.List;
import java.util.NoSuchElementException;

public class ListGraph implements Graph {


    @Override
    public void add(Node n1, Node n2) {

    }

    @Override
    public void remove(Node node) {

    }

    @Override
    public void connect(Node n1, Node n2) throws NoSuchElementException, IllegalArgumentException, IllegalStateException {

    }

    @Override
    public void disconnect(Node n1, Node n2, String edgeName, int weight) throws NoSuchElementException, IllegalStateException {

    }

    @Override
    public void setConnectionWeight(Edge edge, int weight) throws NoSuchElementException, IllegalArgumentException {

    }

    @Override
    public Node getNodes() {
        return null;
    }

    @Override
    public Edge getEdgeFrom(Node node) throws NoSuchElementException {
        return null;
    }

    @Override
    public Edge getEdgeBetween(Node n1, Node n2) throws NoSuchElementException {
        return null;
    }

    @Override
    public boolean pathExists(Node n1, Node n2) {
        return false;
    }

    @Override
    public List<Edge> getPath(Node n1, Node n2) {
        return null;
    }
}
