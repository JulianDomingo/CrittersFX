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
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import assignment5.Critter.CritterShape;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Main extends Application{
	private static World world;
	private static ArrayList<String> critterNames = new ArrayList<String>();
	
	
	public static void main(String[] args) {
		
		List<String> results = new ArrayList<String>();
		
		String filePath = "/Users/KaTaiHo/Documents/workspace/Critters_2/src/assignment5";
		File[] files = new File(filePath).listFiles();
		
		for (File file : files) {
			if (file.toString().endsWith(".java")) {
				String fileName = file.toString().substring(filePath.length() + 1, file.toString().length() - 5);
				Class<?> myCritter = null;
				Constructor<?> constructor = null;
				Object instanceOfMyCritter = null;
				try {
					myCritter = Class.forName("assignment5." + fileName);
				    constructor = myCritter.getConstructor(); // get null parameter constructor
				    instanceOfMyCritter = constructor.newInstance(); // create instance
				    Critter me = (Critter) instanceOfMyCritter; // cast to Critter
				    System.out.println(fileName);
				    critterNames.add(fileName);
			    }
				catch (Exception e) {
					
				}
			}
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
	
		launch(args);
	}
	
	class World {
		Stage worldStage;
		World() {
			worldStage = new Stage();
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
        
		ObservableList<String> options = FXCollections.observableArrayList();
		for (String s: critterNames) {
			options.add(s);
		}
		
	    final ComboBox<String> comboBox = new ComboBox<String>(options);
		
		TextField tf = new TextField("#");
		tf.setPrefWidth(50);
		
		Rectangle display = new Rectangle(800,900,410,100);
		display.setStroke(Color.LIGHTGREY);
		display.setFill(Color.BLACK);
		
		Button makeButton = new Button();
		makeButton.setOnAction((ActionEvent event)-> {
			String inputText = tf.getText();
			Integer temp = 1;
			
			if (inputText != null && !inputText.equals("#")) {
				temp = Integer.valueOf(inputText);
				
			}
			for (int i = 0; i < temp; i++) {
				try {
					Critter.makeCritter(comboBox.getSelectionModel().getSelectedItem().toString());
					System.out.println(comboBox.getSelectionModel().getSelectedItem().toString());
				}
				catch(InvalidCritterException e) {
						
				}
			}
			System.out.println(CritterWorld.getPopulation());
		});
		
		Button runStatsButton = new Button();
		runStatsButton.setOnAction((ActionEvent event)-> {
			String critterName = comboBox.getSelectionModel().getSelectedItem().toString();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				List<Critter> critters = Critter.getInstances(critterName);
				Class<?> classes = Class.forName("assignment5." + critterName);
				classes.getMethod("runStats", java.util.List.class).invoke(null, critters);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			runStatsOutput.setText(baos.toString());
		});

        Button stepButton = new Button();
        stepButton.setOnAction((ActionEvent event)-> {
        	String inputText = tf.getText();
        	
        	
        	if (inputText != null && !inputText.equals("#")) {
        		Integer temp = Integer.valueOf(inputText);
    			for (int i = 0; i < temp; i++) {
    				//System.out.println("stepping");
    				Critter.worldTimeStep();
    			}
        	}
        	else {
        		Critter.worldTimeStep();
        		//System.out.println("step once");
        	}
		});
        
        
        Button displayButton = new Button();
        displayButton.setOnAction((ActionEvent event)-> Critter.displayWorld());
        
        
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
        grid.add(comboBox, 3, 3, 1, 1);
        grid.add(titleCritter, 3, 0);
        
        grid.setStyle("-fx-background-color: black");
        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setX(170);
        primaryStage.setY(150);
        primaryStage.show();
    	System.out.print(critterNames.toString());
        world = new World();
	}
}
