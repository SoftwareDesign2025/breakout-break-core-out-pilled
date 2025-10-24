import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;

public class Game extends Application {
	
	// Launch game as application
    public static void main(String[] args) {
        launch(args);
    }

    // publicly available game properties
    private static final int SIZE_X = 480;
    private static final int SIZE_Y = 640;
    private static final int FPS = 60;
    private static final int MS_DELAY = 1000 / FPS;
    
    // instance variables per game
    private Stage gameStage; // new window
    private Timeline loop;
    private KeyCode currentKey = null;
    
    protected int lives = 3;
    protected int score = 0;
    
    
    @Override
    public void start(Stage primaryStage) {
        gameStage = new Stage();
        StartMenu startMenu = new StartMenu();
        gameStage.setScene(startMenu.getScene());
        gameStage.show();
    }
    
    public void startGame() {
    		
    		
        // key handling per level scene
        currentLevel.getScene().setOnKeyPressed(e -> currentKey = e.getCode());
        currentLevel.getScene().setOnKeyReleased(e -> currentKey = null);
    		
        // game loop
        loop = new Timeline(new KeyFrame(Duration.millis(MS_DELAY), e -> step()));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

//    private ArrayList<Block> myBlocks;
//    private Ball ball;
//    private Paddle paddle;
//
//
//
//    private boolean waitingForRespawn = false; // NEW FLAG
//    private Text livesText; // TEMPORARY TEXT DISPLAY





    // show the initial menu
//    private void showStartMenu() {
//        Group menuRoot = new Group();
//        Scene menuScene = new Scene(menuRoot, SIZE_X, SIZE_Y, Color.BLACK);
//
//        Text title = new Text("BREAKOUT!");
//        title.setFont(new Font(40));
//        title.setFill(Color.WHITE);
//        title.setX(100);
//        title.setY(200);
//
//        Button playButton = new Button("PLAY");
//        playButton.setLayoutX(SIZE_X / 2 - 30);
//        playButton.setLayoutY(300);
//
//        playButton.setOnAction(e -> startGame());
//
//        menuRoot.getChildren().addAll(title, playButton);
//        
//    }

//    // start the actual game
//    public void startGame() {
//        lives = 3; // reset lives
//
//        Group root = new Group();
//        Scene scene = new Scene(root, SIZE_X, SIZE_Y, Color.BLACK);
//
//        // create paddle
//        paddle = new Paddle(220, 628);
//        root.getChildren().add(paddle.asNode());
//
//        // create ball
//        ball = new Ball(240, 600, 3, -3);
//        root.getChildren().add(ball.getView());
//
//        // create blocks
//        Level defaultLevel = new Level(30, 30, 10, 10, false, false);
//        myBlocks = defaultLevel.getBlocks();
//        for (Block block : myBlocks) {
//            root.getChildren().add(block.asNode());
//        }
//        
//
//        // LIVES TEXT (hidden by default)
//        livesText = new Text();
//        livesText.setFont(new Font(24));
//        livesText.setFill(Color.WHITE);
//        livesText.setVisible(false);
//        root.getChildren().add(livesText);
//
//
//
//        gameStage.setScene(scene);
//
//
//    }

    private void step() {
        paddle.move(currentKey);

        // while paused after losing a life, freeze ball movement but keep paddle responsive
        if (waitingForRespawn) {
            return;
        }

        ball.move();

        // check bottom of screen
        if (ball.getView().getY() + 15 >= SIZE_Y) {
            lives--;
            if (lives <= 0) {
                gameOver();
                return;
            } else {
                resetBallWithDelay();
                return;
            }
        }

        // paddle collision
        if (paddle.checkCollision(ball)) {
            paddle.onCollision(ball);
        }

        // block collisions
        Iterator<Block> blockIterator = myBlocks.iterator();
        while (blockIterator.hasNext()) {
            Block b = blockIterator.next();
            if (!b.isDestroyed() && b.checkCollision(ball)) {
                b.onCollision(ball);
            }
        }
        
        // powerup movement and collisions
        Iterator<PowerUp> powerupIterator = myPowerups.iterator();
        while (powerupIterator.hasNext()) {
        	PowerUp p = powerupIterator.next();
        	
        	p.move();
        	
        	if (p.intersects(paddle.getView())) {
				p.activatePower();
			}
        }

        // check win condition
        boolean allDestroyed = myBlocks.stream().allMatch(Block::isDestroyed);
        if (allDestroyed) {
            winScreen();
        }
    }

    // MODIFIED RESET: adds 3-sec delay & lives text display
    private void resetBallWithDelay() {
        waitingForRespawn = true;

        // set ball position ~240 px from center bottom
        ball.getView().setX(SIZE_X / 2.0 - 7.5);
        ball.getView().setY(SIZE_Y - 240);

        // freeze ball
        ball.setVelocity(0, 0);

        // display lives text
        livesText.setText("LIVES: " + lives);
        livesText.setX(SIZE_X / 2.0 - 50);
        livesText.setY(ball.getView().getY() - 120);
        livesText.setVisible(true);

        // wait 3 seconds before resuming
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            livesText.setVisible(false);
            waitingForRespawn = false;

            // random downward angle
            double randomDX = (Math.random() * 2 - 1) * 3; // -3 to +3
            double randomDY = Math.abs(Math.random() * 2 + 2); // downward
            ball.setVelocity(randomDX, randomDY);
        }));
        delay.play();
    }

    private void gameOver() {
        loop.stop();
        showEndScreen("GAME OVER");
    }

    private void winScreen() {
        loop.stop();
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
        restartButton.setOnAction(e -> startGame());

        root.getChildren().addAll(text, restartButton);
//        gameStage.setScene(scene);
    }
}
