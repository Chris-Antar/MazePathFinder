package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

/**
 * <P>
 * This class represents a general "directed graph", which could be used for any
 * purpose. The graph is viewed as a collection of vertices, which are sometimes
 * connected by weighted, directed edges.
 * </P>
 * 
 * <P>
 * This graph will never store duplicate vertices.
 * </P>
 * 
 * <P>
 * The weights will always be non-negative integers.
 * </P>
 * 
 * <P>
 * The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.
 * </P>
 * 
 * <P>
 * The Weighted Graph will maintain a collection of "GraphAlgorithmObservers",
 * which will be notified during the performance of the graph algorithms to
 * update the observers on how the algorithms are progressing.
 * </P>
 */
public class WeightedGraph<V> {

	/*
	 * STUDENTS: You decide what data structure(s) to use to implement this
	 * class.
	 * 
	 * You may use any data structures you like, and any Java collections that
	 * we learned about this semester. Remember that you are implementing a
	 * weighted, directed graph.
	 */
	public Map<V, HashMap<V, Integer>> weightedGraph;

	/*
	 * Collection of observers. Be sure to initialize this list in the
	 * constructor. The method "addObserver" will be called to populate this
	 * collection. Your graph algorithms (DFS, BFS, and Dijkstra) will notify
	 * these observers to let them know how the algorithms are progressing.
	 */
	private Collection<GraphAlgorithmObserver<V>> observerList;

	/**
	 * Initialize the data structures to "empty", including the collection of
	 * GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		weightedGraph = new HashMap<V, HashMap<V, Integer>>();
		observerList = new ArrayList<GraphAlgorithmObserver<V>>();
	}

	/**
	 * Add a GraphAlgorithmObserver to the collection maintained by this graph
	 * (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/**
	 * Add a vertex to the graph. If the vertex is already in the graph, throw
	 * an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in the graph
	 */
	public void addVertex(V vertex) {
		if (!weightedGraph.containsKey(vertex)) {
			weightedGraph.put(vertex, new HashMap<V, Integer>());
		}

	}

	/**
	 * Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		if (weightedGraph.containsKey(vertex)) {
			return true;
		}
		return false;
	}

	/**
	 * <P>
	 * Add an edge from one vertex of the graph to another, with the weight
	 * specified.
	 * </P>
	 * 
	 * <P>
	 * The two vertices must already be present in the graph.
	 * </P>
	 * 
	 * <P>
	 * This method throws an IllegalArgumentExeption in three cases:
	 * </P>
	 * <P>
	 * 1. The "from" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 2. The "to" vertex is not already in the graph.
	 * </P>
	 * <P>
	 * 3. The weight is less than 0.
	 * </P>
	 * 
	 * @param from   the vertex the edge leads from
	 * @param to     the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex is not in the graph,
	 *                                  or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		if (!weightedGraph.containsKey(from) || !weightedGraph.containsKey(from)
				|| weight < 0) {
			throw new IllegalArgumentException();
		}
		HashMap<V, Integer> placeholder = weightedGraph.get(from);
		placeholder.put(to, weight);
		weightedGraph.put(from, placeholder);
	}

	/**
	 * <P>
	 * Returns weight of the edge connecting one vertex to another. Returns null
	 * if the edge does not exist.
	 * </P>
	 * 
	 * <P>
	 * Throws an IllegalArgumentException if either of the vertices specified
	 * are not in the graph.
	 * </P>
	 * 
	 * @param from vertex where edge begins
	 * @param to   vertex where edge terminates
	 * @return weight of the edge, or null if there is no edge connecting these
	 *         vertices
	 * @throws IllegalArgumentException if either of the vertices specified are
	 *                                  not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		if (!weightedGraph.containsKey(from)
				|| !weightedGraph.containsKey(to)) {
			throw new IllegalArgumentException();
		}
		if (!weightedGraph.get(from).containsKey(to)) {
			return null;
		}
		return weightedGraph.get(from).get(to);
	}

	/**
	 * <P>
	 * This method will perform a Breadth-First-Search on the graph. The search
	 * will begin at the "start" vertex and conclude once the "end" vertex has
	 * been reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyBFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through
	 * the collection of observers calling notifyVisit on each one (passing in
	 * the vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after
	 * which the method should terminate immediately, without processing further
	 * vertices.
	 * </P>
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */
	public void DoBFS(V start, V end) {
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyBFSHasBegun();
		}
		ArrayList<V> visitedset = new ArrayList<V>();
		ArrayDeque<V> discovered = new ArrayDeque<V>();
		discovered.add(start);
		while (discovered.size() != 0) {
			V curVertex = discovered.poll();
			if (!visitedset.contains(curVertex)) {
				visitedset.add(curVertex);
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(curVertex);
				}
				// checks if curVertex is the end vertex. Will end the function
				if (curVertex.equals(end)) {
					for (GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
						;
					}
					break;
				}
				// Checks adjacent vertices
				for (V adjancentcies : weightedGraph.get(curVertex).keySet()) {
					if (!visitedset.contains(adjancentcies)) {
						discovered.add(adjancentcies);
					}
				}
			}

		}

	}

	/**
	 * <P>
	 * This method will perform a Depth-First-Search on the graph. The search
	 * will begin at the "start" vertex and conclude once the "end" vertex has
	 * been reached.
	 * </P>
	 * 
	 * <P>
	 * Before the search begins, this method will go through the collection of
	 * Observers, calling notifyDFSHasBegun on each one.
	 * </P>
	 * 
	 * <P>
	 * Just after a particular vertex is visited, this method will go through
	 * the collection of observers calling notifyVisit on each one (passing in
	 * the vertex being visited as the argument.)
	 * </P>
	 * 
	 * <P>
	 * After the "end" vertex has been visited, this method will go through the
	 * collection of observers calling notifySearchIsOver on each one, after
	 * which the method should terminate immediately, without visiting further
	 * vertices.
	 * </P>
	 * 
	 * @param start vertex where search begins
	 * @param end   the algorithm terminates just after this vertex is visited
	 */
	public void DoDFS(V start, V end) {
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDFSHasBegun();
		}
		ArrayList<V> visitedset = new ArrayList<V>();
		Stack<V> discovered = new Stack<V>();
		discovered.push(start);
		while (discovered.size() != 0) {
			V curVertex = discovered.pop();
			if (!visitedset.contains(curVertex)) {
				visitedset.add(curVertex);
				for (GraphAlgorithmObserver<V> observer : observerList) {
					observer.notifyVisit(curVertex);
				}
				// checks if curVertex is the end vertex. Will end the function
				if (curVertex.equals(end)) {
					for (GraphAlgorithmObserver<V> observer : observerList) {
						observer.notifySearchIsOver();
					}
					break;
				}
				// Checks adjacent vertices
				for (V adjacentcies : weightedGraph.get(curVertex).keySet()) {
					if (!visitedset.contains(adjacentcies)) {
						discovered.push(adjacentcies);
					}
				}
			}

		}
	}

	/**
	 * <P>
	 * Perform Dijkstra's algorithm, beginning at the "start" vertex.
	 * </P>
	 * 
	 * <P>
	 * The algorithm DOES NOT terminate when the "end" vertex is reached. It
	 * will continue until EVERY vertex in the graph has been added to the
	 * finished set.
	 * </P>
	 * 
	 * <P>
	 * Before the algorithm begins, this method goes through the collection of
	 * Observers, calling notifyDijkstraHasBegun on each Observer.
	 * </P>
	 * 
	 * <P>
	 * Each time a vertex is added to the "finished set", this method goes
	 * through the collection of Observers, calling notifyDijkstraVertexFinished
	 * on each one (passing the vertex that was just added to the finished set
	 * as the first argument, and the optimal "cost" of the path leading to that
	 * vertex as the second argument.)
	 * </P>
	 * 
	 * <P>
	 * After all of the vertices have been added to the finished set, the
	 * algorithm will calculate the "least cost" path of vertices leading from
	 * the starting vertex to the ending vertex. Next, it will go through the
	 * collection of observers, calling notifyDijkstraIsOver on each one,
	 * passing in as the argument the "lowest cost" sequence of vertices that
	 * leads from start to end (I.e. the first vertex in the list will be the
	 * "start" vertex, and the last vertex in the list will be the "end"
	 * vertex.)
	 * </P>
	 * 
	 * @param start vertex where algorithm will start
	 * @param end   special vertex used as the end of the path reported to
	 *              observers via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		HashSet<V> allVertices = new HashSet<V>();
		HashMap<V, V> Predecessor = new HashMap<V, V>();
		HashMap<V, Double> Cost = new HashMap<V, Double>();
		HashSet<V> finishedSet = new HashSet<V>();
		// Sets initial values for Cost and Predecessor Maps
		for (V vertex : weightedGraph.keySet()) {
			allVertices.add(vertex);
			Predecessor.put(vertex, null);
			Cost.put(vertex, Double.POSITIVE_INFINITY);
		}
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraHasBegun();
		}
		Cost.put(start, 0.0);
		while (finishedSet.size() < allVertices.size()) {
			double curMinCost = Double.POSITIVE_INFINITY;
			V curVertex = null;
			// Finds the lowest cost vertex in the "Cost" Map
			for (HashMap.Entry<V, Double> entry : Cost.entrySet()) {
				if (entry.getValue() < curMinCost) {
					if (!finishedSet.contains(entry.getKey())) {
						curMinCost = entry.getValue();
						curVertex = entry.getKey();
					}
				}
			}
			finishedSet.add(curVertex);
			for (GraphAlgorithmObserver<V> observer : observerList) {
				observer.notifyDijkstraVertexFinished(curVertex,
						Cost.get(curVertex).intValue());
			}

			// Updates costs from current vertex
			for (V adjacentVertex : weightedGraph.get(curVertex).keySet()) {
				if (!finishedSet.contains(adjacentVertex)) {
					if (Cost.get(curVertex) + getWeight(curVertex,
							adjacentVertex) < Cost.get(adjacentVertex)) {
						Cost.put(adjacentVertex, Cost.get(curVertex)
								+ getWeight(curVertex, (V) adjacentVertex));
						Predecessor.put(adjacentVertex, curVertex);
					}
				}
			}
		}
		// Makes Lowest path into a list of vertices
		ArrayList<V> lowestPathCost = new ArrayList<V>();
		V curVertex = end;
		while (curVertex != null) {
			lowestPathCost.add(curVertex);
			curVertex = Predecessor.get(curVertex);
		}
		// Notifies observers
		Collections.reverse(lowestPathCost);
		for (GraphAlgorithmObserver<V> observer : observerList) {
			observer.notifyDijkstraIsOver(lowestPathCost);
		}

	}

}
