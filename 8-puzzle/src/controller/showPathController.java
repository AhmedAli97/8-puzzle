package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.TilesState;
import model.TilesStatesManager;

public class showPathController implements Initializable{

	// indexes of the 3x3 matrix representing the puzzle
    @FXML
    private TextField index0, index1, index2, index3, index4, index5, index6, index7, index8;

    @FXML
    private Button previousBtn, nextBtn;
    
    private ArrayList<TilesState> pathToGoal = new ArrayList<TilesState>();
    ArrayList<TextField> puzzleState = new ArrayList<TextField>();
    TilesStatesManager stateManagerInst;
    int currState;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	mainPageController mainp = new mainPageController();
		pathToGoal = mainp.getGoalPath(mainp.getSelectedAgent());
		puzzleState.add(0, index0);
		puzzleState.add(1, index1);
		puzzleState.add(2, index2);
		puzzleState.add(3, index3);
		puzzleState.add(4, index4);
		puzzleState.add(5, index5);
		puzzleState.add(6, index6);
		puzzleState.add(7, index7);
		puzzleState.add(8, index8);
		stateManagerInst = TilesStatesManager.getInstance();
		currState = 0;
		showState(pathToGoal.get(currState));
	}
    
    @FXML
    void getNextPath(ActionEvent event) {
    	currState++;
    	showState(pathToGoal.get(currState));
    }

    @FXML
    void getPreviousPath(ActionEvent event) {
    	currState--;
    	showState(pathToGoal.get(currState));
    }
    
    private void showState(TilesState state) {
    	for (int i = 0; i < puzzleState.size(); i++) {
    		int indexValue = stateManagerInst.getNumberAt(state, i);
    		if (indexValue != 9)
    			puzzleState.get(i).setText(Integer.toString(indexValue));
    		else
    			puzzleState.get(i).setText("");
    	}
    	if (currState == 0) {
    		previousBtn.setDisable(true);
    		nextBtn.setDisable(false);
    	} else if (currState == pathToGoal.size() - 1) {
    		previousBtn.setDisable(false);
    		nextBtn.setDisable(true);
    	} else {
    		previousBtn.setDisable(false);
    		nextBtn.setDisable(false);
    	}
    }

	

}
