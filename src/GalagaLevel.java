import javafx.scene.input.KeyCode;
import java.util.ArrayList;
import java.util.Iterator;

public class GalagaLevel extends Level {

    PlayerShip player;
    protected ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    private int enemyMoveCounter = 0; // for controlling speed

    public GalagaLevel() {
        // Initialize player
        player = new PlayerShip();
        paddles.add(player); // reuse paddle list
        root.getChildren().add(player.getView());

        // Initialize enemies in a grid
        int rows = 3;
        int cols = 6;
        int startX = 50;
        int startY = 50;
        int xSpacing = 60;
        int ySpacing = 50;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Enemy enemy = new Enemy(startX + c * xSpacing, startY + r * ySpacing, 1); // health=1
                enemies.add(enemy);
                root.getChildren().add(enemy.getView());
            }
        }
    }

    @Override
    public int step(KeyCode currentKey, double elapsedTime) {
        pointsPerStep = 0;

        // 1️⃣ Move player and shoot bullets
        if (player != null) {
            player.move(currentKey, elapsedTime);
            if (currentKey == KeyCode.SPACE) {
                player.shoot(); // adds bullet to player's internal list
            }
        }

        // Add newly shot bullets to level
        for (Bullet b : player.getBullets()) {
            if (!bullets.contains(b)) {
                bullets.add(b);
                root.getChildren().add(b.getView());
            }
        }

        // 2️⃣ Move bullets
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet b = bulletIter.next();
            b.move(elapsedTime);
            if (b.getView().getY() + b.getView().getFitHeight() < 0) {
                root.getChildren().remove(b.getView());
                bulletIter.remove();
            }
        }

        // 3️⃣ Move enemies (slower)
        enemyMoveCounter++;
        if (enemyMoveCounter >= 6) { // move every 6 frames (~10 times/sec at 60fps)
            for (Enemy e : enemies) {
                e.move(elapsedTime);
            }
            enemyMoveCounter = 0;
        }

        // 4️⃣ Check collisions between bullets and enemies
        bulletIter = bullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet b = bulletIter.next();
            Iterator<Enemy> enemyIter = enemies.iterator();
            while (enemyIter.hasNext()) {
                Enemy e = enemyIter.next();
                if (e.getView().getBoundsInParent().intersects(b.getView().getBoundsInParent())) {
                    root.getChildren().removeAll(e.getView(), b.getView());
                    enemyIter.remove();
                    bulletIter.remove();
                    pointsPerStep += 10;
                    break; // bullet removed, move to next bullet
                }
            }
        }

        // 5️⃣ Check lose condition: any enemy reaches player's Y
        for (Enemy e : enemies) {
            double enemyBottom = ((javafx.scene.image.ImageView)e.getView()).getY() + ((javafx.scene.image.ImageView)e.getView()).getFitHeight();
            double playerY = ((javafx.scene.image.ImageView)player.asNode()).getY();
            if (enemyBottom >= playerY) {
                enemies.clear(); // trigger game over
                break;
            }
        }

        return pointsPerStep;
    }

    @Override
    public boolean isComplete() {
        return enemies.isEmpty();
    }
    
    
}