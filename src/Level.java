// abstract superclass for building each level
import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.Group;

import javafx.scene.input.KeyCode;

public abstract class Level {
	
	// Object lists for all levels:
	protected ArrayList<Block> blocks = new ArrayList<Block>();
	protected ArrayList<Block> obstacles = new ArrayList<Block>();
	protected ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
	protected ArrayList<Ball> balls = new ArrayList<Ball>();
	protected ArrayList<Paddle> paddles = new ArrayList<Paddle>();
	
	protected Group root = new Group();
	
	protected int pointsPerStep = 0;
	
	// level variables; // default values
	protected int TOP_PADDING = 30; // 30
	protected int SIDE_PADDING = 36; // 36
	protected int COLUMNS = 9; // 9
	protected int BLOCK_PAD = 6; // 6
	
	// set per level:
	protected int ROWS;
	
	// PROTECTED METHODS:
	
	// add/remove Paddle
	public void addPaddle() {
		Paddle p = new Paddle();
		paddles.add(p);
		root.getChildren().add(p.asNode());
	}
	
	public void removePaddle(Paddle p) {
		paddles.remove(p);
		root.getChildren().remove(p.asNode());
	}
	
	// add/remove Ball
	public void addBall() {
		Ball b = new Ball();
		balls.add(b);
		root.getChildren().add(b.getView());
	}
	
	public void removeBall(Ball b) {
		balls.remove(b);
		root.getChildren().remove(b.getView());
	}
	
	public boolean noBalls() {
		if (balls.isEmpty()) {
			return true;
		}
		else return false;
	}
	
	// make row of block objects, where startX and startY are
	// integer coordinates in a 9 by (variable Y) grid; range (0-8), (0- ~20)
	protected void makeRow(int startX, int startY, int length, ArrayList<Block> blocks, int health) {
		if(length > (9 - startX)) { length = 9 - startX; }
		int yCoord = TOP_PADDING + ((Block.SIZE_Y + BLOCK_PAD) * startY);
		int xCoord;
		
		for(int i = 0; i < length; i++) {
			xCoord = SIDE_PADDING + ((Block.SIZE_X + BLOCK_PAD) * i);
			Block b = new Block(xCoord, yCoord, health);
			blocks.add(b);
			root.getChildren().add(b.asNode());
		}
	}
	
	// add an obstacle to a set grid coordinate
	protected void makeObstacle(int posX, int posY) {
		int yCoord = TOP_PADDING + ((Block.SIZE_Y + BLOCK_PAD) * posY);
		int xCoord = SIDE_PADDING + ((Block.SIZE_X + BLOCK_PAD) * posX);
		Obstacle o = new Obstacle(xCoord, yCoord);
		obstacles.add(o);
		root.getChildren().add(o.asNode());
	}
	
	// return root group
	public Group getRoot() {
		return root;
	}
	
	// check for level completed
	public boolean isComplete() {
		boolean allDestroyed = blocks.stream().allMatch(Block::isDestroyed);
		return allDestroyed;
	}
	
	// update objects in level per frame
    public int step(KeyCode currentKey) {
    		
    		pointsPerStep = 0;
    		
    		// move paddle(s), check collision
    		Iterator<Paddle> paddleIterator = paddles.iterator();
    		while(paddleIterator.hasNext() ) {
    			Paddle paddle = paddleIterator.next();
				paddle.move(currentKey);
				for(Ball ball : balls) {
					if(paddle.checkCollision(ball)) {
						paddle.onCollision(ball);
					}
				}
    		}

    
    		// move ball(s), check bounds
    		Iterator<Ball> ballIterator = balls.iterator();
    		while(ballIterator.hasNext()) {
    			Ball ball = ballIterator.next();
    			ball.move();
				if(ball.getDy() == 0 && currentKey == KeyCode.SPACE) {
					ball.launch();
					}
				// in progress lives game logic
		        if (ball.getView().getY() + 15 >= Game.SIZE_Y) {
		        	removeBall(ball);
		        }
    		}

    		
        // block collisions
        Iterator<Block> blockIterator = blocks.iterator();
        while (blockIterator.hasNext()) {
        		Block b = blockIterator.next();
        		for(Ball ball : balls) {
            		if (!b.isDestroyed() && b.checkCollision(ball)) {
            			b.onCollision(ball);
            			pointsPerStep += b.hit();
            		}
            		// cheat key to destroy blocks quickly
            		if(currentKey == KeyCode.BACK_SLASH && !b.isDestroyed()) {
            			b.hit();
            		}
        		}
        	}
        
        // obstacle collision
        Iterator<Block> obstaclesIterator = obstacles.iterator();
        while (obstaclesIterator.hasNext()) {
        		Block b = obstaclesIterator.next();
        		for(Ball ball : balls) {
            		if (!b.isDestroyed() && b.checkCollision(ball)) {
            			b.onCollision(ball);
            		}
        		}
        }
        
	
        // powerup movement and collisions
        Iterator<PowerUp> powerupIterator = powerUps.iterator();
        while (powerupIterator.hasNext()) {
        		PowerUp p = powerupIterator.next();
        		p.move();
        		for(Paddle paddle : paddles) {
        			if (p.intersects(paddle.getView())) {
        				p.activatePower();
        			}
        		}
        }
        return pointsPerStep;
    }
} 