import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
public class Paddle {
	public static int PADDLE_WIDTH = 92; //placeholder until we run and see how everything fits
	public static int PADDLE_HEIGHT = 12;
	public static final int POWERED_UP_PADDLE = PADDLE_WIDTH * 2;
	public static final int PADDLE_VELOCITY = 20; //placeholder until we decide on a set value
	public static final String PADDLE_IMAGE = "resources/Paddle.png";
	public static final int Y_COORDINATE = 628;
	public static final int START_X = 220;
	public ImageView myView;
 
	public Image paddleImage;
	
	public int xCoordinate; //placeholder 
	
	public Paddle (Image image) {
		this.paddleImage = image;
		 myView = new ImageView(image);
		 myView.setX(START_X);
		 myView.setY(Y_COORDINATE);
	}
	
	 public Node getView () {
	        return myView;
	    }
	
	public void changeWidth(int newWidth) {
		PADDLE_WIDTH = newWidth;
	}
	
	public void handleKeyInput(KeyCode code) {
		if(code == KeyCode.LEFT) {
			xCoordinate+= PADDLE_VELOCITY;
		} else if(code == KeyCode.RIGHT) {
			xCoordinate-=PADDLE_VELOCITY;
		}
		
	}
	
	

	public void reset() {
		xCoordinate = START_X;
	}
	
	public void move(KeyCode code) {
		if(code == KeyCode.LEFT) {
			xCoordinate -= PADDLE_VELOCITY;
		}
		else if (code == KeyCode.RIGHT) {
			xCoordinate += PADDLE_VELOCITY;
		}
	}
}
