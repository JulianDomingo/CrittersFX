package assignment5;
import java.util.*;

import javafx.scene.paint.Color;

public class MyCritter1 extends Critter.TestCritter {

	@Override
	public void doTimeStep() {
		walk(0);
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 10) return true;
		return false;
	}
	 
	public String toString() {
		return "1";
	}
	
	public void test (List<Critter> l) {
		
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.DIAMOND;
	}
	
	@Override
	public javafx.scene.paint.Color viewFillColor() {
		return javafx.scene.paint.Color.CORAL;
	}
}
