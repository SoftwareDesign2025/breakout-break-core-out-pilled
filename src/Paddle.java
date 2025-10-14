import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class Paddle {
	public static int PADDLE_WIDTH = 92; //placeholder until we run and see how everything fits
	public static int PADDLE_HEIGHT = 12;
	public static final int POWERED_UP_PADDLE = PADDLE_WIDTH * 2;
	public static final int PADDLE_VELOCITY = 20; //placeholder until we decide on a set value
	public static final String PADDLE_IMAGE = "resources/Paddle.png";
	public static final int Y_COORDINATE = 628;
	public static final int START_X = 220;
	public static final int SCREEN_HEIGHT = 480;
	public static final int SCREEN_X = 640;
	public ImageView myView;
	public Image paddleImage;
    private Point2D myVelocity;
    
	public int xCoordinate; //placeholder 
	
	public Paddle (int posX, int posY) {
		try {
			this.paddleImage = new Image(new FileInputStream(PADDLE_IMAGE));
		}
		catch (FileNotFoundException e) {};
		 myView = new ImageView(paddleImage);
		 myView.setX(posX);
		 myView.setY(posY);
	}
	
	 public Node asNode() {
	        return myView;
	    }
	
	public void changeWidth(int newWidth) {
		PADDLE_WIDTH = newWidth;
	}
	
	public void handleKeyInput(KeyCode code) {
		if(code == KeyCode.LEFT) {
			xCoordinate+= PADDLE_VELOCITY;
		} else if(code == KeyCode.RIGHT) {
			xCoordinate -= PADDLE_VELOCITY;
		}
		
	}
	
	

	public void reset() {
		xCoordinate = START_X;
	}
	
	public void moveHorizontally() {
		
	}
	
	public void move(double elapsedTime, KeyCode code) {
	if (myView.getX() < 0 || myView.getX() > SCREEN_X) {	
	if (code == KeyCode.LEFT) {	
	  myView.setX(myView.getX() + PADDLE_VELOCITY * elapsedTime);
	}
	if (code == KeyCode.RIGHT) {
		myView.setX(myView.getX() - PADDLE_VELOCITY * elapsedTime);
	}
}
	}
}
