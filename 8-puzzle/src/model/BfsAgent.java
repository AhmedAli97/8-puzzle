package model;

import java.util.Stack;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

public class BfsAgent implements SearchAgent {

	private TilesState initState;
	private Stack<TilesState> pathToGoal = new Stack<TilesState>();
	private long runtime = 0;
	private long expandedNodesCount = 0;
	private int visitedNodes = 0;
	private int pathCost = 0;
	private int searchDepth = 0;
	private TilesStatesManager stateManager = TilesStatesManager.getInstance();
	@Override
	public void setInitialState(TilesState state) {
		initState = state;
	}

	@Override
	public boolean performSearch() {
		// variables used to calculate the running time of the algorithm
		long start, end;
		start = System.currentTimeMillis();
		// Queue to contain the states and keep track of visited ones
		Queue<TilesState> frontier = new LinkedList<TilesState>();
		// list of states values to check existence and prevent duplicates
		ArrayList<Integer> frontierValues = new ArrayList<Integer>();
		enqueueFrontier(initState, frontier, frontierValues);
		// list used to keep track of explored states
		ArrayList<Integer> explored = new ArrayList<Integer>();
		/* BFS Algorithm */
		while(!frontier.isEmpty()) {
			TilesState currState = frontier.remove();
			explored.add(currState.getValue());
			visitedNodes++;
			// check if we reached the goal state (912345678) to return true
			if (stateManager.isGoalState(currState)) {
				end = System.currentTimeMillis();
				runtime = (end - start);
				// fill the path to the goal state
				while (currState != TilesStatesFactory.getInstance().getNullParent()) {
					pathToGoal.add(currState);
					currState = currState.getParent();
				}
				pathCost = pathToGoal.size() - 1;
				return true;
			}
			// getting next states and adding them to the frontier queue
			// neighbors are listed in the next order: left - top - right - bottom
			// using queue we need to insert the left first (FIFO)
			ArrayList<TilesState> neighbors = stateManager.getNextStates(currState);
			for (int i = 0; i < neighbors.size(); i++) {
				TilesState currentNeighbor = neighbors.get(i);
				// check that this is the first time we obtain this state to prevent duplicates
				if (!(explored.contains(currentNeighbor.getValue())
						|| frontierValues.contains(currentNeighbor.getValue()))) {
					enqueueFrontier(currentNeighbor, frontier, frontierValues);
				}
			}
		}
		end = System.currentTimeMillis();
		runtime = (end - start);
		return false;
	}
	
	private void enqueueFrontier(TilesState state, Queue<TilesState> frontier, ArrayList<Integer> frontierValues) {
		frontier.add(state);
		frontierValues.add(state.getValue());
		expandedNodesCount++;
		searchDepth = Math.max(searchDepth, state.getDepth());
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
		return expandedNodesCount;
	}

	@Override
	public int searchDepth() {
		return searchDepth;
	}

	@Override
	public long runningTime() {
		return runtime;
	}
	
	@Override
	public int nodesVisited() {
		return visitedNodes;
	}

}
