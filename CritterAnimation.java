package assignment5;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javafx.scene.control.TextArea;

public class CritterAnimation extends TimerTask{
	private double frameSpeed;
	private ArrayList<String> critterStats;
	private TextArea txt;
	public CritterAnimation(Double frameSpeed, ArrayList<String> crits, TextArea textarea) {
		this.frameSpeed = frameSpeed;
		critterStats = crits;
		txt = textarea;
	}
	
	public void run() {
		for (int i = 0; i < frameSpeed; i++) {
			Critter.worldTimeStep();
		}
		String tempString = "";
		
		for (String s: critterStats) {
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
		txt.setText(tempString);
		
		//Critter.displayWorld();
		
	}
}
