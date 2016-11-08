package assignment5;

import assignment5.Critter.CritterShape;
import assignment5.Critter.TestCritter;

public class MyCritter6 extends TestCritter {
	
	@Override
	public void doTimeStep() {
	}

	@Override
	public boolean fight(String opponent) {
		run(getRandomInt(8));
		return false;
	}

	@Override
	public String toString () {
		return "5";
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.CIRCLE;
	} 
	
	@Override
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.DARKVIOLET;
	}
	
	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return javafx.scene.paint.Color.BLACK;
	}
}
