import bagel.util.Point;

/**
 * a type of active tower
 */
public class SuperTank extends ActiveTower {
    private static final String IMAGE_FILE = "res/images/supertank.png";
    private static final int RADIUS = 150;
    private static final int COOLDOWN_MILLISECONDS = 500;

    /**
     * create a super tank
     *
     * @param point where it is at
     */
    public SuperTank(Point point) {
        super(point, IMAGE_FILE, COOLDOWN_MILLISECONDS, RADIUS);
    }

    /**
     * launch a super tank projectile
     *
     * @param slicer the target slicer
     */
    @Override
    protected void launchProjectile(Slicer slicer) {
        new SuperTankProjectile(this.getCenter(), slicer);
    }
}
