package com.example.prog2;

import java.util.List;
import java.util.NoSuchElementException;

public class ListGraph <T> implements Graph<T> {
    @Override
    public void add(T node1, T node2) {

    }

    @Override
    public void remove(T node) {

    }

    @Override
    public void connect(T node1, T node2) throws NoSuchElementException, IllegalArgumentException, IllegalStateException {

    }

    @Override
    public void disconnect(T node1, T node2, String edgeName, int weight) throws NoSuchElementException, IllegalStateException {

    }

    @Override
    public void setConnectionWeight(Edge<T> edge, int weight) throws NoSuchElementException, IllegalArgumentException {

    }

    @Override
    public T getNodes() {
        return null;
    }

    @Override
    public Edge<T> getEdgeFrom(T node) throws NoSuchElementException {
        return null;
    }

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) throws NoSuchElementException {
        return null;
    }

    @Override
    public boolean pathExists(T node1, T node2) {
        return false;
    }

    @Override
    public List<Edge<T>> getPath(T node1, T node2) {
        return null;
    }
}
