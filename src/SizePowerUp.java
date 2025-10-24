/**
 * On paddle collision with this object, changes the size of the paddle for a duration
 * 
 * @author Caelan Duncan
 */
public class SizePowerUp extends PowerUp {
	private static final double SIZE_INCREASE = 1.5;
	private static final long DURATION = 15000;
	
	protected String powerName = "size";
	private Paddle paddle;

	public SizePowerUp(int xCoord, int yCoord, Paddle paddle) {		
		super(xCoord, yCoord);
        
        this.paddle = paddle;
	}
	
	@Override
	public void activatePower() {
		super.activatePower();
		
		paddle.getView().setScaleX(paddle.getView().getScaleX() * SIZE_INCREASE);
		
		try {
			Thread.sleep(DURATION);
		} catch (InterruptedException e) {}
		
		deactivatePower();
	}
	
	private void deactivatePower() {
		paddle.getView().setScaleX(paddle.getView().getScaleX() / SIZE_INCREASE);
	}
}
