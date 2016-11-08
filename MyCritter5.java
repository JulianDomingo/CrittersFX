package assignment5;

import assignment5.Critter.CritterShape;

/* CRITTERS <MyCritter5.java>
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
/*
 * This Critter is a predator Critter it is at the top of the food chain
 * It does not reproduce, but it is (a dank meme that beat C9 in worlds because C9 sucks ay lmao jk)
 * It always tries to fight when its powerlevel is greater than 10.
 */

public class MyCritter5 extends Critter{
	private int dir;
	private int murdered;
	private int powerLevel;

	/**
	 * constructor for MyCritter5()
	 */
	public MyCritter5() {
		dir = Critter.getRandomInt(8);
		murdered = 0;
		powerLevel = 100;
	}
	
	/**
	 * to String returns 5
	 */
	@Override
	public String toString() { return "5"; }
	
	/**
	 * fight method for MyCritter5 uses powerLevel
	 * to determine whether it should fight or not
	 */
	@Override 
	public boolean fight(String not_used) { 
		if (powerLevel > 10) {
			murdered++;
			
			return true;
		}
		
		return false; 
	}
	
	/**
	 * moves the character and decrements his powerlevel
	 */
	@Override
	public void doTimeStep() {
		run(dir);
		powerLevel--;
	}
	
	/**
	 * shows how many Critters have been killed by Predator
	 * the Alpha Predator and kill count
	 * the weakest Predator and kill count
	 * @param Predator ArrayList of Predators
	 */
	public static void runStats(java.util.List<Critter> Predator) {
		int killed = 0;
		int max = -90;
		int min = 90000;
		
		for (Object obj : Predator) {
			MyCritter5 p = (MyCritter5) obj;
			killed += p.murdered;
			
			if (p.murdered > max) {
				max = p.murdered;
			}
			
			if (p.murdered < min) {
				min = p.murdered;
			}
		}
		
		System.out.println("Predators have killed: " + killed + " plebs");
		System.out.println("Alpha Predator killed " + max + " killed");
		System.out.println("Beta Predator killed " + min + " killed");
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.STAR;
	} 
	
	@Override
	public javafx.scene.paint.Color viewFillColor() {
		return javafx.scene.paint.Color.GREY;
	}
	
	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return javafx.scene.paint.Color.BLACK;
	}
}
