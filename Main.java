package assignment5;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;

import assignment5.Critter.CritterShape;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Main extends Application{
	
	class World {
		World() {
			Stage worldStage = new Stage();
			worldStage.setTitle("Critters World");
	
			GridPane root = new GridPane();
			Canvas worldCanvas = new Canvas(550, 550);
			root.setAlignment(Pos.CENTER);
			
			root.getChildren().add(worldCanvas);
			
			Scene worldScene = new Scene(root, 600, 400);		
						
			worldStage.setScene(worldScene);
			worldStage.setX(670);
			worldStage.setY(150);
			worldStage.show();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Critters Controller");
		
		Label runStatsOutput = new Label();
		runStatsOutput.setTextFill(Color.WHITE);
		
		Label titleCritter = new Label();
		titleCritter.setText("Critters");
		titleCritter.setAlignment(Pos.CENTER);
		titleCritter.setMaxWidth(Double.MAX_VALUE);
		titleCritter.setTextFill(Color.rgb(50, 205, 50));
		
		TextField tf = new TextField("#");
		tf.setPrefWidth(50);
		
		Rectangle display = new Rectangle(800,900,420,100);
		display.setStroke(Color.LIGHTGREY);
		display.setFill(Color.BLACK);
		
		Button makeButton = new Button();
		
		Button runStatsButton = new Button();
		runStatsButton.setOnAction((ActionEvent event)-> {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			runStatsOutput.setText("omg it worked");
		});

        Button stepButton = new Button();
        stepButton.setOnAction((ActionEvent event)-> {
			Critter.worldTimeStep();
		});
        
        
        Button displayButton = new Button();
        //displayButton.setOnAction((ActionEvent event)-> new World());
       
        
        ObservableList<String> options = 
        	    FXCollections.observableArrayList(
        	        "Option 1",
        	        "Option 2",
        	        "Option 3"
        	    );
        final ComboBox comboBox = new ComboBox(options);
        
        
        Button quitButton = new Button();
        quitButton.setOnAction((ActionEvent event)-> System.exit(0));
        
        makeButton.setText("Make");
        runStatsButton.setText("runStats");
        quitButton.setText("Quit");
        stepButton.setText("Step");
        displayButton.setText("Show");
        
        makeButton.setMaxWidth(Double.MAX_VALUE);
        runStatsButton.setMaxWidth(Double.MAX_VALUE);
        quitButton.setMaxWidth(Double.MAX_VALUE);
        stepButton.setMaxWidth(Double.MAX_VALUE);
        displayButton.setMaxWidth(Double.MAX_VALUE);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        grid.setPadding(new Insets(25,25,25,25));
        
        grid.add(display, 0, 0, 7, 1);
        grid.add(runStatsOutput, 1, 0, 2, 1);
        grid.add(makeButton, 1, 2);
        grid.add(stepButton, 2, 2);
        grid.add(runStatsButton, 3, 2);
        grid.add(quitButton, 4, 2);
        grid.add(tf, 5, 2);
        grid.add(displayButton, 3, 4);
        grid.add(comboBox, 3, 3);
        grid.add(titleCritter, 3, 0);
        
        grid.setStyle("-fx-background-color: black");
        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setX(170);
        primaryStage.setY(150);
        primaryStage.show();
        new World();
	}
}
