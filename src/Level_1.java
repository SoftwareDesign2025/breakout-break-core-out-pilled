
public class Level_1 extends Level {
	
	public Level_1() {
		// Blocks:
		this.ROWS = 4;
		blocks = generateBlocks(ROWS);
		
		addPaddle();
		addBall();
		
	}

}
