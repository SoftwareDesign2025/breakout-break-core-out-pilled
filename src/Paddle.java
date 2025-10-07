import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Paddle {
	public static final int PADDLE_SIZE = 3; //placeholder until we run and see how everything fits
	public static final int PADDLE_VELOCITY = 1; //placeholder until we decide on a set value
	public static final int MY_Y_COORDINATES = 1; //placeholder until we find y value to keep the paddle at
	public int my_x_coordinates = 0; //placeholder 
	
	
	public Paddle (Image image, int screenWidth, int screenHeight) {
		
	}
	
	public void handleKeyInput(KeyCode code) {
		if(code == KeyCode.LEFT) {
			// make call to move() here once figured out
		} else if(code == KeyCode.RIGHT) {
			//make call to move but have it be opposite to previous call? (if that makes sene)
		}
		
	}
	
	public void move() {
		
	}
}
