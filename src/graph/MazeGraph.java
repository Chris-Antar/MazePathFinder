package graph;

import java.util.Map;

import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/**
 * <P>
 * The MazeGraph is an extension of WeightedGraph. The constructor converts a
 * Maze into a graph.
 * </P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	/*
	 * STUDENTS: SEE THE PROJECT DESCRIPTION FOR A MUCH MORE DETAILED
	 * EXPLANATION ABOUT HOW TO WRITE THIS CONSTRUCTOR
	 */

	/**
	 * <P>
	 * Construct the MazeGraph using the "maze" contained in the parameter to
	 * specify the vertices (Junctures) and weighted edges.
	 * </P>
	 * 
	 * <P>
	 * The Maze is a rectangular grid of "junctures", each defined by its X and
	 * Y coordinates, using the usual convention of (0, 0) being the upper left
	 * corner.
	 * </P>
	 * 
	 * <P>
	 * Each juncture in the maze should be added as a vertex to this graph.
	 * </P>
	 * 
	 * <P>
	 * For every pair of adjacent junctures (A and B) which are not blocked by a
	 * wall, two edges should be added: One from A to B, and another from B to
	 * A. The weight to be used for these edges is provided by the Maze. (The
	 * Maze methods getMazeWidth and getMazeHeight can be used to determine the
	 * number of Junctures in the maze. The Maze methods called "isWallAbove",
	 * "isWallToRight", etc. can be used to detect whether or not there is a
	 * wall between any two adjacent junctures. The Maze methods called
	 * "getWeightAbove", "getWeightToRight", etc. should be used to obtain the
	 * weights.)
	 * </P>
	 * 
	 * @param maze to be used as the source of information for adding vertices
	 *             and edges to this MazeGraph.
	 */

	public MazeGraph(Maze maze) {
		// Add all vertices to graph
		for (int xCoordinate = 0; xCoordinate < maze
				.getMazeWidth(); xCoordinate++) {
			for (int yCoordinate = 0; yCoordinate < maze
					.getMazeHeight(); yCoordinate++) {
				super.addVertex(new Juncture(xCoordinate, yCoordinate));
			}
		}
		// Add edges between vertices
		for (int xCoordinate = 0; xCoordinate < maze
				.getMazeWidth(); xCoordinate++) {
			for (int yCoordinate = 0; yCoordinate < maze
					.getMazeHeight(); yCoordinate++) {
				Juncture temp = new Juncture(xCoordinate, yCoordinate);
				if (!maze.isWallBelow(temp)) {
					int weight = maze.getWeightBelow(temp);
					super.addEdge(temp,
							new Juncture(xCoordinate, yCoordinate + 1), weight);
				}
				if (!maze.isWallAbove(temp)) {
					int weight = maze.getWeightAbove(temp);
					super.addEdge(temp,
							new Juncture(xCoordinate, yCoordinate - 1), weight);
				}
				if (!maze.isWallToLeft(temp)) {
					int weight = maze.getWeightToLeft(temp);
					super.addEdge(temp,
							new Juncture(xCoordinate - 1, yCoordinate), weight);
				}
				if (!maze.isWallToRight(temp)) {
					int weight = maze.getWeightToRight(temp);
					super.addEdge(temp,
							new Juncture(xCoordinate + 1, yCoordinate), weight);
				}
			}
		}
	}
}
