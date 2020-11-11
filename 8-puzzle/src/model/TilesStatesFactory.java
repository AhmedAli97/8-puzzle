package model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/*
 * This class is responsible for methods corresponding to creating the states.
 */

public class TilesStatesFactory {
	// This class is a singleton
	private static TilesStatesFactory single_instance = null;

	// static method to create instance of the class
	public static TilesStatesFactory getInstance() {
		if (single_instance == null)
			single_instance = new TilesStatesFactory();
		return single_instance;
	}

	// A state that encodes a null parent to use it in case no parent exist for a
	// node.
	private final TilesState nullParent = new TilesState(-1);

	public TilesState getRandomInitialState() {
		// list keeps the possible numbers in the tiles
		Integer[] possibleNumbers = { 1, 2, 3, 4, 5, 6, 7, 8, TilesState.getEmptyValue() };
		List<Integer> randomNumbers = Arrays.asList(possibleNumbers);
		// Randomizing the numbers order
		Collections.shuffle(randomNumbers);
		// constructing the initial state with a null parent
		return new TilesState(getValue(randomNumbers), getNullParent());
	}

	// given a collection of the possible numbers to encode the state, it returns
	// the encoding value
	public int getValue(Collection<Integer> col) {
		int value = 0;
		Iterator<Integer> iterator = col.iterator();
		int counter = 0;
		while (iterator.hasNext()) {
			value += (int) Math.pow(10, counter++) * iterator.next();
		}
		return value;
	}

	// getters and setters
	public TilesState getNullParent() {
		return nullParent;
	}
}
