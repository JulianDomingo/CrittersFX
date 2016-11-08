/* CRITTERS <MyCritter4.java>
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
 * This Critter imitates the Keepo icon that is spammed in twitch chat
 * Keepo keeps spamming as long as it is alive and tries to reproduce
 * as long as it is alive like a virus. Keepo does not fight instead 
 * it spams. 
 */
package assignment5;

import assignment5.Critter.CritterShape;

public class MyCritter4 extends Critter{
	private int dir;
	private int spam;
	private int spamChat;

	/**
	 * constructor at MyCritter4()
	 */
	public MyCritter4() {
		dir = Critter.getRandomInt(8);
		spam = dir * Critter.getRandomInt(8);
		spamChat = 0;
	}
	
	/**
	 * toString to return 4
	 */
	@Override
	public String toString() { return "4"; }
	
	/**
	 * always tries to run away
	 */
	@Override 
	public boolean fight(String not_used) { return false; }
	
	/**
	 * moves the Critter, reproduces and spams!
	 */
	@Override
	public void doTimeStep() {
		run(dir);
		
		MyCritter4 child = new MyCritter4();
		reproduce(child, Critter.getRandomInt(8));
		
		if (getEnergy() > 1) {
			spam++;
			spamChat++;
		}
	}
	
	/**
	 * show how many Keepos have been spammed
	 * @param keepos ArrayList of Keeps
	 */
	public static void runStats(java.util.List<Critter> keepos) {
		int spammed = 0;
		for(Object k: keepos) {
			spammed += ((MyCritter4)k).spam; 
		}
		System.out.println("Keepo has been spammed " + spammed + " times");
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.SQUARE;
	} 
	
	@Override
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.DARKORANGE;
	}
	
	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return javafx.scene.paint.Color.BLACK;
	}
}
