import java.awt.Point;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * On paddle collision with this object, it should call a paddle method to change its size
 * 
 * @author Caelan Duncan
 */
public class SizePowerUp extends PowerUp {
	public static final String POWER_IMAGE = "resources/sizepower.gif";
	
	private Paddle paddle;

	public SizePowerUp(Point pos, Paddle paddle) {
		super(pos);
		
		try {
			Image img = new Image(new FileInputStream(POWER_IMAGE));
			myView = new ImageView(img);
		} 
		catch (FileNotFoundException e) {}
		
		this.paddle = paddle;
	}
	
	@Override
	public void activatePower() {
		paddle.getView().setScaleX(paddle.getView().getScaleX() * 2);
		
		try {
			Thread.sleep(DURATION);
		} catch (InterruptedException e) {}
		
		deactivatePower();
	}
	
	private void deactivatePower() {
		paddle.getView().setScaleX(paddle.getView().getScaleX() / 2);
	}
}
