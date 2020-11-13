package model;
/*
 * This interface is for consensus on the search agents design
 */

import java.util.Stack;

public interface SearchAgent {

	// Method that sets the initial state of search space
	public void setInitialState(TilesState state);

	// Perform the algorithm, return true if a path was found to the goal
	public boolean performSearch();

	// Returns the found path to the goal starting from the initial state at
	// location zero
	// Return null if search didn't reach the goal
	// implemented as a stack because the path is found by backtracking
	public Stack<TilesState> pathToGoal();

	// Returns the cost of the path to the goal where each step costs 1
	public int pathCost();

	// Returns the total number of nodes expanded during the search algorithm for
	// performance measure
	public long nodesExpanded();

	// Returns the depth of the search tree found (to be discussed more but
	// initially considered to be the max path cost found)
	public int searchDepth();

	// Returns the running time of the search algorithm in seconds for performance
	// measure
	public float runningTime();
}
