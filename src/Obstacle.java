
public class Obstacle extends Block {
	private final static String IMAGE_RESOURCES = "resources/obstacle.gif";

	public Obstacle(int xCoord, int yCoord, int health) {
		super(xCoord, yCoord, health);
	}
	
	@Override
	protected int hit() {
		return 0;
	}
}
