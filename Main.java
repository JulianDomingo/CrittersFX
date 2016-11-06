package assignment5;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import assignment5.Critter.CritterShape;
import javafx.application.Application;


public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Critters Controller");
		
		Label runStatsOutput = new Label();
		runStatsOutput.setTextFill(Color.WHITE);
		
		TextField tf = new TextField("#");
		tf.setPrefWidth(50);
		
		Rectangle display = new Rectangle(800,900,385,100);
		display.setStroke(Color.GREY);
		display.setFill(Color.LIGHTGREY);
		

		
		
		Button makeButton = new Button();
		
		Button runStatsButton = new Button();
		runStatsButton.setOnAction((ActionEvent event)-> {
			runStatsOutput.setText("omg it worked");
		});

        Button stepButton = new Button();
        stepButton.setOnAction((ActionEvent event)-> {
			Critter.worldTimeStep();
		});
        
        
        Button quitButton = new Button();
        
        makeButton.setText("Make");
        runStatsButton.setText("runStats");
        quitButton.setText("Quit");
        stepButton.setText("Step");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        grid.setPadding(new Insets(25,25,25,25));
        
        grid.add(display, 0, 0, 7, 1);
        grid.add(runStatsOutput, 0, 0, 2, 1);
        grid.add(makeButton, 1, 2);
        grid.add(stepButton, 2, 2);
        grid.add(runStatsButton, 3, 2);
        grid.add(quitButton, 4, 2);
        grid.add(tf, 5, 2);
        
        grid.setStyle("-fx-background-color: black");
        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        
        primaryStage.show();
	}
}
