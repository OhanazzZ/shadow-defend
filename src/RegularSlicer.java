/**
 * a type of slicer
 */
public class RegularSlicer extends Slicer {

    private static final String IMAGE_FILE = "res/images/slicer.png";
    private static final int nChildren = 0;

    public static final int SPEED = 2;
    public static final int INITIAL_HEALTH = 1;
    public static final int REWARD = 2;
    public static final int PENALTY = 1;

    /**
     * create a regular slicer
     */
    public RegularSlicer() {
        super(IMAGE_FILE, SPEED, INITIAL_HEALTH, REWARD, PENALTY, nChildren);
    }
}
