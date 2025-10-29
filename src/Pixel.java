import java.awt.Point;

import javafx.scene.Node;



public class Pixel implements Collidable{
	public Node myView;
	private boolean destroyed = false;
	public Pixel() {
		//super();
	}
	
    public boolean checkCollision(Ball ball) {
        // simple bounds check
        return !destroyed && myView.getBoundsInParent().intersects(ball.getView().getBoundsInParent());
    }

    @Override
    public void onCollision(Ball ball) {
        //hit();         // reduce health / mark destroyed
        ball.reverseY(); // bounce ball up
    }

    @Override
    public Node getView() {
        return myView;
    
	
}
}