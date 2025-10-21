import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball implements Collidable {
    private static final String BALL_IMAGE = "resources/ball.gif";
    private static final int BALL_SIZE = 15;

    private static final double DEFAULT_SCREEN_WIDTH = 480;
    private static final double DEFAULT_SCREEN_HEIGHT = 640;

    private ImageView myView;
    private double dx;
    private double dy;

    // store initial speed magnitude for consistent respawns
    private double speedMagnitude;

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

    @Override
    public ImageView getView() {
        return myView;
    }

    // updated move so it moves in smaller chunks to avoid skipping collisions
    public void move() {
        int steps = (int) Math.ceil(Math.max(Math.abs(dx), Math.abs(dy))); 
        double stepX = dx / steps;
        double stepY = dy / steps;

        for (int i = 0; i < steps; i++) {
            myView.setX(myView.getX() + stepX);
            myView.setY(myView.getY() + stepY);
        }

        checkBounds((int) DEFAULT_SCREEN_WIDTH, (int) DEFAULT_SCREEN_HEIGHT);
    }

    public void checkBounds(int screenWidth, int screenHeight) {
        double x = myView.getX();
        double y = myView.getY();

        if (x <= 0 || x + BALL_SIZE >= screenWidth) {
            reverseX();
        }
        if (y <= 0) {
            reverseY();
        }
        if (y + BALL_SIZE >= screenHeight) {
            reverseY();
        }
    }

    public void reverseX() { dx = -dx; }
    public void reverseY() { dy = -dy; }

    public void setVelocity(double newDX, double newDY) {
        // maintain speed magnitude for consistent gameplay
        double angle = Math.atan2(newDY, newDX);
        dx = speedMagnitude * Math.cos(angle);
        dy = speedMagnitude * Math.sin(angle);
    }

    public double getX() { return myView.getX(); }
    public double getY() { return myView.getY(); }

    @Override
    public boolean checkCollision(Ball other) {
        if (other == null || other.getView() == null) return false;
        return myView.getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    @Override
    public void onCollision(Ball other) {
        reverseY();
    }
}
