/**
 * a type of slicer
 */
public class ApexSlicer extends Slicer {

    private static final String IMAGE_FILE = "res/images/apexslicer.png";
    private static final int nChildren = 4;

    public static final double SPEED = 1.0/2 * MegaSlicer.SPEED;
    public static final int INITIAL_HEALTH = 25 * RegularSlicer.INITIAL_HEALTH;
    public static final int REWARD = 150;
    public static final int PENALTY = nChildren * MegaSlicer.PENALTY;

    /**
     * create a apex slicer
     */
    public ApexSlicer() {
        super(IMAGE_FILE, SPEED, INITIAL_HEALTH, REWARD, PENALTY, nChildren);
    }
}
