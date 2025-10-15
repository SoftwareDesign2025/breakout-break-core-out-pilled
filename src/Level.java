// abstract superclass for building each level
import java.util.ArrayList;

public abstract class Level {
	
	protected ArrayList<Block> blocks;
	
	protected int TOP_PADDING;
	protected int SIDE_PADDING;
	protected int COLUMNS;
	protected int ROWS;
	protected boolean POWERUPS;
	protected boolean OBSTACLES;
	
	// @todo:
	//	set up return value such that it can be drawn in w/ javaFX
	//	root? group? stage?
	
	public Level(int topPadding, int sidePadding, int col, int row, boolean powerups, boolean obstacles) {
		this.TOP_PADDING = topPadding;
		this.SIDE_PADDING = sidePadding;
		this.COLUMNS = col;
		this.ROWS = row;
		this.POWERUPS = powerups;
		this.OBSTACLES = obstacles;
	}
	
	protected ArrayList<Block> generateBlocks() {
		
		blocks = new ArrayList<Block>();
		// Row generation
		for(int i = 0; i < ROWS; i++) {
			makeRow(i, COLUMNS, blocks);
		}
		
		return blocks;
	}
	
	// add block objects by row
	protected void makeRow(int row, int length, ArrayList<Block> blocks) {
		
	}
	
}
	