// Initialize game
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.awt.Point;

/* 
 * set up game with window size, paddle, ball(s)
 * set up lives (3 chances?)
 * 
 */
public class Game extends Application {
	
	public static final int SIZE_X = 480;
	public static final int SIZE_Y = 640;
	public static final int FPS = 60;
	public static final int MS_DELAY = 1000/FPS;
	public static final double SEC_DELAY = 1.0/FPS;
	public static final String TITLE = "Breakout!";
	public static final Paint BACKGROUND = Color.BLACK;
	
	// agreed upon pixel values for items:
	final int PADDLE_WIDTH = 60;
	final int PADDLE_HEIGHT = 12;
	final int PADDLE_PADDING = 20;
	final int BALL_SIZE = 20;
	final int SIDE_PADDING = 31;
	final int BLOCK_X = 40;
	final int BLOCK_Y = 20;
	final int BLOCK_PADDING = 2;
	
	// game setup parameters:
	final int NUM_BLOCKS_X = 10;
	final int NUM_BLOCKS_Y = 5;
	
	
	private Scene myScene;
	
	// initialize game window
	public void start(Stage stage) {
		myScene = setupScene(SIZE_X, SIZE_Y, BACKGROUND);
		stage.setScene(myScene);
		stage.setTitle(TITLE);
		stage.show();
		
		KeyFrame frame = new KeyFrame(Duration.millis(MS_DELAY), e -> step(SEC_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		
	}
	
	private Scene setupScene (int width, int height, Paint background) {
		Group root = createRootForGame(width, height);
		Scene scene = new Scene(root, width, height, background);
		
		return scene;
		
	}
	
	private Group createRootForGame(int width, int height) {
		
		Group root = new Group();
		// add blocks to arraylist and root
		ArrayList<Block> myBlocks = new ArrayList<>();
		int blockStepX = BLOCK_X + BLOCK_PADDING;
		int blockStepY = BLOCK_Y + BLOCK_PADDING;
		for(int i = 0; i < NUM_BLOCKS_Y; i++) {
			int health = NUM_BLOCKS_Y - i;
			for(int n = 0; n < NUM_BLOCKS_X; n++) {
				Point location = new Point(SIDE_PADDING + (blockStepX * n), SIDE_PADDING + (blockStepY * i));
				Block nextBlock = new Block(health, location, BLOCK_X, BLOCK_Y, null);
				myBlocks.add(nextBlock);
				root.getChildren().add(nextBlock.asNode());
			}
		}
		// add paddle to root
		int paddlePosX = (int) (SIZE_X/2  - (0.5 * PADDLE_WIDTH));
		int paddlePosY = SIZE_Y - (PADDLE_HEIGHT + PADDLE_PADDING);
		Paddle paddle = new Paddle(paddlePosX, paddlePosY);
		root.getChildren().add(paddle.asNode());
		// add ball to root (?)
		
		return root;
	}
			
	private void step(double elapsedTime) {
		// 
	}
}
