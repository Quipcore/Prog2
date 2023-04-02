package main.graph;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public interface Graph <T> {
    void add(T node);
    void remove(T node);

    void connect(T node1, T node2, String edgeName, int weight) throws NoSuchElementException, IllegalArgumentException, IllegalStateException;

    void disconnect(T node1, T node2) throws NoSuchElementException, IllegalStateException;
    void setConnectionWeight(T node1, T node2, int weight) throws NoSuchElementException, IllegalArgumentException;
    Set<T> getNodes();
    Set<Edge<T>> getEdgeFrom(T node) throws NoSuchElementException;
    Edge<T> getEdgeBetween(T node1, T node2) throws NoSuchElementException;
    String toString();
    boolean pathExists(T node1, T node2);
    List<Edge<T>> getPath (T origin, T destination);
}
