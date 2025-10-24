public class Level_1 extends Level {
	public Level_1() {
		this.ROWS = 4;
		generateBlocks(ROWS);
		addPaddle();
		addBall();
	}
}