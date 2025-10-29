import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

public class Game extends Application {
	
	// Launch game as application
    public static void main(String[] args) {
        launch(args);
    }
    
    // public fields for default position constructors
    public static final int SIZE_X = 480;
    public static final int SIZE_Y = 640;
    private static final int FPS = 60;
    private static final int MS_DELAY = 1000 / FPS;
    
    // instance variables
    private Stage gameStage; // new window
    private Timeline animation;
    private KeyCode currentKey = null;
    private Level currentLevel;
    
    protected GameData gameData;
    protected StartMenu startMenu;
    protected int stepScore = 0;
    protected ArrayList<Level> levels = getLevels(); // list of levels
    protected Scene levelScene;
    
    // brings up start menu, waits for New Game button press
    @Override
    public void start(Stage primaryStage) {
        gameStage = new Stage();
        startMenu = new StartMenu(SIZE_X, SIZE_Y, this);
//        PauseMenu pauseMenu = new PauseMenu(SIZE_X, SIZE_Y, this);
        gameStage.setScene(startMenu.getScene());
        gameStage.show();
    }
    
    // Add any new level subclasses here to be included in the game
    private ArrayList<Level> getLevels() {
    		ArrayList<Level> levels = new ArrayList<Level>();
    		levels.add(new Level_1());
//    		levels.add(new Level_2());
    		
    		return levels;
    }
    
    
    public void runGame() {
    		
    		gameData = new GameData();
    		
    		this.currentLevel = levels.get(gameData.level);
    		
    		levelScene = new Scene(currentLevel.getRoot(), SIZE_X, SIZE_Y, Color.BLACK);
    		gameStage.setScene(levelScene);
    		
        // key handling per level scene
    		levelScene.setOnKeyPressed(e -> currentKey = e.getCode());
    		levelScene.setOnKeyReleased(e -> currentKey = null);
    		
        // game loop
        animation = new Timeline(new KeyFrame(Duration.millis(MS_DELAY), e -> step()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }
    
    public void pause() {
    		animation.pause();
    		gameStage.setScene(startMenu.getScene());
    }
    
    public void resume() {
    		gameStage.setScene(levelScene);
    		animation.play();
    }
    
    public void step() {
    		if(currentKey == KeyCode.ESCAPE) {
    			pause();
    			currentKey = null;
    		}
    		stepScore = currentLevel.step(currentKey);
    		gameData.score += stepScore;
    		
    		
    }
//        paddle.move(currentKey);
//
//        // while paused after losing a life, freeze ball movement but keep paddle responsive
//        if (waitingForRespawn) {
//            return;
//        }
//
//        ball.move();
//
//        // check bottom of screen
//        if (ball.getView().getY() + 15 >= SIZE_Y) {
//            lives--;
//            if (lives <= 0) {
//                gameOver();
//                return;
//            } else {
//                resetBallWithDelay();
//                return;
//            }
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
//        Iterator<PowerUp> powerupIterator = powerups.iterator();
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
//    }
//
//    // MODIFIED RESET: adds 3-sec delay & lives text display
//    private void resetBallWithDelay() {
//        waitingForRespawn = true;
//
//        // set ball position ~240 px from center bottom
//        ball.getView().setX(SIZE_X / 2.0 - 7.5);
//        ball.getView().setY(SIZE_Y - 240);
//
//        // freeze ball
//        ball.setVelocity(0, 0);
//
//        // display lives text
//        livesText.setText("LIVES: " + lives);
//        livesText.setX(SIZE_X / 2.0 - 50);
//        livesText.setY(ball.getView().getY() - 120);
//        livesText.setVisible(true);
//
//        // wait 3 seconds before resuming
//        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
//            livesText.setVisible(false);
//            waitingForRespawn = false;
//
//            // random downward angle
//            double randomDX = (Math.random() * 2 - 1) * 3; // -3 to +3
//            double randomDY = Math.abs(Math.random() * 2 + 2); // downward
//            ball.setVelocity(randomDX, randomDY);
//        }));
//        delay.play();
//    }
//
//    private void gameOver() {
//        animation.stop();
//        showEndScreen("GAME OVER");
//    }
//
//    private void winScreen() {
//        animation.stop();
//        showEndScreen("YOU WIN!");
//    }
//
//    private void showEndScreen(String message) {
//        Group root = new Group();
//        Scene scene = new Scene(root, SIZE_X, SIZE_Y, Color.BLACK);
//
//        Text text = new Text(message);
//        text.setFont(new Font(40));
//        text.setFill(Color.WHITE);
//        text.setX(80);
//        text.setY(200);
//
//        Button restartButton = new Button("RESTART");
//        restartButton.setLayoutX(SIZE_X / 2 - 40);
//        restartButton.setLayoutY(300);
//        restartButton.setOnAction(e -> runGame());
//
//        root.getChildren().addAll(text, restartButton);
////        gameStage.setScene(scene);
//    }
}
