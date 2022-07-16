import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * Basic unit of a game object. This is a modification of the Sprite Class in the sample solution from project 1.
 */
public abstract class GameObject {
    private final Image image;
    private final Rectangle rect;
    private double angle;

    protected GameObject(Point point, String imageSrc) {
        this.image = new Image(imageSrc);
        this.rect = image.getBoundingBoxAt(point);
        this.angle = 0;
    }

    protected Rectangle getRect() {
        return this.rect;
    }

    protected Point getCenter() {
        return getRect().centre();
    }

    protected void setAngle(double angle) {
        this.angle = angle;
    }

    protected void draw() {
        image.draw(getCenter().x, getCenter().y, new DrawOptions().setRotation(angle));
    }

    protected double getRadius(){
        return Math.max(image.getHeight(), image.getWidth());
    }

}
