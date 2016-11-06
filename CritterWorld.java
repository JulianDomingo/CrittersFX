package assignment5;

/* CRITTERS <CritterWorld.java>
 * EE422C Project 4 submission by
 * 
 * Julian Domingo
 * jad5348
 * 16465
 * Ka Tai Ho
 * kh33248
 * 16465
 * Slip days used: <1>
 * Fall 2016
 */

import java.util.*;


public class CritterWorld {
	private static LinkedList<Critter> livingCritters = new LinkedList<Critter>();
	private static ArrayList<Critter> babies = new ArrayList<Critter>();
	private static Critter[][] world = new Critter[Params.world_height][Params.world_width];
	
	/**
	 * 
	 * @return the number of Living Critters
	 */
	public static int getPopulation() {
		return livingCritters.size();
	}
	
	/**
	 * 
	 * @return the 2d Array World
	 */
	public static Critter[][] getWorld() {
		return world;
	}
	
	/**
	 * Adds a new Critter to our list of Critters
	 * @param c The critter object to be added
	 */
	public static void addCritter(Critter c) {
		livingCritters.add(c);
	}
	
	/**
	 * Adds a new baby Critter to our list of babies
	 * @param c the critter baby object to be added
	 */
	public static void addBabies(Critter c) {
		babies.add(c);
	}
	
	/**
	 * gets the LinkedList of living Critters
	 * @return the LinkedList of living Critters
	 */
	public static LinkedList<Critter> getLivingCritters () {
		return livingCritters;
	}
	
	/**
	 * gets the ArrayList of babies
	 * @return the ArrayList of baby Critters
	 */
	public static ArrayList<Critter> getBabies() {
		return babies;
	}
	
	/**
	 * removes all the dead Critters in the LivingCritters
	 */
	public static void removeDead() {
		LinkedList<Critter> step = CritterWorld.getLivingCritters();
		Iterator<Critter> itr = step.iterator();
		Critter next;
		
		while (itr.hasNext()) {
			next = itr.next();
			
			if(next.getEnergy() <= 0){
				itr.remove();
			}
		}
	}
	
	/** 
	 * turns all of the current baby Critters to adult Critters
	 * by adding the baby critters from our baby List 
	 * into our Critter LinkedList
	 */
	public static void babiesToCritters() {
		for (Critter c : babies) {
			addCritter(c);
		}
		
		babies = new ArrayList<Critter>(0);
	}
	
	/**
	 * clears the old world and sets the new World to an empty 2d Array
	 */
	public static void newWorld() {
		world = new Critter[Params.world_height][Params.world_width];
	}
	
	/**
	 * clears everything inside the world.
	 */
	public static void clearWorld() {
		world = new Critter[Params.world_height][Params.world_width];
		livingCritters = new LinkedList<Critter>();
		babies = new ArrayList<Critter>();
	}
}
