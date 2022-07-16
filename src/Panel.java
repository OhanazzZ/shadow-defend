import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The abstract parent class for status panel and buy panel.
 */
public abstract class Panel {
    private final Image IMG;
    private final Point TOP_LEFT;
    private final int WINDOW_HEIGHT;
    private final int WINDOW_WIDTH;
    private final Rectangle rect;
    protected static final String FONT_ADDRESS = "res/fonts/DejaVuSans-Bold.ttf";

    /**
     * create a panel
     * @param HEIGHT window height
     * @param WIDTH window width
     * @param imageAddress image file address
     * @param point top left corner of the panel
     */
    protected Panel(int HEIGHT, int WIDTH, String imageAddress, Point point) {
        this.WINDOW_HEIGHT = HEIGHT;
        this.WINDOW_WIDTH = WIDTH;
        this.IMG = new Image(imageAddress);
        this.TOP_LEFT = point;
        this.rect = new Rectangle(TOP_LEFT, IMG.getWidth(), IMG.getHeight());
    }

    /**
     * draw the panel
     */
    protected void draw(){
        IMG.drawFromTopLeft(TOP_LEFT.x, TOP_LEFT.y);
    }

    /**
     * get window height
     * @return window height
     */
    protected int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    /**
     * get window width
     * @return window width
     */
    protected int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    /**
     * get the centre of panel image
     * @return the centre of panel image
     */
    protected Point imageCenter(){
        return new Rectangle(IMG.getBoundingBox()).centre();
    }

    /**
     * get the rectangle of this panel
     * @return the rectangle of this panel
     */
    protected Rectangle getRect(){
        return this.rect;
    }
}
