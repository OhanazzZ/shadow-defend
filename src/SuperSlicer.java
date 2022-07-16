/**
 * a type of slicer
 */
public class SuperSlicer extends Slicer {

    private static final String IMAGE_FILE = "res/images/superslicer.png";
    private static final int nChildren = 2;

    public static final double SPEED = 3.0/4 * RegularSlicer.SPEED;
    public static final int INITIAL_HEALTH = RegularSlicer.INITIAL_HEALTH;
    public static final int REWARD = 15;
    public static final int PENALTY = nChildren * RegularSlicer.PENALTY;

    /**
     * create a super slicer
     */
    public SuperSlicer() {
        super(IMAGE_FILE, SPEED, INITIAL_HEALTH, REWARD, PENALTY, nChildren);
    }
}
