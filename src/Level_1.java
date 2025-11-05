public class Level_1 extends Level {
	public Level_1() {
		this.ROWS = 4;
		for(int i = 0; i < ROWS; i++) {
			makeRow(0, i, COLUMNS, blocks, 1);
		}
		addPaddle();
		addBall();
	}
}