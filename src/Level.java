// abstract superclass for building each level
import java.util.ArrayList;

public class Level {
	
	protected ArrayList<Block> blocks;
	
	// level variables; // default values
	protected int TOP_PADDING; // 
	protected int SIDE_PADDING;
	protected int COLUMNS;
	protected int ROWS;
	protected boolean POWERUPS;
	protected boolean OBSTACLES;
	
	protected int BLOCK_PADDING = 2;
	
	
	public Level(int topPadding, int sidePadding, int col, int row, boolean powerups, boolean obstacles) {
		this.TOP_PADDING = topPadding;
		this.SIDE_PADDING = sidePadding;
		this.COLUMNS = col;
		this.ROWS = row;
		this.POWERUPS = powerups;
		this.OBSTACLES = obstacles;
		
		blocks = generateBlocks();
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	protected ArrayList<Block> generateBlocks() {
		blocks = new ArrayList<Block>();
		// Row generation
		for(int i = 0; i < ROWS; i++) {
			makeRow(i, COLUMNS, blocks);
		}
		return blocks;
	}
	
	// add block objects by row to blocks array
	protected void makeRow(int row, int length, ArrayList<Block> blocks) {
		int yCoord = TOP_PADDING + ((Block.SIZE_Y + BLOCK_PADDING) * row);
		int xCoord;
		int health = ROWS - row;

		for(int i = 0; i < length; i++) {
			xCoord = SIDE_PADDING + ((Block.SIZE_X + BLOCK_PADDING) * i);
			Block b = new Block(xCoord, yCoord, health, null);
			blocks.add(b);
		}
	}
	
}
	