/* CRITTERS <MyCritter2.java>
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
/**
 * MyCritter2 imitates the Biblethump twitch emoji by being a lil' wuss. MyCritter2
 * only fights if the enemy is an algae. Otherwise, it runs away. Compared to other
 * Critters, MyCritter2 has a number of cries that is being kept tracked of.
 */
package assignment5;

import java.util.ArrayList;

import assignment5.Critter.CritterShape;

public class MyCritter2 extends Critter {
	
	// MyCritter2 fields
	private int cries;
	private int direction;
	private int age;

	/**
	 * Prints character representation of MyCritter2
	 */
	@Override
	public String toString() { return "2"; }
	
	/**
	 * MyCritter2 Constructor
	 */
	public MyCritter2() {
		cries = 0;
		age = 1;
		direction = Critter.getRandomInt(8);
	}
	
	/**
	 * Fights the enemy 'String'.
	 * If energy is <= 90, runs away.
	 * Otherwise, randomly determines if it wants to fight.
	 */
	public boolean fight(String enemy) {
		if (enemy.equals("Algae")) {
			return true;
		} 
		else if (this.getEnergy() <= 90) {
			// What a wuss! 
			run(Critter.getRandomInt(8));
			return false;
		}
		else if (enemy.equals("MyCritter2")) {
			int decision = Critter.getRandomInt(2);
			return (decision == 1) ? true : false;
		}
		else { 
			return false;
		}
	} 

	/**
	 * Always cries. If energy is >= 70, depressingly walks.
	 * Otherwise, reproduces and runs away like a pus pus
	 */
	@Override
	public void doTimeStep() {	
		age += 1;
		if (this.getEnergy() >= 130) {
			cries += 1;
			direction = Critter.getRandomInt(8);
			walk(direction);
		} 
		else if (this.getEnergy() < 130 && this.getEnergy() >= 70) {
			cries += 2;
			direction = (2 * Critter.getRandomInt(8)) % 8;
			walk(direction);
		}
		else {
			cries += 5; 
			MyCritter2 child = new MyCritter2();
			reproduce(child, Critter.getRandomInt(8));
			run(Critter.getRandomInt(8));
		}
	}
	
	/**
	 * Prints out number of cries and living MyCritter2's to console.
	 * @param MyCritter2
	 */
	public static void runStats(java.util.List<Critter> MyCritter2) {
		int oldest = 0;
		int numCries = 0;
		int numBibleThumps = 0;
		
		for (Object obj : MyCritter2) {
			MyCritter2 b = (MyCritter2) obj;
			numBibleThumps += 1; 
			numCries += b.cries;
			if (oldest < b.age) {
				oldest = b.age;
			}
		} 
		
		System.out.println("Total Living Biblethumps: " + numBibleThumps);
		System.out.println("Total Biblethump Cries: " + numCries);
		System.out.println("Oldest Biblethump: " + oldest);
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.TRIANGLE;
	} 
	
	@Override
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.TOMATO;
	}
	
	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return javafx.scene.paint.Color.BLACK;
	}
}
