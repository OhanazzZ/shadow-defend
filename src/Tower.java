import bagel.util.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * a tower targets and deals damage to slicers
 */
public abstract class Tower extends GameObject{
    private static final List<Tower> towers = new ArrayList<>();
    private double frameCount;
    private double cooldown;

    /**
     * create a tower
     * @param point whether it is at
     * @param imageSrc image file address
     * @param cooldown cool down between each shot
     */
    protected Tower(Point point, String imageSrc, double cooldown) {
        super(point, imageSrc);
        this.frameCount = 0;
        this.cooldown = cooldown;
        towers.add(this);
    }

    /**
     * clear all tower
     */
    public static void reset(){
        towers.clear();
        Plane.reset();
    }

    /**
     * if the tower overlaps with a position
     * @param point a position to be checked
     * @return if the tower overlaps with a point
     */
    public static boolean overlap(Point point){
        for (Tower tower : towers){
            if (tower.getRect().intersects(point)){
                return true;
            }
        }
        return false;
    }

    /**
     * update all towers and possibly explosives
     */
    public static void update(){
        for (int i = towers.size() - 1; i >= 0; i--){
            Tower tower = towers.get(i);
            tower.updateOne();
            tower.draw();
        }
        Explosive.update();
    }

    /**
     * update the calling tower
     */
    protected abstract void updateOne();

    /**
     * set cooldown
     * @param cooldown the new cooldown
     */
    protected void setCooldown(double cooldown){
        this.cooldown = cooldown;
    }

    /**
     * get cooldown
     * @return cooldown
     */
    protected double getCooldown() {
        return cooldown;
    }

    /**
     * get the list of towers
     * @return the list of towers
     */
    protected static List<Tower> getTowers(){
        return towers;
    }

    /**
     * add difference to the frame count
     * @param diff the difference to be added
     */
    protected void setFrameCount(double diff){
        frameCount += diff;
    }

    /**
     * get frame count
     * @return frame count
     */
    protected double getFrameCount() {
        return frameCount;
    }
}
