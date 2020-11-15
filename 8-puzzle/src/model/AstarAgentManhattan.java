package model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

import javafx.util.Pair;

public class AstarAgentManhattan implements SearchAgent {

	private TilesState startState;
	private Stack<TilesState> pathToGoal = new Stack<TilesState>();
	private long nodesExpanded = 0;
	private int pathCost = 0;
	private int searchDepth = 0;
	private long runningTime = 0;
	private int nodesVisited = 0;
	
	@Override
	public void setInitialState(TilesState state) {
		startState = state;	
	}
	
	class frontierComparator implements Comparator<Pair<TilesState, Float>>{

		@Override
		public int compare(Pair<TilesState, Float> pair1, Pair<TilesState, Float> pair2) {
			
			// f(n) = g(n) + h(n)  arrange each pair in priority queue according to cost and heuristic of each state
			float f1 = pair1.getKey().getPathCost() + pair1.getValue();
			float f2 = pair2.getKey().getPathCost() + pair2.getValue();
			if(f1 < f2) {
				return -1;
			}
			else if (f1 > f2) {
				return 1;
			}
			return 0;
		}
		
	}
	
	
	// check if state is in frontier or not depending on its value
	private boolean inFrontier(PriorityQueue<Pair< TilesState , Float>> PQ , TilesState state) {
		Iterator<Pair< TilesState , Float>> PQIt = PQ.iterator();
		while(PQIt.hasNext()) {
			if( TilesStatesManager.getInstance().sameStates(PQIt.next().getKey(), state)) {
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public boolean performSearch() {
		
		long startTime = System.currentTimeMillis();
		
		// frontier that holds the states in ascending order according to total cost (path cost + heuristic)
		PriorityQueue<Pair<TilesState, Float>> frontier = new PriorityQueue<>(new frontierComparator());
		Pair<TilesState, Float> startStatePair = new Pair<>(startState , TilesStatesManager.getInstance().getHeuristicManhattan(startState));
		frontier.add(startStatePair);
		nodesExpanded++;
		
		// set that holds the explored values of states
		HashSet<Integer> explored = new HashSet<>();
		
		while(!frontier.isEmpty()) {
			Pair<TilesState, Float> currentPair = frontier.poll();
			TilesState currentState = currentPair.getKey();
			explored.add(currentState.getValue());
			nodesVisited++;
			
			// check for reaching the goal state to store whole path to the goal state
			if(TilesStatesManager.getInstance().isGoalState(currentState)) {
				long endTime = System.currentTimeMillis();
				runningTime = endTime - startTime;
				
				TilesState tempState = currentState;
				pathToGoal.push(tempState);
				while(tempState.getParent() != TilesStatesFactory.getInstance().getNullParent()) {
					tempState = tempState.getParent();
					pathToGoal.push(tempState);
				}
				pathCost = pathToGoal.size() - 1;
				
				return true;
			}
			
			// loop on neighbors of current state
			for (TilesState nextState : TilesStatesManager.getInstance().getNextStates(currentState)) {
				
				// if the new state is not in frontier and the explored then add it to frontier
				if(!explored.contains(nextState.getValue()) && !inFrontier(frontier, nextState)) {
					float manhatinNextState = TilesStatesManager.getInstance().getHeuristicManhattan(nextState);
					Pair<TilesState, Float> newPair = new Pair<>(nextState, manhatinNextState);
					frontier.add(newPair);
					nodesExpanded++;
					searchDepth = Math.max(searchDepth, nextState.getDepth());
				}
				// if the new state in frontier then compare between cost of new state and the state in frontier
				// and select the minimum.
				else if (inFrontier(frontier, nextState)) {
					Iterator<Pair< TilesState , Float>> frontierIt = frontier.iterator();
					while(frontierIt.hasNext()) {
						Pair<TilesState, Float>  inFrontierPair = frontierIt.next();
						float manhatinNextState = TilesStatesManager.getInstance().getHeuristicManhattan(nextState);
						if(TilesStatesManager.getInstance().sameStates(inFrontierPair.getKey(), nextState)
								&& (nextState.getPathCost() < 
								inFrontierPair.getKey().getPathCost())) {
							Pair<TilesState, Float> newPair = new Pair<>(nextState, manhatinNextState);
							frontier.remove(inFrontierPair);
							frontier.add(newPair);
							break;
						}
					}
				}
			}
				
		}
		
		long endTime = System.currentTimeMillis();
		runningTime = endTime - startTime;
		
		return false;
	}

	@Override
	public Stack<TilesState> pathToGoal() {
		// TODO Auto-generated method stub
		return pathToGoal;
	}

	@Override
	public int pathCost() {
		// TODO Auto-generated method stub
		if(pathCost <= 0) {
			return 0;
		}
		return pathCost;
	}

	@Override
	public long nodesExpanded() {
		// TODO Auto-generated method stub
		return nodesExpanded;
	}

	@Override
	public int searchDepth() {
		// TODO Auto-generated method stub
		return searchDepth;
	}

	@Override
	public long runningTime() {
		// TODO Auto-generated method stub
		return runningTime;
	}

	@Override
	public int nodesVisited() {
		// TODO Auto-generated method stub
		return nodesVisited;
	}

}
