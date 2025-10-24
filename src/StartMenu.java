import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StartMenu extends Game {
	
	private Scene startMenuScene;
	private boolean startGame;

	public StartMenu() {
		
		synchronized(this) {
			GridPane startMenuGridPane = new GridPane();
			startGame = false;
			
		    Text title = new Text("BREAKOUT!");
		    title.setFont(new Font(40));
		    title.setFill(Color.WHITE);
		    startMenuGridPane.add(title, 0, 0);
		    
		    Button playButton = new Button("New Game");
		    startMenuGridPane.add(playButton, 0, 1);
		    startMenuGridPane.setConstraints(playButton, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		    
		    // @TODO: change event handling to avoid inheritance
		    playButton.setOnAction(e -> startGame());
		    
		    startMenuGridPane.setAlignment(Pos.TOP_CENTER);
		    startMenuScene = new Scene(startMenuGridPane, 480, 640, Color.BLACK);
		}

	}
	
	public Scene getScene() {
		return startMenuScene;
	}
    
}