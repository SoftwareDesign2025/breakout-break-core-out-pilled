import java.awt.Point;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Caelan Duncan
 */
public class SizePowerUp extends PowerUp {
	public static final String POWER_IMAGE = "resources/sizepower.gif";
	private boolean activated = false;

	public SizePowerUp(Point pos) {
		super(pos);
		
		try {
			Image img = new Image(new FileInputStream(POWER_IMAGE));
			myView = new ImageView(img);
		} 
		catch (FileNotFoundException e) {}
		
		
		duration = 15;
	}

	@Override
	public void activate() {
		activated = true;
		
		
	}

}
