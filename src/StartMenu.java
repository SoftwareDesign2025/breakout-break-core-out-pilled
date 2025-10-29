import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartMenu extends Game{
	
	private Scene startMenuScene;
	private boolean startGame;
	private Game game;
	private Button playButton;

	public StartMenu(int size_x, int size_y, Game game) {
		
			this.game = game;
			
			GridPane startMenuGridPane = new GridPane();
			startGame = false;
			
		    Text title = new Text("BREAKOUT!");
		    title.setFont(new Font(40));
		    title.setFill(Color.BLACK);
		    startMenuGridPane.add(title, 0, 0);
		    
		    playButton = new Button("New Game");
		    playButton.setOnAction(e -> {this.startButton();} );
		    startMenuGridPane.add(playButton, 0, 1);
		    startMenuGridPane.setConstraints(playButton, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		    startMenuGridPane.setAlignment(Pos.TOP_CENTER);
		    startMenuScene = new Scene(startMenuGridPane, size_x, size_y, Color.BLUE);

	}
	
	public Scene getScene() {
		return startMenuScene;
	} 
	
	private void startButton() {
		playButton.setText("Resume");
		playButton.setOnAction(e -> {game.resume();});
		game.runGame();
	}
}