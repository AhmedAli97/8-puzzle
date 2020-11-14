package model;

import java.util.ArrayList;
import java.util.Stack;

/*
 * An uninformed search agent implementing the DFS algorithm
 */
public class DfsAgent implements SearchAgent {

	// The state stored in this agent to perform the algorithm and obtain results
	private TilesState initialState;
	private Stack<TilesState> pathToGoal = new Stack<TilesState>();
	private long nodesExpanded = 0;
	private int pathCost = 0;
	private int searchDepth = 0;
	private float runningTime = 0;
	private int nodesVisited = 0;

	@Override
	public void setInitialState(TilesState state) {
		initialState = state;
	}

	@Override
	// Calculates the nodes expanded, search depth and running time
	public boolean performSearch() {
		// Start time
		long start = System.currentTimeMillis();
		// follows the DFS search algorithm
		// initializing the frontier stack and the visited set
		Stack<TilesState> frontier = new Stack<TilesState>();
		// keeps the values in the frontier to check if they were already added, it is
		// an array list of integers for convenience
		ArrayList<Integer> frontierValues = new ArrayList<Integer>();
		addFrontier(initialState, frontier, frontierValues);
		// Set here is implemented as an array list of integers for convenience to check
		// duplicationsF
		ArrayList<Integer> explored = new ArrayList<Integer>();
		while (!frontier.isEmpty()) {
			// exploring the current state on top of the stack
			TilesState currentState = frontier.pop();
			explored.add(currentState.getValue());
			nodesVisited++;
			if (TilesStatesManager.getInstance().isGoalState(currentState)) {
				// end time in case the goal was found
				long end = System.currentTimeMillis();
				// calculating running time in seconds
				runningTime = (end - start) / 1000;
				createPathToGoal(currentState);
				return true;
			}
			ArrayList<TilesState> neighbors = TilesStatesManager.getInstance().getNextStates(currentState);
			// we loop from the end so that the leftmost is on top of the stack
			for (int i = neighbors.size() - 1; i >= 0; i--) {
				TilesState currentNeighbor = neighbors.get(i);
				// check that this is the first time we obtain this state
				if (!(explored.contains(currentNeighbor.getValue())
						|| frontierValues.contains(currentNeighbor.getValue()))) {
					addFrontier(currentNeighbor, frontier, frontierValues);
				}
			}
		}
		// end time in case the goal was found
		long end = System.currentTimeMillis();
		// calculating running time in seconds
		runningTime = (end - start) / 1000;
		return false;
	}

	// Adds the frontier and updates the state variables according to it
	private void addFrontier(TilesState state, Stack<TilesState> frontier, ArrayList<Integer> frontierValues) {
		frontier.push(state);
		frontierValues.add(state.getValue());
		nodesExpanded++;
		searchDepth = Math.max(searchDepth, state.getDepth());
	}

	// fills the path to goal given the found goal state
	private void createPathToGoal(TilesState goalState) {
		TilesState currentState = goalState;
		while (currentState != TilesStatesFactory.getInstance().getNullParent()) {
			pathToGoal.push(currentState);
			currentState = currentState.getParent();
		}
		pathCost = pathToGoal.size() - 1;
	}

	@Override
	public Stack<TilesState> pathToGoal() {
		if (pathToGoal == null || pathToGoal.size() == 0)
			return null;
		return pathToGoal;
	}

	@Override
	public int pathCost() {
		return pathCost;
	}

	@Override
	public long nodesExpanded() {
		return nodesExpanded;
	}

	@Override
	public int searchDepth() {
		return searchDepth;
	}

	@Override
	public float runningTime() {
		return runningTime;
	}

	@Override
	public int nodesVisited() {
		return nodesVisited;
	}
}
