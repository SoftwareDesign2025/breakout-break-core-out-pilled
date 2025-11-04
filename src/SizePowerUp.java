import java.util.ArrayList;
import java.util.Iterator;

/**
 * On paddle collision with this object, changes the size of the paddle for a duration
 * 
 * @author Caelan Duncan
 */
public class SizePowerUp extends PowerUp {
	private static final double SIZE_INCREASE = 1.5;
	
	protected String powerName = "size";
	private ArrayList<Paddle> paddles;

	public SizePowerUp(ArrayList<Paddle> paddles) {		
		super();
        
        this.paddles = paddles;
	}
	
	@Override
	public void activatePower() {
		super.activatePower();
		
		modifyPaddles(SIZE_INCREASE);
	}
	
	@Override
	protected void deactivatePower() {
		modifyPaddles(1/SIZE_INCREASE);
	}
	
	private void modifyPaddles(double scalar) {
		Iterator<Paddle> paddleIterator = paddles.iterator();
		while (paddleIterator.hasNext()) {
			Paddle paddle = paddleIterator.next();
			paddle.getView().setScaleX(paddle.getView().getScaleX() * scalar);
		}
	}
}
