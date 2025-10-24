// abstract superclass for building each level
import java.util.ArrayList;
import java.util.HashMap;

public class Level {
	
	// Level map
//	protected static final HashMap<Integer, Level> levels = getLevels();
	
	// Add levels to hashmap
	protected HashMap<Integer, Level> getLevels() {
		HashMap<Integer, Level> levels = new HashMap<Integer, Level>();
//		levels.put(1, new Level_1());
//		levels.put(2, new Level_2());
//		levels.put(3, new Level_3());
		
		return levels;
	}
	
	public Level(int levelNum) {
		
	}
	
	// level variables; // default values
	protected int TOP_PADDING; // 
	protected int SIDE_PADDING;
	protected int COLUMNS;
	protected int ROWS;
	protected boolean POWERUPS;
	protected boolean OBSTACLES;
	
	protected int BLOCK_PADDING = 2;
	
	protected ArrayList<Block> blocks;
	
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
			Block b = new Block(xCoord, yCoord, health);
			blocks.add(b);
		}
	}
	
}
	