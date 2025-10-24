// abstract superclass for building each level
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.Group;

import javafx.scene.input.KeyCode;

public abstract class Level {
	
	// Object lists for all levels:
	protected ArrayList<Block> blocks;
	protected ArrayList<PowerUp> powerUps;
	protected ArrayList<Ball> balls;
	protected Paddle paddle;
	
	protected Group root;
	
	// level variables; // default values
	protected int TOP_PADDING = 30; // 30
	protected int SIDE_PADDING = 30; // 30
	protected int COLUMNS = 10; // 10
	protected int BLOCK_PAD = 4; // 4
	protected boolean levelComplete = false;
	
	// set per level:
	protected int ROWS;
	
	protected ArrayList<Block> generateBlocks(int rows, int columns) {
		blocks = new ArrayList<Block>();
		// Row generation
		for(int i = 0; i < rows; i++) {
			makeRow(i, columns, blocks);
		}
		return blocks;
	}
	
	protected ArrayList<Block> generateBlocks(int rows) {
		blocks = this.generateBlocks(rows, COLUMNS);
		return blocks;
	}
	
	// add block objects by row to blocks array
	protected void makeRow(int row, int length, ArrayList<Block> blocks) {
		int yCoord = TOP_PADDING + ((Block.SIZE_Y + BLOCK_PAD) * row);
		int xCoord;
		int health = 1;

		for(int i = 0; i < length; i++) {
			xCoord = SIDE_PADDING + ((Block.SIZE_X + BLOCK_PAD) * i);
			Block b = new Block(xCoord, yCoord, health);
			blocks.add(b);
		}
	}
	
	// check for level completed
	public boolean isComplete() {
		return levelComplete;
	}
	
	// update objects in level per frame
    public void step(KeyCode currentKey) {
        paddle.move(currentKey);

        // while paused after losing a life, freeze ball movement but keep paddle responsive
        if (waitingForRespawn) {
            return;
        }

        for(Ball ball : balls) {
        		ball.move();
        		if(ball.getView().getY() + 15 >= SIZE_Y) {
                    lives--;
                    if (lives <= 0) {
                        gameOver();
                        return;
                    } else {
                        resetBallWithDelay();
                        return;
                    }
                }
        }

        // paddle collision
        if (paddle.checkCollision(ball)) {
            paddle.onCollision(ball);
        }

        // block collisions
        Iterator<Block> blockIterator = blocks.iterator();
        while (blockIterator.hasNext()) {
            Block b = blockIterator.next();
            if (!b.isDestroyed() && b.checkCollision(ball)) {
                b.onCollision(ball);
            }
        }
        
        // powerup movement and collisions
        Iterator<PowerUp> powerupIterator = powerUps.iterator();
        while (powerupIterator.hasNext()) {
        	PowerUp p = powerupIterator.next();
        	
        	p.move();
        	
        	if (p.intersects(paddle.getView())) {
				p.activatePower();
			}
        }

        // check win condition
        boolean allDestroyed = blocks.stream().allMatch(Block::isDestroyed);
        if (allDestroyed) {
            winScreen();
        }
    }
	
}
	