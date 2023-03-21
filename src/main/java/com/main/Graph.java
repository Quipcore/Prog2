package com.main;

import java.util.List;
import java.util.NoSuchElementException;

public interface Graph <T> {
    public void add(T node1, T node2);
    public void remove(T node);
    public void connect(T node1, T node2) throws NoSuchElementException,IllegalArgumentException,IllegalStateException;
    public void disconnect(T node1, T node2, String edgeName, int weight) throws NoSuchElementException, IllegalStateException;
    public void setConnectionWeight(Edge<T> edge, int weight) throws NoSuchElementException, IllegalArgumentException;
    public T getNodes();
    public Edge<T> getEdgeFrom(T node) throws NoSuchElementException;
    public Edge<T> getEdgeBetween(T node1, T node2) throws NoSuchElementException;
    public String toString();
    public boolean pathExists(T node1, T node2);
    public List<Edge<T>> getPath (T node1, T node2);
}
