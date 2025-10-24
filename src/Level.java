// abstract superclass for building each level
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.Group;

import javafx.scene.input.KeyCode;

public abstract class Level {
	
	// Object lists for all levels:
	protected ArrayList<Block> blocks = new ArrayList<Block>();
	protected ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
	protected ArrayList<Ball> balls = new ArrayList<Ball>();
	protected ArrayList<Paddle> paddles = new ArrayList<Paddle>();
	
	protected Group root = new Group();
	
	// level variables; // default values
	protected int TOP_PADDING = 30; // 30
	protected int SIDE_PADDING = 36; // 36
	protected int COLUMNS = 9; // 9
	protected int BLOCK_PAD = 6; // 6
	protected boolean levelComplete = false;
	
	// set per level:
	protected int ROWS;
	
	
	// PROTECTED METHODS:
	
	// add Paddle
	protected void addPaddle() {
		Paddle p = new Paddle();
		paddles.add(p);
		root.getChildren().add(p.asNode());
	}
	
	// generate default blocks in rows and columns
	protected void generateBlocks(int rows, int columns) {
		// Row generation
		for(int i = 0; i < rows; i++) {
			makeRow(i, columns, blocks);
		}
	}
	
	// generate default blocks with default column value
	protected void generateBlocks(int rows) {
		this.generateBlocks(rows, COLUMNS);
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
			root.getChildren().add(b.asNode());
		}
	}
	
	// PUBLIC METHODS:
	
	// return root group
	public Group getRoot() {
		return root;
	}
	
	// check for level completed
	public boolean isComplete() {
		return levelComplete;
	}
	
	// add a Ball object
	public void addBall() {
		Ball b = new Ball();
		balls.add(b);
		root.getChildren().add(b.getView());
	}
	
	// update objects in level per frame
    public void step(KeyCode currentKey) {
    	for(Paddle paddle : paddles) {
    		paddle.move(currentKey);
    	}
    	for(Ball ball : balls) {
    		ball.move();
    	}
//
//        // while paused after losing a life, freeze ball movement but keep paddle responsive
//        if (waitingForRespawn) {
//            return;
//        }
//
//        for(Ball ball : balls) {
//        		ball.move();
//        		if(ball.getView().getY() + 15 >= SIZE_Y) {
//                    lives--;
//                    if (lives <= 0) {
//                        gameOver();
//                        return;
//                    } else {
//                        resetBallWithDelay();
//                        return;
//                    }
//                }
//        }
//
//        // paddle collision
//        if (paddle.checkCollision(ball)) {
//            paddle.onCollision(ball);
//        }
//
//        // block collisions
//        Iterator<Block> blockIterator = blocks.iterator();
//        while (blockIterator.hasNext()) {
//            Block b = blockIterator.next();
//            if (!b.isDestroyed() && b.checkCollision(ball)) {
//                b.onCollision(ball);
//            }
//        }
//        
//        // powerup movement and collisions
//        Iterator<PowerUp> powerupIterator = powerUps.iterator();
//        while (powerupIterator.hasNext()) {
//        	PowerUp p = powerupIterator.next();
//        	
//        	p.move();
//        	
//        	if (p.intersects(paddle.getView())) {
//				p.activatePower();
//			}
//        }
//
//        // check win condition
//        boolean allDestroyed = blocks.stream().allMatch(Block::isDestroyed);
//        if (allDestroyed) {
//            winScreen();
//        }
    }
	
}
	