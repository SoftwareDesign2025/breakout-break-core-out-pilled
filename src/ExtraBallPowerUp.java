import java.util.ArrayList;

import javafx.scene.Group;

/**
 * On paddle collision with this object, adds an extra ball to the game
 * 
 * @author Caelan Duncan
 */
public class ExtraBallPowerUp extends PowerUp {
	protected String powerName = "extraBall";
	private ArrayList<Ball> balls;
	private Group parent;
	
	public ExtraBallPowerUp(Group root, ArrayList<Ball> balls) {
		super();
		
		this.balls = balls;
		this.parent = root;
	}

	@Override
	public void activatePower() {
		super.activatePower();
		
		Ball extraBall = new Ball();
		balls.add(extraBall);
		parent.getChildren().add(extraBall.getView());
	}

	@Override
	protected void deactivatePower() {}
}
