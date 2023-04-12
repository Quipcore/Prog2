package main.graph;

import java.util.*;

public class ListGraph<T> implements Graph<T> {

    private Map<T,Set<Edge<T>>> nodes = new HashMap<>();

    //--------------------------------------------------------------------------------------

    public void add(T node) {
        nodes.putIfAbsent(node, new HashSet<>());
    }

    //--------------------------------------------------------------------------------------

    @Override
    public void remove(T node) throws NoSuchElementException{
        if(!nodes.containsKey(node)){
            throw new NoSuchElementException();
        }
        nodes.remove(node);

        for(Set<Edge<T>> edges : nodes.values()) {
            edges.removeIf(edge -> edge.getDestination().equals(node));
        }
    }

    //--------------------------------------------------------------------------------------

    @Override
    public void connect(T node1, T node2, String edgeName, int weight) throws NoSuchElementException, IllegalArgumentException, IllegalStateException {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException();
        }
        if (weight < 0) {
            throw new IllegalArgumentException();
        }

        if (edgeExists(node1, node2)) {
            throw new IllegalStateException();
        }


        addToEdgeMap(node1, node2, edgeName, weight);
        addToEdgeMap(node2, node1, edgeName, weight);
    }

    //--------------------------------------------------------------------------------------

    private void addToEdgeMap(T node1, T node2, String edgeName, int weight){
        if(nodes.containsKey(node1)) {
            nodes.get(node1).add(new Edge<>(node1, node2, edgeName, weight));
        }
        else {
            nodes.put(node1, new HashSet<>());
            nodes.get(node1).add(new Edge<>(node1, node2, edgeName, weight));
        }
    }

    //--------------------------------------------------------------------------------------

    private boolean edgeExists(T node1, T node2) {
        return getEdgeBetween(node1, node2) != null;
    }

    @Override
    public void disconnect(T node1, T node2) throws NoSuchElementException, IllegalStateException {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException();
        }
        if (!edgeExists(node1, node2)) {
            throw new IllegalStateException();
        }

        nodes.get(node1).remove(getEdgeBetween(node1, node2));
        nodes.get(node2).remove(getEdgeBetween(node2, node1));
    }

    //--------------------------------------------------------------------------------------

    @Override
    public void setConnectionWeight(T node1, T node2, int weight) throws NoSuchElementException, IllegalArgumentException {
        if (weight < 0) {
            throw new IllegalArgumentException();
        }
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2) || !edgeExists(node1, node2)) {
            throw new NoSuchElementException();
        }

        getEdgeBetween(node1, node2).setWeight(weight);
        getEdgeBetween(node2, node1).setWeight(weight);
    }

    //--------------------------------------------------------------------------------------

    @Override
    public Set<T> getNodes() {
        return Set.copyOf(nodes.keySet());
    }

    //--------------------------------------------------------------------------------------

    @Override
    public Collection<Edge<T>> getEdgesFrom(T node) throws NoSuchElementException {
        if (!nodes.containsKey(node)) {
            throw new NoSuchElementException();
        }

        return Set.copyOf(nodes.get(node));
    }

    //--------------------------------------------------------------------------------------

    @Override
    public Edge<T> getEdgeBetween(T node1, T node2) throws NoSuchElementException {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            throw new NoSuchElementException();
        }

        Set<Edge<T>> edges = nodes.get(node1);
        return edges == null ? null : edges.stream()
                                            .filter(edge -> edge.getDestination().equals(node2))
                                            .findAny()
                                            .orElse(null);

    }

    //--------------------------------------------------------------------------------------

    @Override
    public boolean pathExists(T node1, T node2) {
        if (!nodes.containsKey(node1) || !nodes.containsKey(node2)) {
            return false;
        }
        return getPath(node1, node2) != null;
    }

    //--------------------------------------------------------------------------------------

    //Link to wikipedia article on Dijkstra's algorithm: shorturl.at/blrFG
    @Override
    public List<Edge<T>> getPath(T origin, T destination) {
        Map<T, Float> distance = new HashMap<>();
        distance.put(origin, 0f);

        Map<T, T> previous = new HashMap<>();

        PriorityQueue<T> nodeQueue = new PriorityQueue<>(Comparator.comparing(distance::get));

        for (T node : nodes.keySet()) {
            if (!node.equals(origin)) {
                distance.put(node, Float.POSITIVE_INFINITY);
                previous.put(node, null);
            }

            nodeQueue.add(node);
        }

        while (!nodeQueue.isEmpty()) {
            T u = nodeQueue.poll();
            for (Edge<T> edge : getEdgesFrom(u)) {
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

        List<Edge<T>> path = new LinkedList<>();
        T current = destination;

        try {
            while (!current.equals(origin)) {
                path.add(getEdgeBetween(previous.get(current), current));
                current = previous.get(current);
            }
        } catch (NoSuchElementException e) {
            //if returned path is null, there is no path between the two nodes
            return null;
        }

        Collections.reverse(path);
        return path;
    }

    //--------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (T node : nodes.keySet()) {
            sb.append(node.toString()).append(", ");
            for (Edge<T> edge : getEdgesFrom(node)) {
                sb.append(edge.toString()).append(", ");
            }
            //sb.append("\n");
        }
        return sb.toString();
    }
}
