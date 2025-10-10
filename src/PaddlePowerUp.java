import java.awt.Point;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class PaddlePowerUp extends PowerUp {
	public static final String POWER_IMAGE = ;

	public PaddlePowerUp(Point pos) {
		super(pos);
		
		myView = new ImageView(image);
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

}
