import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * explosives are dropped by a plane
 */
public class Explosive extends GameObject {
    private static final String IMAGE_FILE = "res/images/explosive.png";
    private static final List<Explosive> explosives = new ArrayList<>();
    private static final int DAMAGE = 500;
    private static final int RADIUS = 200;
    private static final int DELAY_SECOND = 2;
    private static final int FPS = 60;
    private static final int DELAY = FPS * DELAY_SECOND;

    private int frameCount;

    /**
     * drop an explosive
     * @param point where to be dropped
     */
    public Explosive(Point point) {
        super(point, IMAGE_FILE);
        this.frameCount = 0;
        explosives.add(this);
    }

    /**
     * remove all explosives
     */
    public static void reset(){
        explosives.clear();
    }

    /**
     * update the calling explosive
     */
    private void updateOne(){
        frameCount += ShadowDefend.getTimescale();
        if (frameCount >= DELAY){
            List<Slicer> slicers = Slicer.getSlicers();
            for (int i = slicers.size() - 1; i >= 0; i--){
                Slicer slicer = slicers.get(i);
                if (slicer.getCenter().distanceTo(this.getCenter()) <= RADIUS){
                    slicer.hit(DAMAGE);
                }
            }
            explosives.remove(this);
        }
    }

    /**
     * update all explosives
     */
    public static void update(){
        for (int i = explosives.size() - 1; i >= 0; i--){
            Explosive explosive = explosives.get(i);
            explosive.draw();
            explosive.updateOne();
        }
    }
}
