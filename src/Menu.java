import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu extends Game{
	
	private Scene menuScene;
	private Game game;
	private Button playButton;

	public Menu(int size_x, int size_y, Game game) {
		
			this.game = game;
			
			GridPane gridPane = new GridPane();
			
		    Text title = new Text("BREAKOUT!");
		    title.setFont(new Font(40));
		    title.setFill(Color.BLACK);
		    gridPane.add(title, 0, 0);
		    
		    playButton = new Button("New Game");
		    playButton.setOnAction(e -> {this.startButton();} );
		    gridPane.add(playButton, 0, 1);
		    gridPane.setConstraints(playButton, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		    gridPane.setAlignment(Pos.TOP_CENTER);
		    
		    menuScene = new Scene(gridPane, size_x, size_y, Color.BLUE);

	}
	
	public Scene getScene() {
		return menuScene;
	} 
	
	private void startButton() {
		playButton.setText("Resume");
		playButton.setOnAction(e -> {game.resume();});
		game.runGame();
	}
}