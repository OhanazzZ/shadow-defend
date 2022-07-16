import bagel.util.Point;
import bagel.util.Vector2;
import java.util.List;

/**
 * a type of tower
 */
public class Plane extends Tower implements MoveAble {

    private static final String IMAGE_FILE = "res/images/airsupport.png";

    private static int WINDOW_WIDTH;
    private static int WINDOW_HEIGHT;
    private static final int SPEED = 3;
    private static final int randomCoolDownAdjustment = 1;
    private static final int FPS = 60;

    private static boolean nextOneFlyHorizontally = true;
    private final boolean flyHorizontally;


    /**
     * get window size (a plane needs to know its boundary)
     *
     * @param HEIGHT window height
     * @param WIDTH window width
     */
    public static void getWindowSize(int HEIGHT, int WIDTH){
        Plane.WINDOW_WIDTH = WIDTH;
        Plane.WINDOW_HEIGHT = HEIGHT;
    }

    /**
     * create a plane
     * @param point where to be created
     */
    public Plane(Point point) {
        super(point, IMAGE_FILE, randomCooldown() * FPS);
        this.flyHorizontally = nextOneFlyHorizontally;
        // alternate between each plane
        nextOneFlyHorizontally = !nextOneFlyHorizontally;
        // move its spawn position to the edge of screen, and set the right angle
        if (flyHorizontally){
            move(new Vector2(-this.getCenter().x, 0), getRect());
            setAngle(Math.PI / 2);
        } else {
            move(new Vector2(0, -this.getCenter().y), getRect());
            setAngle(Math.PI);
        }
    }

    /**
     * let the plane fly
     */
    private void fly(){
        List<Tower> towers = getTowers();
        Vector2 direction;
        if (flyHorizontally){
            // fly toward the right
            direction = new Vector2(SPEED * ShadowDefend.getTimescale(), 0);
            move(direction, getRect());
            // if it is outside of the map, remove it
            if (getRect().left() > WINDOW_WIDTH){
                towers.remove(this);
            }
        } else {
            // fly toward the bottom
            direction = new Vector2(0, SPEED * ShadowDefend.getTimescale());
            move(direction, getRect());
            // if it is outside of the map, remove it
            if (getRect().top() > WINDOW_HEIGHT){
                towers.remove(this);
            }
        }
    }

    /**
     * update the calling plane
     */
    protected void updateOne(){
        fly();
        setFrameCount(ShadowDefend.getTimescale());
        if (getFrameCount() >= getCooldown()){
            setFrameCount(-getCooldown());
            setCooldown(randomCooldown() * FPS);
            new Explosive(this.getCenter());
        }
    }

    /**
     * get a random cooldown
     * @return a random cooldown
     */
    private static double randomCooldown(){
        return Math.random() + randomCoolDownAdjustment;
    }

    public static void reset(){
        nextOneFlyHorizontally = true;
    }
}
