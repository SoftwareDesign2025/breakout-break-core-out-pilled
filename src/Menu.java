import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu {

    private Scene menuScene;
    private Game game;
    private Button breakoutButton;
    private Button galagaButton;

    public Menu(int size_x, int size_y, Game game) {
        this.game = game;

        GridPane gridPane = new GridPane();

        Text title = new Text("CHOOSE GAME MODE");
        title.setFont(new Font(40));
        title.setFill(Color.BLACK);
        gridPane.add(title, 0, 0);

        // Breakout button
        breakoutButton = new Button("Breakout");
        breakoutButton.setOnAction(e -> startBreakout());
        gridPane.add(breakoutButton, 0, 1);
        gridPane.setConstraints(breakoutButton, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);

        // Galaga button
        galagaButton = new Button("Galaga");
        galagaButton.setOnAction(e -> startGalaga());
        gridPane.add(galagaButton, 0, 2);
        gridPane.setConstraints(galagaButton, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER);

        gridPane.setAlignment(Pos.TOP_CENTER);

        menuScene = new Scene(gridPane, size_x, size_y, Color.BLUE);
    }

    public Scene getScene() {
        return menuScene;
    }

    private void startBreakout() {
        game.isGalagaMode = false; // set mode before starting game
        game.runGame();
    }

    private void startGalaga() {
        game.isGalagaMode = true; // set mode before starting game
        game.runGame();
    }
}
