package test;

import java.util.Stack;

import model.DfsAgent;
import model.SearchAgent;
import model.TilesState;
import model.TilesStatesFactory;

public class TestDFS {

	public static void main(String[] args) {
		SearchAgent dfs = new DfsAgent();
		// TilesState initialRandomState =
		// TilesStatesFactory.getInstance().createRandomInitialState();
		TilesState initialState = TilesStatesFactory.getInstance().createInitialState(125349678);
		dfs.setInitialState(initialState);
		System.out.println("initial state: " + initialState.getValue());
		boolean found = dfs.performSearch();
		if (found) {
			Stack<TilesState> path = dfs.pathToGoal();
			while (!path.isEmpty()) {
				TilesState currentState = path.pop();
				System.out.println("Value: " + currentState.getValue());
				System.out.println("Cost: " + currentState.getPathCost());
				System.out.println("Depth: " + currentState.getDepth());
				System.out.println("Parent: " + currentState.getParent().getValue());
				System.out.println();
			}
		} else {
			System.out.println("path not found");
		}
		System.out.println();
		System.out.println("Cost of path: " + dfs.pathCost());
		System.out.println("nodes expanded: " + dfs.nodesExpanded());
		System.out.println("search depth: " + dfs.searchDepth());
		System.out.println("Goal depth: " + dfs.pathCost());
		System.out.println("Visited nodes including goal state: " + dfs.nodesVisited());
		System.out.println("running time: " + dfs.runningTime());
	}

}
