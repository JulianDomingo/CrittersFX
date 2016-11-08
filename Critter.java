/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Julian Domingo
 * jad5348
 * 16465
 * Ka Tai Ho
 * kh33248
 * 16465
 * Slip days used: <0>
 * Fall 2016
 */

package assignment5;
import java.lang.reflect.Constructor;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	/**
	 * TODO: Implement this method.
	 */
	protected String look(int direction, boolean steps) {
		this.energy = this.getEnergy() - Params.look_energy_cost;
		int spaces = (steps) ? 2 : 1;
			
		List<Critter> critters = (lookingAfterDoTimeStep) ? CritterWorld.getLivingCritters() : oldCoordinates;
		
		for (Critter critter : critters) {
			if (!critter.equals(this)) {
				this.move(spaces, direction);
				if (samePos(this, critter)) {
					return critter.toString();
				}
			}
		}
		return null;
	}
	
	/*
	 * END OF NEW MATERIAL FOR PROJECT 5
	 */
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	
	private static List<Critter> oldCoordinates = new java.util.ArrayList<Critter>();
	private boolean hasMoved;
	private static boolean lookingAfterDoTimeStep;
	private static boolean isFighting;
	
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = Params.start_energy;
	protected int getEnergy() { return energy; }
	
	private int x_coord = Math.abs(getRandomInt(Params.world_width));
	private int y_coord = Math.abs(getRandomInt(Params.world_height));
	
	/**
	 * Moves Critter in specified direction by one space. 
	 * @param direction: direciton that Critter moves in.
	 */
	protected final void walk(int direction) {
		this.energy -= Params.walk_energy_cost;
		
		if (isFighting && !hasMoved) {
			int tempx = this.x_coord;
			int tempy = this.y_coord;
			this.move(1, direction);
			
			for (Critter c : CritterWorld.getLivingCritters()) {
				if (this.x_coord == c.x_coord && this.y_coord == c.y_coord) {
					this.x_coord = tempx;
					this.y_coord = tempy;
					break;
				}
			}
		}
		
		else if (!hasMoved) {
			this.move(1, direction);
			this.hasMoved = true;
		}
	}

	/**
	 * Moves Critter in specified direction by two spaces
	 * @param direction: direction that Critter moves in.
	 */
	protected final void run(int direction) {
		this.energy -= Params.run_energy_cost;
		
		if (isFighting && !hasMoved) {
			int tempx = this.x_coord;
			int tempy = this.y_coord;
			this.move(2, direction);
			
			for (Critter c : CritterWorld.getLivingCritters()) {
				if (this.x_coord == c.x_coord && this.y_coord == c.y_coord) {
					this.x_coord = tempx;
					this.y_coord = tempy;
					break;
				}
			}
		}
		
		if (!hasMoved) {
			this.move(2, direction);
			this.hasMoved = true;
		}
	}
	
	/**
	 * Registers movement of Critter.
	 * @param steps: Number of steps taken by Critter.
	 * @param direction: specified direction of Critter.
	 */
	private void move(int steps, int direction) {
		if (direction == 2) {
			this.y_coord = isSafeY(steps + this.y_coord);
		}
		else if (direction == 1) {
			this.x_coord = isSafeX(this.x_coord + steps);
			this.y_coord = isSafeY(this.y_coord + steps);
		}
		else if (direction == 0) {
			this.x_coord = isSafeX(this.x_coord + steps);
		}
		else if (direction == 7) {
			this.x_coord = isSafeX(this.x_coord + steps);
			this.y_coord = isSafeY(this.y_coord - steps);
		}
		else if (direction == 6) {
			this.y_coord = isSafeY(this.y_coord - steps);
		}
		else if (direction == 5) {
			this.x_coord = isSafeX(this.x_coord - steps);
			this.y_coord = isSafeY(this.y_coord - steps);
		}
		else if (direction == 4) {
			this.x_coord = isSafeX(this.x_coord - steps);
		}
		else if (direction == 3) {
			this.x_coord = isSafeX(this.x_coord - steps);
			this.y_coord = isSafeY(this.y_coord + steps);
		}
	}
	
	/**
	 * Returns new X coordinate for Critter.
	 * @param dist: coordinate to evaluate.
	 * @return new coordinate
	 */
	private int isSafeX(int dist) {
		if (dist < 0) {
			return Params.world_width - 1;
		}
		else if (dist > Params.world_width - 1) {
			return 0;
		}
		
		return dist;
	}
	
	/**
	 * Returns new Y coordinate for Critter.
	 * @param dist: coordinate to evaluate.
	 * @return new coordinate
	 */
	private int isSafeY(int dist) {
		if (dist < 0) {
			return Params.world_height - 1;
		}
		else if (dist > Params.world_height - 1) {
			return 0;
		}
		
		return dist;
	}
	
	/**
	 * Generates a new Critter in the adjacent direction of the invoking Critter.
	 * @param offspring: Critter type to reproduce
	 * @param direction: adjacent direction for child Critter to spawn.
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy < Params.min_reproduce_energy) {
			return;
		}
		
		offspring.energy = this.energy / 2;
		this.energy = (int) Math.ceil( ((double)this.energy) / 2);
		
		offspring.move(1, direction);
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		
		CritterWorld.addBabies(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		
		Class<?> myCritter = null;
		Constructor<?> constructor = null;
		Object instanceOfMyCritter = null;

	    try {
			myCritter = Class.forName("assignment5." + critter_class_name);
		    constructor = myCritter.getConstructor(); // get null parameter constructor
		    instanceOfMyCritter = constructor.newInstance(); // create instance
		    Critter me = (Critter) instanceOfMyCritter; // cast to Critter
		    
		    me.x_coord = getRandomInt(Params.world_width);
		    me.y_coord = getRandomInt(Params.world_height);
		    
		    CritterWorld.addCritter(me);
	    }
		catch (Exception e) {
			if (e instanceof ClassNotFoundException)
				throw new InvalidCritterException(critter_class_name);
			else
				e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		
		for (Critter critter : CritterWorld.getLivingCritters()) {
			if (critter.getClass().toString().contains(critter_class_name)) {
				result.add(critter);
			}
		}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		
		String prefix = "";
		
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return CritterWorld.getLivingCritters();
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return CritterWorld.getBabies();
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		CritterWorld.clearWorld();
	}
	
	/**
	 * checks if two critters have the same position
	 * @param crit1 first Critter to compare
	 * @param crit2 second Critter to compare
	 * @return if they have the same position
	 */
	private static boolean samePos(Critter crit1, Critter crit2) {
		if (crit1.x_coord == crit2.x_coord &&
			crit1.y_coord == crit2.y_coord) 
		{
			return true;
		}
		return false;
	}
	
	/**
	 * spawns Algae into the world
	 */
	private static void spawnAlgae() {
		for (int i = 0; i < Params.refresh_algae_count; i++) {
			try {
				makeCritter("Algae");
			}
			catch(InvalidCritterException e) {
				e.printStackTrace();
			}
			
			CritterWorld.getWorld()[Math.abs(getRandomInt(Params.world_height) - 1)][Math.abs(getRandomInt(Params.world_width - 1))] = new Algae();
		}
	}
	
	/**
	 * Deducts rest energy from Critters
	 */
	private static void deductRestEnergy() {
		for (Critter c: CritterWorld.getLivingCritters()) {
			c.energy -= Params.rest_energy_cost;
			c.hasMoved = false;
		}
	}
	
	/**
	 * Handles Critters that are on the same spot
	 * invoke fight
	 */
	private static void handleEncounters() {
		isFighting = true;
		for (Critter thisCrit : CritterWorld.getLivingCritters()) {
			for (Critter otherCrit : CritterWorld.getLivingCritters()) {
				if (thisCrit.equals(otherCrit)) {
					continue;
				}
				else if (samePos(thisCrit, otherCrit) &&
						 thisCrit.getEnergy() > 0 &&
						 otherCrit.getEnergy() > 0)
				{
					boolean thisAlive = true;
					boolean otherAlive = true;
					
					boolean thisWantsToFight = thisCrit.fight(otherCrit.toString());
					if (thisCrit.getEnergy() <= 0) {
						thisAlive = false;
						thisCrit.energy = 0;
					}
					boolean otherWantsToFight = otherCrit.fight(thisCrit.toString());
					if (otherCrit.getEnergy() <= 0) {
						otherAlive = false;
						otherCrit.energy = 0;
					}
					
					if (samePos(thisCrit, otherCrit) &&
						thisAlive && 
						otherAlive) 
					{
						// roll dice
						int thisEnergy = thisCrit.energy;
						int otherEnergy = otherCrit.energy;
						
						int thisRoll = thisWantsToFight ? getRandomInt(thisCrit.getEnergy()) : 0;
						int otherRoll = otherWantsToFight ? getRandomInt(otherCrit.getEnergy()) : 0;
						
						// Settle an equal roll.
						if (thisRoll == otherRoll) {							
							if (getRandomInt(2) == 1) {
								thisRoll += 1;
							}
							else {
								otherRoll += 1;
							} 
						}
						
						if (thisRoll > otherRoll) {
							thisAlive = true;
							otherCrit.energy = 0;
							thisCrit.energy += otherEnergy / 2;
						}
						else if (thisRoll < otherRoll) {
							thisCrit.energy = 0;
							otherAlive= true;
							otherCrit.energy += thisEnergy / 2;
						}
					}
				}	
			}
		}
		isFighting = false;
	}
	
	/**
	 * Keeps track of all living critter's coordinates BEFORE
	 * their respective doTimeSteps().
	 */
	private static void registerOldCoordinates() {
		for (Critter c : CritterWorld.getLivingCritters()) {
			oldCoordinates.add(c);
		}
	}
	
	/**
	 * runs every Critters doTimeStep
	 * updates the world
	 * updates LivingCritters
	 * updates energy
	 * spawns Algae
	 * updates babies
	 * removes dead Critters
	 */
	public static void worldTimeStep() {
		lookingAfterDoTimeStep = false;
		registerOldCoordinates();
		for (Critter c : CritterWorld.getLivingCritters()) {
			c.doTimeStep();
		}
		lookingAfterDoTimeStep = true;
		
		populateWorld();
		handleEncounters();
		deductRestEnergy();
		spawnAlgae();
		CritterWorld.babiesToCritters();
		CritterWorld.removeDead();
	}
	
	/**
	 * updates the Living Critters in the World 
	 * and clears the old ones
	 */
	private static void populateWorld() {
		CritterWorld.newWorld();
		
		for (Critter c : CritterWorld.getLivingCritters()) {
			CritterWorld.getWorld()[c.y_coord][c.x_coord] = c;
		}
	}
	
	/**
	 * Displays the Current state of the World
	 */
	static double size = 10.0;
	public static void displayWorld() {
		
		populateWorld();
		
		Main.gridPane = new GridPane();
			
		for (Critter living : CritterWorld.getLivingCritters()) {
			// TODO: Make unique shape / color for each critter.
			// Main.grid.add(getCritterShape(living), living.x_coord, living.y_coord);
			Main.gridPane.add(new Circle(10.0), living.x_coord, living.y_coord);
		}
		
		Main.worldScene = new Scene(Main.gridPane, 600, 400);
		Main.worldStage.setScene(Main.worldScene);
		Main.worldStage.show();
	} 
	
//	static Shape getCritterShape(Critter c) {
//		Shape s = null;
//		
//		switch (c.viewShape()) {
//			case CIRCLE: s = new Circle(10.0);
//						 break;
//			case SQUARE: s = new Rectangle(10.0, 10.0);
//						 break;
//			// case TRIANGLE: s = new Triangle(10.0, 10.0);
//			case TRIANGLE: s = (Polygon) new Polygon();	
//						   
//		}
//		s.setFill(c.viewColor());
//		s.setStroke(c.viewOutlineColor());
//		return s;	
//	}
	
	
	/**
	 * 	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	 */
	
	
}

