package model;
/*
 * This interface is for consensus on the search agents design
 */

import java.util.ArrayList;

public interface SearchAgent {

	// Method that sets the initial state of search space
	public void setInitialState(TilesState state);

	// Perform the algorithm
	public void performSearch();

	// Returns the found path to the goal starting from the initial state at
	// location zero
	// Return null if search didn't reach the goal
	public ArrayList<TilesState> pathToGoal();

	// Returns the cost of the path to the goal where each step costs 1
	public int pathCost();

	// Returns the total number of nodes expanded during the search algorithm for
	// performance measure
	public int nodesExpanded();

	// Returns the depth of the search tree found (to be discussed more but
	// initially considered to be the max path cost found)
	public int searchDepth();

	// Returns the running time of the search algorithm for performance measure
	public float runningTime();
}
