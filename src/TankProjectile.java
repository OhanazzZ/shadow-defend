import bagel.util.Point;

/**
 * a tank projectile is launched by a tank
 */
public class TankProjectile extends Projectile {
    private static final String IMAGE_FILE = "res/images/tank_projectile.png";
    public static final int DAMAGE = 1;

    /**
     * create a tank projectile
     * @param point where it is launched
     * @param slicer its target
     */
    public TankProjectile(Point point, Slicer slicer) {
        super(point, IMAGE_FILE, slicer, DAMAGE);
    }
}
