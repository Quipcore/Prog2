package com.main;

import java.util.List;
import java.util.NoSuchElementException;

public interface Graph <T> {
    void add(T node1, T node2);
    void remove(T node);
    void connect(T node1, T node2) throws NoSuchElementException,IllegalArgumentException,IllegalStateException;
    void disconnect(T node1, T node2, String edgeName, int weight) throws NoSuchElementException, IllegalStateException;
    void setConnectionWeight(Edge<T> edge, int weight) throws NoSuchElementException, IllegalArgumentException;
    T getNodes();
    Edge<T> getEdgeFrom(T node) throws NoSuchElementException;
    Edge<T> getEdgeBetween(T node1, T node2) throws NoSuchElementException;
    String toString();
    boolean pathExists(T node1, T node2);
    List<Edge<T>> getPath (T node1, T node2);
}
