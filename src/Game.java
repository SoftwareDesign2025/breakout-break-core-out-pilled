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

    public static void main(String[] args) {
        launch(args);
    }

    public static final int SIZE_X = 480;
    public static final int SIZE_Y = 640;
    private static final int FPS = 60;
    private static final int MS_DELAY = 1000 / FPS;

    private Stage gameStage;
    private Timeline animation;
    private KeyCode currentKey = null;
    

    protected GameData gameData = new GameData();
    protected Menu menu;
    protected int stepScore = 0;
    protected ArrayList<Level> levels = getLevels();
    private Level currentLevel;
    protected boolean isGalagaMode = false;

    protected Scene levelScene;

    @Override
    public void start(Stage primaryStage) {
        gameStage = new Stage();
        menu = new Menu(SIZE_X, SIZE_Y, this);
        gameStage.setScene(menu.getScene());
        gameStage.show();
    }

    private ArrayList<Level> getLevels() {
        ArrayList<Level> levels = new ArrayList<>();
        levels.add(new Level_1());
        levels.add(new Level_2());
        levels.add(new Level_3());
        return levels;
    }

    public void runGame() {
        // Select level based on mode
        if (isGalagaMode) {
            currentLevel = new GalagaLevel();
        } else {
            currentLevel = levels.get(gameData.level);
        }

        levelScene = new Scene(currentLevel.getRoot(), SIZE_X, SIZE_Y, Color.BLACK);

        gameStage.setScene(levelScene);

        levelScene.setOnKeyPressed(e -> currentKey = e.getCode());
        levelScene.setOnKeyReleased(e -> currentKey = null);

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
        if (!isGalagaMode) {
            gameData.level += 1;
            try {
                currentLevel = levels.get(gameData.level);
                runGame();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No more levels");
                gameOver(true);
            }
        } else {
            // restart Galaga level
            runGame();
        }
    }

    public void step(double elapsedTime) {
        if (currentKey == KeyCode.ESCAPE) {
            pause();
            currentKey = null;
        }

        if (isGalagaMode) {
            stepScore = currentLevel.step(currentKey, elapsedTime);
            gameData.score += stepScore;
        } else {
            if (currentLevel.noBalls()) {
                loseLife();
            } else {
                stepScore = currentLevel.step(currentKey, elapsedTime);
                currentKey = null;
                gameData.score += stepScore;
            }
        }

        if (currentLevel.isComplete()) {
            nextLevel();
        }
    }

    private void loseLife() {
        gameData.lives -= 1;
        if (gameData.lives <= 0) {
            gameOver(false);
        } else {
            currentLevel.addBall();
        }
    }

    private void gameOver(boolean gameWon) {
    		if(gameWon) { winScreen(); }
    		else { loseScreen(); }
    	}

    private void loseScreen() {
        showEndScreen("GAME OVER");
    }

    private void winScreen() {
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
//        restartButton.setOnAction(e -> this.runGame());

        root.getChildren().addAll(text, restartButton);
        gameStage.setScene(scene);
        animation.stop();
    }
}