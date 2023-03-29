package main;

import java.util.*;

public class ListGraph<T> implements Graph<T> {

    private Set<Edge<T>> edges = new HashSet<>();
    private Set<T> nodes = new HashSet<>();


    public void add(T node) {
        nodes.add(node);
    }

    @Override
    public void remove(T node) {
        if (!nodes.remove(node)) {
            throw new NoSuchElementException();
        }

        edges.removeIf(edge -> edge.getDestination().equals(node));
    }

    @Override
    public void connect(T node1, T node2, String edgeName, int weight) throws NoSuchElementException, IllegalArgumentException, IllegalStateException {
        if (!nodes.contains(node1) || !nodes.contains(node2)) {
            throw new NoSuchElementException();
        }
        if (weight < 0) {
            throw new IllegalArgumentException();
        }
        if (edgeExists(node1, node2)) {
            throw new IllegalStateException();
        }

        edges.add(new Edge<>(node1, node2, edgeName, weight));
        edges.add(new Edge<>(node2, node1, edgeName, weight));
    }

    private boolean edgeExists(T node1, T node2) {
        return getEdgeBetween(node1, node2) != null;
    }

    @Override
    public void disconnect(T node1, T node2) throws NoSuchElementException, IllegalStateException {
        if (!nodes.contains(node1) || !nodes.contains(node2)) {
            throw new NoSuchElementException();
        }
        if (!edgeExists(node1, node2)) {
            throw new IllegalStateException();
        }

        edges.remove(getEdgeBetween(node1, node2));
        edges.remove(getEdgeBetween(node2, node1));

    }

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) throws NoSuchElementException, IllegalArgumentException {
        if (weight <= 0) {
            throw new IllegalArgumentException();
        }
        if (!nodes.contains(node1) || !nodes.contains(node2) || !edgeExists(node1, node2)) {
            throw new NoSuchElementException();
        }

        getEdgeBetween(node1, node2).setWeight(weight);
        getEdgeBetween(node2, node1).setWeight(weight);
    }

    @Override
    public Set<T> getNodes() {
        return new HashSet<>(nodes);
    }

    @Override
    public Set<Edge<T>> getEdgeFrom(T node) throws NoSuchElementException {
        if (!nodes.contains(node)) {
            throw new NoSuchElementException();
        }

        Set<Edge<T>> nodeEdges = new HashSet<>();
        for (T setNodes : nodes) {
            if (edgeExists(node, setNodes)) {
                nodeEdges.add(getEdgeBetween(node, setNodes));
            }
        }

        return nodeEdges;
    }

    //TODO: Fix this implementation, move away from the default implementation of hascode().
    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) throws NoSuchElementException {
        if (!nodes.contains(node1) || !nodes.contains(node2)) {
            throw new NoSuchElementException();
        }

        Edge<T> tempEdge = new Edge<>(node1, node2, "", -1);
        String tempEdgeNodesHash = tempEdge.toString().split(";")[0];
        for (Edge<T> edge : edges) {
            String edgeNodeHash = edge.toString().split(";")[0];
            if (edgeNodeHash.equals(tempEdgeNodesHash)) {
                return edge;
            }
        }

        return null;
    }

    @Override
    public boolean pathExists(T node1, T node2) {
        if (!nodes.contains(node1) || !nodes.contains(node2)) {
            return false;
        }
        return getPath(node1, node2) != null;
    }


    //Write javadoc to getPath

    /**
     * Link to wikipedia article on Dijkstra's algorithm: shorturl.at/blrFG
     *
     * @param origin
     * @param destination
     * @return
     */
    @Override
    public List<Edge<T>> getPath(T origin, T destination) {
        Map<T, Float> distance = new HashMap<>();
        distance.put(origin, 0f);

        Map<T, T> previous = new HashMap<>();

        PriorityQueue<T> nodeQueue = new PriorityQueue<>(Comparator.comparing(distance::get));

        for (T node : nodes) {
            if (!node.equals(origin)) {
                distance.put(node, Float.POSITIVE_INFINITY);
                previous.put(node, null);
            }

            nodeQueue.add(node);
        }

        while (!nodeQueue.isEmpty()) {
            T u = nodeQueue.poll();
            for (Edge<T> edge : getEdgeFrom(u)) {
                T v = edge.getDestination();
                float alt = distance.get(u) + edge.getWeight();
                if (alt < distance.get(v)) {
                    distance.put(v, alt);
                    previous.put(v, u);
                    nodeQueue.remove(v);
                    nodeQueue.add(v);
                }
            }
            if (u.equals(destination)) {
                break;
            }
        }

        List<Edge<T>> path = new ArrayList<>();
        T current = destination;

        try {
            while (!current.equals(origin)) {
                if (!previous.containsKey(current)) {
                    return null;
                }
                path.add(getEdgeBetween(previous.get(current), current));
                current = previous.get(current);
            }
        } catch (NoSuchElementException e) {
            return null;
        }
        path.add(getEdgeBetween(origin, current));
        Collections.reverse(path);
        return path;
    }
}
