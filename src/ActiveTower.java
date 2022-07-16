import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * a type of tower
 */
public abstract class ActiveTower extends Tower {

    private boolean ready;
    private static final double FRAME_PER_MILLISECONDS = 60.0 / 1000;
    private final Rectangle areaOfEffect;

    /**
     * create an active tower
     * @param point where it is at
     * @param imageSrc image file
     * @param cooldown cool down between shots
     * @param radius radius
     */
    protected ActiveTower(Point point, String imageSrc, double cooldown, double radius) {
        super(point, imageSrc, cooldown * FRAME_PER_MILLISECONDS);
        this.ready = true;
        Point areaTopLeft = this.getCenter().asVector().sub(new Vector2(radius/2, radius/2)).asPoint();
        this.areaOfEffect = new Rectangle(areaTopLeft, radius, radius);
    }

    /**
     * update the calling active tower
     */
    protected void updateOne(){
        // if ready to launch a projectile
        if (ready){
            // the launch was successful
            if (launch()){
                ready = false;
            }
        } else{
            // add to cooldown
            setFrameCount(ShadowDefend.getTimescale());
            // if cooldown is met
            if (getFrameCount() >= getCooldown()){
                setFrameCount(-getCooldown());
                ready = true;
            }
        }
    }

    /**
     * try to launch a projectile
     * @return if the launch was successful
     */
    private boolean launch(){
        for (Slicer slicer: Slicer.getSlicers()){
            if (areaOfEffect.intersects(slicer.getRect())){
                launchProjectile(slicer);
                setAngle(Math.atan2(slicer.getCenter().y - this.getCenter().y, slicer.getCenter().x - this.getCenter().x) + Math.PI/2);
                return true;
            }
        }
        return false;
    }

    /**
     * launch a projectile, the type depends on the tower
     * @param slicer the target slicer
     */
    protected abstract void launchProjectile(Slicer slicer);
}
