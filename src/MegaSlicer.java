/**
 * a type of slicer
 */
public class MegaSlicer extends Slicer {

    private static final String IMAGE_FILE = "res/images/megaslicer.png";
    private static final int nChildren = 2;

    public static final double SPEED = SuperSlicer.SPEED;
    public static final int INITIAL_HEALTH = 2 * SuperSlicer.INITIAL_HEALTH;
    public static final int REWARD = 10;
    public static final int PENALTY = nChildren * SuperSlicer.PENALTY;

    /**
     * create a mega slicer
     */
    public MegaSlicer() {
        super(IMAGE_FILE, SPEED, INITIAL_HEALTH, REWARD, PENALTY, nChildren);
    }
}
