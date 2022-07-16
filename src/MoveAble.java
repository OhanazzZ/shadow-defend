import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * enable movable game units to move
 */
public interface MoveAble {
    default void move(Vector2 dx, Rectangle rect){
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }
}
