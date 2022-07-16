import bagel.util.Point;

/**
 * a super tank projectile is launched by a super tank
 */
public class SuperTankProjectile extends Projectile {
    private static final String IMAGE_FILE = "res/images/supertank_projectile.png";
    private static final int DAMAGE = TankProjectile.DAMAGE * 3;

    /**
     * create a super tank projectile
     * @param point where it is launched
     * @param slicer its target
     */
    public SuperTankProjectile(Point point, Slicer slicer) {
        super(point, IMAGE_FILE, slicer, DAMAGE);
    }
}
