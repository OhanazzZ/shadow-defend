import bagel.util.Point;

/**
 * a type of active tower
 */
public class Tank extends ActiveTower {
    private static final String IMAGE_FILE = "res/images/tank.png";
    private static final int RADIUS = 100;
    private static final int COOLDOWN_MILLISECONDS = 1000;

    /**
     * create a tank
     *
     * @param point where it is at
     */
    public Tank(Point point) {
        super(point, IMAGE_FILE, COOLDOWN_MILLISECONDS, RADIUS);
    }

    /**
     * launch a tank projectile
     *
     * @param slicer the target slicer
     */
    @Override
    protected void launchProjectile(Slicer slicer) {
        new TankProjectile(this.getCenter(), slicer);
    }
}
