import javafx.scene.Node;

/**
 * Something that can be collided with.
 * All collidable things should be able to check collisions with a Ball
 * and respond when they get hit.
 */
public interface Collidable {
    // check if this collidable intersects the given ball
    boolean checkCollision(Ball ball);

    // called when this collidable has been hit by the ball
    void onCollision(Ball ball);

    // return the JavaFX node used for bounds checks / display
    Node getView();
}
