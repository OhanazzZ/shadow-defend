import bagel.util.Point;
import bagel.util.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 * a projectile is launched by a tank or super tank
 */
public abstract class Projectile extends GameObject implements MoveAble{
    private static final List<Projectile> projectiles = new ArrayList<>();
    private final Slicer slicer;
    private final int DAMAGE;
    private static final int SPEED = 10;

    /**
     * create a projectile
     *
     * @param point where it is at
     * @param imageSrc image file
     * @param slicer its target
     * @param DAMAGE how much damage it deals
     */
    public Projectile(Point point, String imageSrc, Slicer slicer, int DAMAGE) {
        super(point, imageSrc);
        this.slicer = slicer;
        this.DAMAGE = DAMAGE;
        projectiles.add(this);
    }

    /**
     * remove all projectiles
     */
    public static void reset(){
        projectiles.clear();
    }

    /**
     * update all projectiles
     */
    public static void update(){
        for (int i = projectiles.size() - 1; i >= 0; i--){
            Projectile projectile = projectiles.get(i);
            projectile.updateOne();
        }
    }

    /**
     * update the calling projectile
     */
    private void updateOne(){
        // if the slicer is dead
        if(slicer.getHealth() < 1){
            projectiles.remove(this);
            return;
        }
        Point currentPoint = getCenter();
        Point targetPoint = slicer.getCenter();
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance we are (in pixels) away from our target point, minus its radius (bounding box)
        double magnitude = distance.length();
        // Check if the projectile would move pass the slicer in the next frame
        if (magnitude <= SPEED * ShadowDefend.getTimescale()) {
            slicer.hit(DAMAGE);
            projectiles.remove(this);
        } else{
            move(distance.normalised().mul(SPEED * ShadowDefend.getTimescale()), getRect());
            // if the two's bounding box intercept
            if (slicer.getRect().intersects(this.getCenter())){
                slicer.hit(DAMAGE);
                projectiles.remove(this);
            }
        }
        super.draw();
    }
}
