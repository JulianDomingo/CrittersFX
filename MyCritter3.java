package assignment5;

/* CRITTERS <MyCritter3.java>
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
 * MyCritter3 imitates the Pogchamp twitch emoji by making big plays. Actually,
 * MyCritter3 has a field pogFights that is incremented every time MyCritter3
 * does a big play in its fight method. MyCritter3 only reproduces when it is
 * about to die, because MyCritter3 needs more time to make big plays like Faker.
 */

import java.util.ArrayList;

import assignment5.Critter.CritterShape;

/**
 * Pogchamp critter 
 * @author User
 *
 */

public class MyCritter3 extends Critter {
	
	// MyCritter3 fields
	private int feelsLikeIt;
	private int direction;
	private int pogFights;
	private int age;
	
	/**
	 * Returns character representation of MyCritter3.
	 */
	@Override
	public String toString() { return "3"; }
	
	public MyCritter3() {
		age = 0;
		feelsLikeIt = getRandomInt(2);
		pogFights = 0;
		direction = Critter.getRandomInt(8);
	}

	/**
	 * If it feels like fighting, it fights. Otherwise,
	 * it runs away.
	 */
	public boolean fight(String enemy) {
		feelsLikeIt = getRandomInt(2);
		if (enemy.equals("Algae")) {
			return true;
		}
		else if (feelsLikeIt == 1) {
			if (getEnergy() >= 70) {
				pogFights += 1;
				return true;
			}
			else {
				run(direction);
				return false;
			} 
		}
		else {
			pogFights += 1;
			return true;
		}
	}
 
	/**
	 * If energy is > 50, it runs in a random direction.
	 * Otherwise, it reproduces to continue the Pogchamp legacy.
	 */
	@Override
	public void doTimeStep() {
		age += 1;
		
		if (this.getEnergy() <= 50) {
			// MyCritter3 must ensure his continued lineage...
			MyCritter3 child = new MyCritter3();
			reproduce(child, Critter.getRandomInt(8));
			direction = (Critter.getRandomInt(8) + direction) % 8;
			walk(direction);
		}
		else if (this.getEnergy() > 50 && this.getEnergy() <= 100) {
			int directionBonus = direction * direction;
			direction = (direction + directionBonus) % 8;
			run(direction);
		}
		else {
			int directionBonus = (direction * direction) + direction;
			direction = (direction + directionBonus) % 8;
			run(direction);
		}	
	}
	
	/**
	 * Prints to the console the total number of living Pogchamps, the oldeset Pogchamp, and number of fights.
	 * @param myCritter3
	 */
	public static void runStats(java.util.List<Critter> myCritter3) {
		int oldest = 0;
		int numFights = 0;
		int numberOfPogs = 0;
		
		for (Object obj : myCritter3) {
			MyCritter3 p = (MyCritter3) obj;
			numberOfPogs += 1;
			if (p.age > oldest) {
				oldest = p.age;
			}
			numFights += p.pogFights;
		}
		
		System.out.println("Total Pogchamp Fights: " + numFights);
		System.out.println("Total Living Pogchamps: " + numberOfPogs);
		System.out.println("Oldest Pogchamp: " + oldest);	
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.DIAMOND;
	} 
	
	@Override
	public javafx.scene.paint.Color viewFillColor() {
		return javafx.scene.paint.Color.DARKOLIVEGREEN;
	}
	
	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return javafx.scene.paint.Color.BLACK;
	}
	
}
