import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Simple Ball class for Breakout.
 * Moves around, can reverse direction, and implements Collidable so
 * the game world can treat it like any other collidable object.
 *
 */
public class Ball implements Collidable {
    private static final String BALL_IMAGE = "resources/ball.gif";
    private static final int BALL_SIZE = 15;
    private static final int ANGLE_RANGE = 60;
    private static final int BALL_LAUNCH_SPEED = 200;
    
    // default screen bounds in case Game doesn't call a bounds-check method
    private static final double DEFAULT_SCREEN_WIDTH = 480;
    private static final double DEFAULT_SCREEN_HEIGHT = 640;
    
    // default paddle dimensions for default ball position
    private static final double PADDLE_HEIGHT = 12;
    private static final double PADDLE_WIDTH = 92;
    private static final double PADDLE_PADDING = 30;
    private static final double DEFAULT_POS_X = (DEFAULT_SCREEN_WIDTH / 2) - (BALL_SIZE / 2);
	private static final double DEFAULT_POS_Y = (DEFAULT_SCREEN_HEIGHT - (PADDLE_HEIGHT + PADDLE_PADDING + BALL_SIZE + 2));
	

    private ImageView myView;
    private double dx;
    private double dy;

    // store initial speed magnitude for consistent respawns
    private double speedMagnitude;

    // constructor sets start position and starting velocity
    public Ball(double startX, double startY, double startDX, double startDY) {
        try {
            Image img = new Image(new FileInputStream(BALL_IMAGE));
            myView = new ImageView(img);
        } catch (FileNotFoundException e) {
            System.out.println("Ball image not found! using empty ImageView.");
            myView = new ImageView();
        }

        myView.setFitWidth(BALL_SIZE);
        myView.setFitHeight(BALL_SIZE);
        myView.setX(startX);
        myView.setY(startY);

        dx = startDX;
        dy = startDY;

        speedMagnitude = Math.sqrt(dx*dx + dy*dy); // store initial speed
    }
    
    // constructor override for default (centered) position, 0 velocity
    public Ball() {
    	this(DEFAULT_POS_X, DEFAULT_POS_Y, 0, 0);
    }
    
    public void launch() {
    	double angle = Math.toRadians((Math.random() * ANGLE_RANGE) + ANGLE_RANGE);
    	setVelocity(angle);
    }

    // getter for the view (used by Game and Collidable)
    @Override
    public ImageView getView() {
        return myView;
    }

    // Game can call this every frame
    public void move(double elapsedTime) {        
        myView.setX(myView.getX() + dx * elapsedTime / 1000);
        myView.setY(myView.getY() + dy * elapsedTime / 1000);
        // keep it from leaving the screen using defaults (Game can do nicer checks)
        checkBounds((int) DEFAULT_SCREEN_WIDTH, (int) DEFAULT_SCREEN_HEIGHT);
    }
    
    // check and bounce on screen edges (Game can call this with real window size)
    public void checkBounds(int screenWidth, int screenHeight) {
        double x = myView.getX();
        double y = myView.getY();

        // left / right sides
        if (x <= 0 || x + BALL_SIZE >= screenWidth) {
            reverseX();
        }
        // top
        if (y <= 0) {
            reverseY();
        }
//        // bottom (fell off)
//        if (y + BALL_SIZE >= screenHeight) {
//            reverseY();
//        }
    }

    public void reverseX() { dx = -dx; }
    public void reverseY() { dy = -dy; }

    // allow game or other objects to set velocity directly (e.g., powerups)
    public void setVelocity(double angleRad) {
        // maintain speed magnitude for consistent gameplay
    		if(speedMagnitude > 0) {
    			dx = speedMagnitude * Math.cos(angleRad);
        		dy = -1 * speedMagnitude * Math.sin(angleRad);
    		}
    		else {
    			dx = BALL_LAUNCH_SPEED * Math.cos(angleRad);
    			dy = -1 * BALL_LAUNCH_SPEED * Math.sin(angleRad); 
    		}
    }

    public double getX() { return myView.getX(); }
    public double getY() { return myView.getY(); }
    
    public void setX(double x) { myView.setX(x); }
    public void setY(double y) { myView.setY(y); }
    
    public double getDx() { return dx; }
    public double getDy() { return dy; }

    // Collidable implementation
    // check intersection with another ball (or any Collidable who uses Ball)
    @Override
    public boolean checkCollision(Ball other) {
        if (other == null || other.getView() == null) return false;
        return myView.getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    @Override
    public void onCollision(Ball other) {
        reverseY();
    }
    
 // Adjust velocity based on a new angle (keeps current speed magnitude)
    public void setVelocityFromAngle(double angleRad) {
        double speed = Math.sqrt(dx*dx + dy*dy); // current speed
        dx = speed * Math.cos(angleRad);
        dy = -Math.abs(speed * Math.sin(angleRad)); // ensure it goes upward
    }

    
 // makes the ball bounce upward off the paddle while keeping same left/right direction
    public void bounceOffPaddle() {
        // flip vertical direction only
        dy = -Math.abs(dy);
    }

}