package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AstarAgentEuclidean;
import model.AstarAgentManhattan;
import model.BfsAgent;
import model.DfsAgent;
import model.SearchAgent;
import model.TilesState;
import model.TilesStatesFactory;
import model.TilesStatesManager;

public class mainPageController implements Initializable{

	// indexes of the 3x3 matrix representing the puzzle
    @FXML
    private TextField index0, index1, index2, index3, index4, index5, index6, index7, index8;
    // labels used to show values returned from the search agents
    @FXML
    private Label dfsCost, dfsDepth, dfsMaxDepth, dfsExpNodes, dfsVisNodes, dfsRuntime,
    bfsCost, bfsDepth, bfsMaxDepth, bfsExpNodes, bfsVisNodes, bfsRuntime,
    aEucCost, aEucDepth, aEucMaxDepth, aEucExpNodes, aEucVisNodes, aEucRuntime,
    aManCost, aManDepth, aManMaxDepth, aManExpNodes, aManVisNodes, aManRuntime;
    // buttons used in the interface to apply some actions
    @FXML
    private JFXButton randomize, setPuzzle, reset,
    dfsBtn, bfsBtn, aEucBtn, aManBtn, solveAllBtn, showResult;
    @FXML
    private JFXRadioButton dfsRadio, bfsRadio, aEucRadio, aManRadio;
    // booleans used to check if an algorithm is performed or not
    boolean dfsIsSolved, bfsIsSolved, aEucIsSolved, aManIsSolved,
    //booleans used to detect selected radio button to show the algorithm result
    dfsIsSelected, bfsIsSelected, aEucIsSelected, aManIsSelected;
    private static int selectedSearchAgent = -1;//no agent is selected
    TilesStatesFactory stateFactoryInst;
    TilesStatesManager stateManagerInst;
    TilesState initailState;
    ArrayList<TextField> puzzleState = new ArrayList<TextField>();
    private static ArrayList<TilesState> dfsGoalPath = new ArrayList<TilesState>();
    private static ArrayList<TilesState> bfsGoalPath = new ArrayList<TilesState>();
    private static ArrayList<TilesState> aEucGoalPath = new ArrayList<TilesState>();
    private static ArrayList<TilesState> aManGoalPath = new ArrayList<TilesState>();
//  #c4cebc #145a32  #98e081  #48c9b0
   
   
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dfsBtn.setDisable(true);
		bfsBtn.setDisable(true);
		aEucBtn.setDisable(true);
		aManBtn.setDisable(true);
		solveAllBtn.setDisable(true);
		showResult.setDisable(true);
		reset.setDisable(true);
		stateFactoryInst = TilesStatesFactory.getInstance();
		stateManagerInst = TilesStatesManager.getInstance();
		puzzleState.add(0, index0);
		puzzleState.add(1, index1);
		puzzleState.add(2, index2);
		puzzleState.add(3, index3);
		puzzleState.add(4, index4);
		puzzleState.add(5, index5);
		puzzleState.add(6, index6);
		puzzleState.add(7, index7);
		puzzleState.add(8, index8);
	}
    
    @FXML
    void performDFS(ActionEvent event) {
    	SearchAgent dfsAgent = new DfsAgent();
    	dfsAgent.setInitialState(initailState);
    	if (!dfsAgent.performSearch()) {
    		dfsCost.setText("No Path found");
        	dfsDepth.setText("No Path found");
        	dfsMaxDepth.setText(Integer.toString(dfsAgent.searchDepth()));
        	dfsExpNodes.setText(Long.toString(dfsAgent.nodesExpanded()));
        	dfsVisNodes.setText(Integer.toString(dfsAgent.nodesVisited()));
        	dfsRuntime.setText(Long.toString(dfsAgent.runningTime()) + " ms");
        	dfsBtn.setDisable(true);
        	return;
    	}
    	dfsCost.setText(Integer.toString(dfsAgent.pathCost()));
    	dfsDepth.setText(Integer.toString(dfsAgent.pathCost()));
    	dfsMaxDepth.setText(Integer.toString(dfsAgent.searchDepth()));
    	dfsExpNodes.setText(Long.toString(dfsAgent.nodesExpanded()));
    	dfsVisNodes.setText(Integer.toString(dfsAgent.nodesVisited()));
    	dfsRuntime.setText(Long.toString(dfsAgent.runningTime()) + " ms");
    	Stack<TilesState> pathToGoal = dfsAgent.pathToGoal();
    	for (int i = pathToGoal.size() - 1; i >= 0; i--)
    		dfsGoalPath.add(pathToGoal.pop());
    	dfsIsSolved = true;
    	dfsBtn.setDisable(true);
    }
    @FXML
    void performBFS(ActionEvent event) {
    	SearchAgent bfsAgent = new BfsAgent();
    	bfsAgent.setInitialState(initailState);
    	if (!bfsAgent.performSearch()) {
    		bfsCost.setText("No Path Found");
        	bfsDepth.setText("No Path Found");
        	bfsMaxDepth.setText(Integer.toString(bfsAgent.searchDepth()));
        	bfsExpNodes.setText(Long.toString(bfsAgent.nodesExpanded()));
        	bfsVisNodes.setText(Integer.toString(bfsAgent.nodesVisited()));
        	bfsRuntime.setText(Long.toString(bfsAgent.runningTime()) + " ms");
        	bfsBtn.setDisable(true);
        	return;
    	}
    	bfsCost.setText(Integer.toString(bfsAgent.pathCost()));
    	bfsDepth.setText(Integer.toString(bfsAgent.pathCost()));
    	bfsMaxDepth.setText(Integer.toString(bfsAgent.searchDepth()));
    	bfsExpNodes.setText(Long.toString(bfsAgent.nodesExpanded()));
    	bfsVisNodes.setText(Integer.toString(bfsAgent.nodesVisited()));
    	bfsRuntime.setText(Long.toString(bfsAgent.runningTime()) + " ms");
    	Stack<TilesState> pathToGoal = bfsAgent.pathToGoal();
    	for (int i = pathToGoal.size() - 1; i >= 0; i--)
    		bfsGoalPath.add(pathToGoal.pop());
    	bfsIsSolved = true;
    	bfsBtn.setDisable(true);
    }
    @FXML
    void performAeuc(ActionEvent event) {
    	SearchAgent aEucAgent = new AstarAgentEuclidean();
    	aEucAgent.setInitialState(initailState);
    	if (!aEucAgent.performSearch()) {
    		aEucCost.setText("No Path Found");
    		aEucDepth.setText("No Path Found");
    		aEucMaxDepth.setText(Integer.toString(aEucAgent.searchDepth()));
    		aEucExpNodes.setText(Long.toString(aEucAgent.nodesExpanded()));
    		aEucVisNodes.setText(Integer.toString(aEucAgent.nodesVisited()));
    		aEucRuntime.setText(Long.toString(aEucAgent.runningTime()) + " ms");
    		aEucBtn.setDisable(true);
        	return;
    	}
    	aEucCost.setText(Integer.toString(aEucAgent.pathCost()));
    	aEucDepth.setText(Integer.toString(aEucAgent.pathCost()));
    	aEucMaxDepth.setText(Integer.toString(aEucAgent.searchDepth()));
    	aEucExpNodes.setText(Long.toString(aEucAgent.nodesExpanded()));
    	aEucVisNodes.setText(Integer.toString(aEucAgent.nodesVisited()));
    	aEucRuntime.setText(Long.toString(aEucAgent.runningTime()) + " ms");
    	Stack<TilesState> pathToGoal = aEucAgent.pathToGoal();
    	for (int i = pathToGoal.size() - 1; i >= 0; i--)
    		aEucGoalPath.add(pathToGoal.pop());
    	aEucIsSolved = true;
    	aEucBtn.setDisable(true);
    }
    @FXML
    void performAman(ActionEvent event) {
    	SearchAgent aManAgent = new AstarAgentManhattan();
    	aManAgent.setInitialState(initailState);
    	if (!aManAgent.performSearch()) {
    		aManCost.setText("No Path Found");
    		aManDepth.setText("No Path Found");
    		aManMaxDepth.setText(Integer.toString(aManAgent.searchDepth()));
    		aManExpNodes.setText(Long.toString(aManAgent.nodesExpanded()));
    		aManVisNodes.setText(Integer.toString(aManAgent.nodesVisited()));
    		aManRuntime.setText(Long.toString(aManAgent.runningTime()) + " ms");
    		aManBtn.setDisable(true);
        	return;
    	}
    	aManCost.setText(Integer.toString(aManAgent.pathCost()));
    	aManDepth.setText(Integer.toString(aManAgent.pathCost()));
    	aManMaxDepth.setText(Integer.toString(aManAgent.searchDepth()));
    	aManExpNodes.setText(Long.toString(aManAgent.nodesExpanded()));
    	aManVisNodes.setText(Integer.toString(aManAgent.nodesVisited()));
    	aManRuntime.setText(Long.toString(aManAgent.runningTime()) + " ms");
    	Stack<TilesState> pathToGoal = aManAgent.pathToGoal();
    	for (int i = pathToGoal.size() - 1; i >= 0; i--)
    		aManGoalPath.add(pathToGoal.pop());
    	aManIsSolved = true;
    	aManBtn.setDisable(true);
    }
    @FXML
    void performAll(ActionEvent event) {
    	if (!dfsIsSolved)
    		performDFS(new ActionEvent());
    	if (!bfsIsSolved)
    		performBFS(new ActionEvent());
    	if (!aEucIsSolved)
    		performAeuc(new ActionEvent());
    	if (!aManIsSolved)
    		performAman(new ActionEvent());
    	solveAllBtn.setDisable(true);
    }

    @FXML
    void randomizeInitalState(ActionEvent event) {
    	TilesState initialState = stateFactoryInst.createRandomInitialState();
    	for (int i = 0; i < puzzleState.size(); i++) {
    		int indexValue = stateManagerInst.getNumberAt(initialState, i);
    		if (indexValue != 9)
    			puzzleState.get(i).setText(Integer.toString(indexValue));
    	}
    }

    @FXML
    void resetData(ActionEvent event) {
    	for (int  i = 0; i < puzzleState.size(); i++)
    		puzzleState.get(i).setText("");
    	dfsBtn.setDisable(true);
		bfsBtn.setDisable(true);
		aEucBtn.setDisable(true);
		aManBtn.setDisable(true);
		solveAllBtn.setDisable(true);
		showResult.setDisable(true);
		reset.setDisable(true);
		dfsIsSolved = bfsIsSolved = aEucIsSolved = aManIsSolved = false;
		dfsIsSelected = bfsIsSelected = aEucIsSelected = aManIsSelected = false;
		dfsRadio.setSelected(false);
		bfsRadio.setSelected(false);
		aEucRadio.setSelected(false);
		aManRadio.setSelected(false);
		selectedSearchAgent = -1;
		initailState = new TilesState();
		dfsGoalPath = new ArrayList<TilesState>();
		bfsGoalPath = new ArrayList<TilesState>();
		aEucGoalPath = new ArrayList<TilesState>();
		aManGoalPath = new ArrayList<TilesState>();
		clearLabels();
    }

    @FXML
    void saveInitialState(ActionEvent event) {
    	List<Integer> stateValues = new ArrayList<Integer>();
    	for (int i = 0; i < puzzleState.size(); i++) {
    		String indexValue = puzzleState.get(i).getText();
    		if (indexValue.isEmpty())
    			stateValues.add(TilesState.getEmptyValue());
    		else
    			stateValues.add(Integer.valueOf(indexValue));
    	}
    	initailState = stateFactoryInst.createInitialState(getValue(stateValues));
    	dfsBtn.setDisable(false);
		bfsBtn.setDisable(false);
		aEucBtn.setDisable(false);
		aManBtn.setDisable(false);
		solveAllBtn.setDisable(false);
		reset.setDisable(false);
	}
    
    private int getValue(Collection<Integer> col) {
		int value = 0;
		Iterator<Integer> iterator = col.iterator();
		int counter = col.size() - 1;
		while (iterator.hasNext()) {
			value += (int) Math.pow(10, counter--) * iterator.next();
		}
		return value;
	}

    @FXML
    void showPathToGoal(ActionEvent event) {
    	try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/showPath.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setTitle("Path Found by Search Agent");
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void selectAEucRadio(ActionEvent event) {
    	if (aEucRadio.isSelected()) {
    		aEucIsSelected = true;
    		dfsIsSelected = bfsIsSelected = aManIsSelected = false;
    	} else
    		aEucIsSelected = false;
    	optimizeRadioBtns();
    }

    @FXML
    void selectAManRadio(ActionEvent event) {
    	if (aManRadio.isSelected()) {
    		aManIsSelected = true;
    		dfsIsSelected = bfsIsSelected = aEucIsSelected = false;
    	} else
    		aManIsSelected = false;
    	optimizeRadioBtns();
    }

    @FXML
    void selectBfsRadio(ActionEvent event) {
    	if (bfsRadio.isSelected()) {
    		bfsIsSelected = true;
    		dfsIsSelected = aEucIsSelected = aManIsSelected = false;
    	} else
    		bfsIsSelected = false;
    	optimizeRadioBtns();
    }

    @FXML
    void selectDfsRadio(ActionEvent event) {
    	if (dfsRadio.isSelected()) {
    		dfsIsSelected = true;
    		bfsIsSelected = aEucIsSelected = aManIsSelected = false;
    	} else
    		dfsIsSelected = false;
    	optimizeRadioBtns();
    }
    /*
     * function to make sure that only one radio button is selected (only one algorithm) 
     * to show the selected algorithm path to the goal state 
     */
    private void optimizeRadioBtns() {
    	if (dfsIsSelected) {
    		bfsRadio.setSelected(false);
    		aEucRadio.setSelected(false);
    		aManRadio.setSelected(false);
    		selectedSearchAgent = 0;
    	}
    	if (bfsIsSelected) {
    		dfsRadio.setSelected(false);
    		aEucRadio.setSelected(false);
    		aManRadio.setSelected(false);
    		selectedSearchAgent = 1;
    	}
    	if (aEucIsSelected) {
    		dfsRadio.setSelected(false);
    		bfsRadio.setSelected(false);
    		aManRadio.setSelected(false);
    		selectedSearchAgent = 2;
    	}
    	if (aManIsSelected) {
    		dfsRadio.setSelected(false);
    		bfsRadio.setSelected(false);
    		aEucRadio.setSelected(false);
    		selectedSearchAgent = 3;
    	}
    	if ((dfsIsSelected && dfsIsSolved) || (bfsIsSelected && bfsIsSolved) || 
    			(aEucIsSelected && aEucIsSolved) || (aManIsSelected && aManIsSolved))
    		showResult.setDisable(false); //enabled if any radio button is selected
    	else
    		showResult.setDisable(true); //disabled
    }
    // 0 for dfs, 1 for bfs, 2 for a*euclidean, 3 for a*manhattan
    public ArrayList<TilesState> getGoalPath(int agentNum) {
    	if (agentNum == 0)
    		return dfsGoalPath;
    	else if (agentNum == 1)
    		return bfsGoalPath;
    	else if (agentNum == 2)
    		return aEucGoalPath;
    	else if (agentNum == 3)
    		return aManGoalPath;
    	return null;
    	
    }
    
    public int getSelectedAgent() {
    	return selectedSearchAgent;
    }

    private void clearLabels() {
    	dfsCost.setText(""); 
    	dfsDepth.setText("");
    	dfsMaxDepth.setText("");
    	dfsExpNodes.setText("");
    	dfsVisNodes.setText("");
    	dfsRuntime.setText("");
        bfsCost.setText("");
        bfsDepth.setText("");
        bfsMaxDepth.setText("");
        bfsExpNodes.setText("");
        bfsVisNodes.setText("");
        bfsRuntime.setText("");
        aEucCost.setText("");
        aEucDepth.setText("");
        aEucMaxDepth.setText("");
        aEucExpNodes.setText("");
        aEucVisNodes.setText("");
        aEucRuntime.setText("");
        aManCost.setText("");
        aManDepth.setText("");
        aManMaxDepth.setText("");
        aManExpNodes.setText("");
        aManVisNodes.setText("");
        aManRuntime.setText("");
    }
}
