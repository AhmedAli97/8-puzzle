package model;

/*
 * This class implements an object to keep the current state of the tiles
 * each state has a corresponding value encoding this state in the form of an integer
 * where numbers are concatenated from the tiles starting from the upper left corner. 
 * We keep track of:
 * the value of the state
 * the parent state of this state for path finding
 * the accumulated cost of the path from the initial state to this state  
 * the depth of this state in the search tree
 */
public class TilesState {
	// The required variables to be stored in the state
	private int value;
	private TilesState parent;
	private int pathCost;
	private int depth;
	// we set the value corresponding to blank to be nine instead of zero for more
	// convenience in implementation considering the case that the encoding value starts with a blank. 
	private static int emptyValue = 9;
	// Constructors
	public TilesState(int value, int pathCost, TilesState parent) {
		this.setValue(value);
		this.setParent(parent);
		this.setPathCost(pathCost);
	}

	public TilesState(int value, TilesState parent) {
		this.setValue(value);
		this.setParent(parent);
	}

	public TilesState(int value) {
		this.setPathCost(0);
		this.setValue(value);
	}

	public TilesState() {
		this.setPathCost(0);
	}

	// getters and setters
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public TilesState getParent() {
		return parent;
	}

	public void setParent(TilesState parent) {
		this.parent = parent;
	}

	public int getPathCost() {
		return pathCost;
	}

	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	public static int getEmptyValue() {
		return emptyValue;
	}

	public static void setEmptyValue(int emptyValue) {
		TilesState.emptyValue = emptyValue;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

}
