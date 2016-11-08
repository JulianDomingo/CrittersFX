package assignment5;

import java.util.TimerTask;

public class CritterAnimation extends TimerTask{
	double frameSpeed;
	public CritterAnimation(Double frameSpeed) {
		this.frameSpeed = frameSpeed;
	}
	
	public void run() {
		for (int i = 0; i < frameSpeed; i++) {
			Critter.worldTimeStep();
		}
		//Critter.displayWorld();
		System.out.println("whats up");
	}
}
