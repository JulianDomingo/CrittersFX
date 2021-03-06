package assignment5;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import assignment5.Critter.CritterShape;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Main extends Application{
	private static World world;
	private static ArrayList<String> critterNames = new ArrayList<String>();
	private static ArrayList<String> runStatsArr = new ArrayList<String>();
	private static Timer timer = new Timer();
	
    static GridPane gridPane;
    static Canvas worldCanvas;
    static Stage worldStage;

	public static void main(String[] args) {
		 
		List<String> results = new ArrayList<String>();
		
		String filePath = "/Users/KaTaiHo/Documents/workspace/Critter3/src/assignment5";
		//String filePath = "/Users/User/422C/assignment5/src/assignment5";
		File[] files = new File(filePath).listFiles();
		
		for (File file : files) {
			if (file.toString().endsWith(".java")) {
				String fileName = file.toString().substring(filePath.length() + 1, file.toString().length() - 5);
				try {
						Class<?> myCritter = Class.forName("assignment5.Critter");
						Class<?> myClasses = Class.forName("assignment5." + fileName);
					    
					    if(!fileName.equals("Critter") && myCritter.isAssignableFrom(myClasses)){
					    	critterNames.add(fileName);
					    }
					    
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
			
			Scene worldScene = new Scene(root, 550, 400);		
						
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
		
		ObservableList<String> options = FXCollections.observableArrayList();
		for (String s: critterNames) {
			options.add(s);
		}
		
	    final ComboBox<String> comboBox = new ComboBox<String>(options);
		
		TextField tf = new TextField("#");
		tf.setPrefWidth(50);
		
		Label animationText = new Label();
		animationText.setFont(Font.font ("Verdana", 15));
		animationText.setText("Animation Speed");
		animationText.setAlignment(Pos.CENTER);
		animationText.setMaxWidth(Double.MAX_VALUE);
		animationText.setTextFill(Color.WHITE);
		
		ScrollBar scrollBar = new ScrollBar();
		scrollBar.setMin(0);
		scrollBar.setMax(500);
		scrollBar.setValue(1);
		
		
		TextArea textArea = new TextArea();
		textArea.setPrefRowCount(500);
		textArea.setPrefColumnCount(400);
		textArea.setWrapText(true);
		textArea.setEditable(false);
		textArea.setText("runStats will appear here : D");
		
		
		Button makeButton = new Button();
		makeButton.setOnAction((ActionEvent event)-> {
			String inputText = tf.getText();
			Integer temp = 1;
			
			if (inputText != null && !inputText.equals("#")) {
				temp = Integer.valueOf(inputText);
				
			}
			for (int i = 0; i < temp; i++) {
				try { 
					String tempString = comboBox.getSelectionModel().getSelectedItem().toString();				
					Critter.makeCritter(tempString);
					
				}
				catch(Exception e) {
						
				}
			}
		});
		
		Button makeAllButton = new Button();
		makeAllButton.setOnAction((ActionEvent event)-> {
			String inputText = tf.getText();
			Integer temp = 1;
			if (inputText != null && !inputText.equals("#")) {
				temp = Integer.valueOf(inputText);
			}
			for (String s: critterNames) {
				for (int i = 0; i < temp; i++) {
					try { 
						if (!temp.equals("AlgaephobicCritter")) {					
							Critter.makeCritter(s);
						}
						
					}
					catch(Exception e) {
							
					}
				}
			}
		});
		
		Button runStatsButton = new Button();
		runStatsButton.setOnAction((ActionEvent event)-> {
			if (!comboBox.getSelectionModel().isEmpty()) {
				 // Create a stream to hold the output
			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    PrintStream ps = new PrintStream(baos);
			    // IMPORTANT: Save the old System.out!
			    PrintStream old = System.out;
			    // Tell Java to use your special stream
			    System.setOut(ps);
			    // Print some output: goes to your special stream
				String critterName = comboBox.getSelectionModel().getSelectedItem().toString();		    
				
				try {
					List<Critter> critters = Critter.getInstances(critterName);
					Class<?> classes = Class.forName("assignment5." + critterName);
					classes.getMethod("runStats", java.util.List.class).invoke(null, critters);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				textArea.setText(baos.toString());
			}
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
        
        Button addButton = new Button();
        addButton.setOnAction((ActionEvent event)->{
        	if (!comboBox.getSelectionModel().isEmpty()) {
        		runStatsArr.add(comboBox.getSelectionModel().getSelectedItem().toString());
        	}	
        	
        	System.out.println(runStatsArr.toString());
        });
        
        Button addAllButton = new Button();
        addAllButton.setOnAction((ActionEvent event)-> {
        	for (String s: critterNames) {
        		runStatsArr.add(s);
        	}
        });
        
        Button startAnimationButton = new Button();
        Button stopAnimationButton = new Button();
		
		startAnimationButton.setOnAction((ActionEvent event)-> {
			makeButton.setDisable(true);
			runStatsButton.setDisable(true);
			displayButton.setDisable(true);
			stepButton.setDisable(true);
			comboBox.setDisable(true);
			tf.setDisable(true);
			addButton.setDisable(true);
			makeAllButton.setDisable(true);
			addAllButton.setDisable(true);
			Double frameSpeed = scrollBar.getValue();
				
			timer = new Timer();
			timer.schedule(new TimerTask() {
				public void run() {
					Platform.runLater(new Runnable() {
						public void run() {
							for (int i = 0; i < frameSpeed; i++) {
								Critter.worldTimeStep();
							}
							String tempString = "";
							
							for (String s: runStatsArr) {
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
							    PrintStream ps = new PrintStream(baos);
							    // IMPORTANT: Save the old System.out!
							    PrintStream old = System.out;
							    // Tell Java to use your special stream
							    System.setOut(ps);
							    // Print some output: goes to your special stream	    
								
								try {
									List<Critter> critters = Critter.getInstances(s);
									Class<?> classes = Class.forName("assignment5." + s);
									classes.getMethod("runStats", java.util.List.class).invoke(null, critters);
								}
								catch (Exception e) {
									e.printStackTrace();
								}
								tempString += baos.toString();
							}
							textArea.setText(tempString);
							
							Critter.displayWorld();							
						}
					});
				}		 
			}, 100, 100);
		});
		
		stopAnimationButton.setOnAction((ActionEvent event)->{
			makeButton.setDisable(false);
			runStatsButton.setDisable(false);
			displayButton.setDisable(false);
			stepButton.setDisable(false);
			comboBox.setDisable(false);
			tf.setDisable(false);
			addButton.setDisable(false);
			makeAllButton.setDisable(false);
			addAllButton.setDisable(false);
			timer.cancel();
			runStatsArr.clear();
		});	
		
        makeButton.setText("Make");
        runStatsButton.setText("runStats");
        quitButton.setText("Quit");
        stepButton.setText("Step");
        displayButton.setText("Show");
        startAnimationButton.setText("Animation Start");
        stopAnimationButton.setText("Animation stop");
        addButton.setText("add");
        makeAllButton.setText("Make All");
        addAllButton.setText("Add All");
        
        makeButton.setMaxWidth(Double.MAX_VALUE);
        runStatsButton.setMaxWidth(Double.MAX_VALUE);
        quitButton.setMaxWidth(Double.MAX_VALUE);
        stepButton.setMaxWidth(Double.MAX_VALUE);
        displayButton.setMaxWidth(Double.MAX_VALUE);
        startAnimationButton.setMaxWidth(Double.MAX_VALUE);
        stopAnimationButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setMaxWidth(Double.MAX_VALUE);
        comboBox.setMaxWidth(Double.MAX_VALUE);
        makeAllButton.setMaxWidth(Double.MAX_VALUE);
        addAllButton.setMaxWidth(Double.MAX_VALUE);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        grid.setPadding(new Insets(25,25,25,25));
        
        grid.add(textArea, 1, 0, 7, 1);
        
        grid.add(runStatsOutput, 1, 1, 2, 1);
        grid.add(makeButton, 1, 2);
        grid.add(stepButton, 2, 2);
        grid.add(runStatsButton, 3, 2, 2, 1);
        grid.add(quitButton, 5, 2);
        grid.add(startAnimationButton, 1, 3, 2, 1);
        grid.add(stopAnimationButton, 5, 3, 3, 1);
        grid.add(comboBox, 3, 3, 2, 1);
        grid.add(displayButton, 3, 4, 2, 1);
        grid.add(tf, 6, 2, 2, 1);
       
        grid.add(animationText, 3, 5, 2, 1);
        grid.add(scrollBar, 1, 6, 7, 1);
        grid.add(addButton, 5, 4);
        grid.add(makeAllButton, 1, 4, 2, 1);
        grid.add(addAllButton, 1, 5, 2, 1);
        
        grid.setStyle("-fx-background-color: black");
        Scene scene = new Scene(grid, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.setX(120);
        primaryStage.setY(50);
        primaryStage.show();
        
        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: black");
        worldStage = new Stage();
        worldStage.setTitle("Critter World");
        
        worldCanvas = new Canvas(Params.world_width * 5.0, Params.world_height * 5.0);
        gridPane.getChildren().add(worldCanvas);
        worldStage.setScene(new Scene(gridPane, Params.world_width * 5.0, Params.world_height * 5.0));
        worldStage.setX(720);
        worldStage.setY(50);
        worldStage.show();	
	}
}
