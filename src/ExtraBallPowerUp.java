import java.util.ArrayList;

import javafx.scene.Group;

/**
 * On paddle collision with this object, adds an extra ball to the game
 * 
 * @author Caelan Duncan
 */
public class ExtraBallPowerUp extends PowerUp {
	protected String powerName = "extraBall";
	

	public ExtraBallPowerUp(int xCoord, int yCoord) {
		super(xCoord, yCoord);
	}

	
}