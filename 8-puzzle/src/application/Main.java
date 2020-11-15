package application;
	
import java.util.Stack;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.BfsAgent;
import model.DfsAgent;
import model.SearchAgent;
import model.TilesState;
import model.TilesStatesFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		/*try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		 /*primaryStage.setTitle("Hello World!");
	        Button btn = new Button();
	        btn.setText("Say 'Hello World'");
	        btn.setOnAction(new EventHandler<ActionEvent>() {
	 
	            @Override
	            public void handle(ActionEvent event) {
	                System.out.println("Hello World!");
	            }
	        });
	        
	        StackPane root = new StackPane();
	        root.getChildren().add(btn);
	        primaryStage.setScene(new Scene(root, 300, 250));
	        primaryStage.show();
	        */
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/mainPage.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("8 puzzle");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//test bfs
	private static void testBFS(TilesState initState) {
		SearchAgent bfs = new BfsAgent();
		bfs.setInitialState(initState);
		boolean bfsSearch = bfs.performSearch();
		Stack<TilesState> path = bfs.pathToGoal();
		if (bfsSearch) {
			for (int i = 0; i < path.size(); i++) {
				System.out.println(path.get(i).getValue() + " ");
			}
		}
		System.out.println("bfs is " + bfsSearch + " with runtime " + bfs.runningTime() + 
				" and cost " + bfs.pathCost() + " and depth " + bfs.searchDepth() +
				" and expanded " + bfs.nodesExpanded() + " and visited " + bfs.nodesVisited());
	}
	//test dfs
	private static void testDFS(TilesState initState) {
		SearchAgent dfs = new DfsAgent();
		dfs.setInitialState(initState);
		boolean dfsSearch = dfs.performSearch();
		//Stack<TilesState> path = dfs.pathToGoal();
		/*if (dfsSearch) {
			for (int i = 0; i < path.size(); i++) {
				System.out.print(path.get(i).getValue() + " ");
			}
		}*/
		System.out.println("dfs is " + dfsSearch + " with runtime " + dfs.runningTime() +
				" and cost " + dfs.pathCost() + " and depth " + dfs.searchDepth() + " and expanded " + dfs.nodesExpanded());
	}
	private static void test() {
		TilesState initState = TilesStatesFactory.getInstance().createInitialState(125349678);
		//TilesState initState = TilesStatesFactory.getInstance().createRandomInitialState();
		testBFS(initState);
		testDFS(initState);
		/*initState = TilesStatesFactory.getInstance().createRandomInitialState();
		testBFS(initState);
		testDFS(initState);
		initState = TilesStatesFactory.getInstance().createRandomInitialState();
		testBFS(initState);
		testDFS(initState);
		initState = TilesStatesFactory.getInstance().createRandomInitialState();
		testBFS(initState);
		testDFS(initState);*/
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
