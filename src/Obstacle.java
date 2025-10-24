import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle extends Block {
	private static final String IMAGE_RESOURCES = "resources/obstacle.gif";

	public Obstacle(int xCoord, int yCoord) {
		super(xCoord, yCoord, 0);
	}
	
	@Override
	protected void setImage(int health) {
		try {
            Image img = new Image(new FileInputStream(IMAGE_RESOURCES));
            myView = new ImageView(img);
        } catch (FileNotFoundException e) {
            myView = new ImageView();
            System.out.println("Block image not found for health: " + health);
        }
	}
	
	@Override
	protected int hit() {
		return 0;
	}
}
