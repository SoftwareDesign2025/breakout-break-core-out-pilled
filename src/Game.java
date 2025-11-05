import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import javafx.scene.control.Button;


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
    
    protected GameData gameData = new GameData();
    protected Menu menu;
    protected int stepScore = 0;
    protected ArrayList<Level> levels = getLevels(); // list of levels
    private Level currentLevel = levels.get(gameData.level);
    
    
    protected Scene gameScene;
    protected Scene levelScene;


    
    // brings up start menu, waits for New Game button press
    @Override
    public void start(Stage primaryStage) {
        gameStage = new Stage();
        menu = new Menu(SIZE_X, SIZE_Y, this);
        gameStage.setScene(menu.getScene());
        gameStage.show();
        
        KeyFrame frame = new KeyFrame(Duration.millis(MS_DELAY), e -> step(MS_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    
    // Add any new level subclasses here to be included in the game
    private ArrayList<Level> getLevels() {
    		ArrayList<Level> levels = new ArrayList<Level>();
    		levels.add(new Level_1());
    		levels.add(new Level_2());
    		levels.add(new Level_3());
    		
    		return levels;
    }
    
    
    public void runGame() {
    	levelScene = new Scene(currentLevel.getRoot(), SIZE_X, SIZE_Y, Color.BLACK);
    	
    	
    	gameStage.setScene(levelScene);
    	
    	// key handling per level scene
    	levelScene.setOnKeyPressed(e -> currentKey = e.getCode());
    	levelScene.setOnKeyReleased(e -> currentKey = null);
    		
        // game loop
        animation = new Timeline(new KeyFrame(Duration.millis(MS_DELAY), e -> step(MS_DELAY)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }
    
    public void pause() {
    		animation.pause();
    		gameStage.setScene(menu.getScene());
    }
    
    public void resume() {
    		gameStage.setScene(levelScene);
    		animation.play();
    }
    
    private void nextLevel() {
    		animation.pause();
    		gameData.level += 1;
    		try {
    			this.currentLevel = levels.get(gameData.level);
    			runGame();
    		}
			catch(IndexOutOfBoundsException e) {
				System.out.println("No more levels"); 
				gameOver(true);
			}
    }
    
    public void step(double elapsedTime) {
    		if(currentKey == KeyCode.ESCAPE) {
    			pause();
    			currentKey = null;
    		}
    		if(currentLevel.noBalls()) {
    			loseLife();
    		}
    		else {
    			stepScore = currentLevel.step(currentKey, elapsedTime);
    			gameData.score += stepScore;
    		}
    		if(currentLevel.isComplete()) {
    				nextLevel();
    		}
    }
    
    private void loseLife() {
    	gameData.lives -= 1;
    	if(gameData.lives <= 0) {
    		gameOver(false);
    	}
    	else {
    		currentLevel.addBall();
    	}
    }
    
    private void gameOver(boolean gameWon) {
    	if(gameWon) {
    		winScreen();
    	}
    	else { loseScreen(); }
    }

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
    private void loseScreen() {
        animation.stop();
        showEndScreen("GAME OVER");
    }
    private void winScreen() {
        animation.stop();
        showEndScreen("YOU WIN!");
    }

    private void showEndScreen(String message) {
        Group root = new Group();
        Scene scene = new Scene(root, SIZE_X, SIZE_Y, Color.BLACK);

        Text text = new Text(message);
        text.setFont(new Font(40));
        text.setFill(Color.WHITE);
        text.setX(80);
        text.setY(200);

        Button restartButton = new Button("RESTART");
        restartButton.setLayoutX(SIZE_X / 2 - 40);
        restartButton.setLayoutY(300);
//        restartButton.setOnAction(e -> { this.runGame(); });

        root.getChildren().addAll(text, restartButton);
        gameStage.setScene(scene);
    }
}
