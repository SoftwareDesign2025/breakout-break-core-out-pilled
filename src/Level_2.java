
public class Level_2 extends Level{
	public Level_2() {
		this.ROWS = 6;
		for(int i = 0; i < ROWS; i++) {
			int rowHealth = ROWS - i;
			if(rowHealth > 4) { rowHealth = 4; }
			makeRow(0, i, COLUMNS, blocks, rowHealth);
		}
		makeObstacle(0, 7);
		makeObstacle(1, 7);
		makeObstacle(7, 7);
		makeObstacle(8, 7);
		addPaddle();
		addBall();
	}
}
