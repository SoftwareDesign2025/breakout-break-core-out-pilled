
public class Level_2 extends Level{
	public Level_2() {
		this.ROWS = 6;
		generateBlocks(ROWS);
		makeRow(8, 4, obstacles);
		addPaddle();
		addBall();
	}
}
