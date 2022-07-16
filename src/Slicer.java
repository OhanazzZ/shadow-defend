import bagel.util.Point;
import bagel.util.Vector2;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic unit of a slicer. This is a modification of the Slicer Class in the sample solution from project 1.
 */
public abstract class Slicer extends GameObject implements MoveAble {

    private final double SPEED;
    private double health;
    private final int reward;
    private final int penalty;
    private final int nChildren;
    private final static ArrayList<Slicer> slicers = new ArrayList<>();

    private static List<Point> polyline;
    private int targetPointIndex;
    private boolean finished;

    /**
     * get the polyline of current map
     * @param polyline polyline of the map
     */
    public static void getPolyline(List<Point> polyline){
        Slicer.polyline = polyline;
    }

    /**
     * create a new slicer
     * @param imageSrc image address
     * @param speed slicer movement speed
     * @param health health
     * @param reward reward on death
     * @param penalty penalty when escaped
     * @param nChildren number of children to be spawned on death
     */
    protected Slicer(String imageSrc, double speed, int health, int reward, int penalty, int nChildren) {
        super(polyline.get(0), imageSrc);
        this.targetPointIndex = 1;
        this.finished = false;
        this.SPEED = speed;
        this.health = health;
        this.reward = reward;
        this.penalty = penalty;
        this.nChildren = nChildren;
        slicers.add(this);
    }

    /**
     * return a list of unfinished slicers
     * @return a list of unfinished slicers
     */
    public static ArrayList<Slicer> getSlicers() {
        return slicers;
    }

    /**
     * update all unfinished slicers
     */
    public static void update(){
        for (int i = slicers.size() - 1; i >= 0; i--){
            Slicer slicer = slicers.get(i);
            slicer.updateOne();
        }
    }

    /**
     * update the calling slicer
     */
    protected void updateOne() {
        if (finished) {
            return;
        }
        // Obtain where is currently is, and where we want to it be
        Point currentPoint = getCenter();
        Point targetPoint = polyline.get(targetPointIndex);
        // Convert them to vectors to perform some very basic vector math
        Vector2 target = targetPoint.asVector();
        Vector2 current = currentPoint.asVector();
        Vector2 distance = target.sub(current);
        // Distance we are (in pixels) away from our target point
        double magnitude = distance.length();
        // Check if we are close to the target point
        if (magnitude <= SPEED * ShadowDefend.getTimescale()) {
            // Check if we have reached the end
            if (targetPointIndex == polyline.size() - 1) {
                finished = true;
                slicers.remove(this);
                Level.loseLives(penalty);
                return;
            } else {
                // Make our focus the next point in the polyline
                targetPointIndex ++;
                move(distance, getRect());
            }
        } else{
            // Move towards the target point
            // We do this by getting a unit vector in the direction of our target, and multiplying it
            // by the speed of the slicer (accounting for the timescale)
            // Update current rotation angle to face target point
            move(distance.normalised().mul(SPEED * ShadowDefend.getTimescale()), getRect());
        }
        setAngle(Math.atan2(targetPoint.y - currentPoint.y, targetPoint.x - currentPoint.x));
        super.draw();
    }

    /**
     * return its remaining health
     * @return health
     */
    public double getHealth(){
        return health;
    }

    /**
     * if the slicer is hit by an explosive or a projectile
     * @param dmg damage dealt to the slicer
     */
    protected void hit(int dmg){
        health -= dmg;
        if (health < 1){
            finished = true;
            Level.setMoney(reward);
            spawnChildren();
            slicers.remove(this);
        }
    }

    /**
     * check if all slicers have finished
     * @return if all slicers have finished
     */
    public static boolean allFinished(){
        return slicers.isEmpty();
    }

    /**
     * spawn children on death
     */
    protected void spawnChildren(){
        for (int i = 0; i < nChildren; i++){
            Slicer slicer;
            if (this instanceof SuperSlicer){
                slicer = new RegularSlicer();
            } else if (this instanceof MegaSlicer){
                slicer = new SuperSlicer();
            } else if (this instanceof ApexSlicer){
                slicer = new MegaSlicer();
            } else{
                System.out.println("unrecognised Slicer type");
                return;
            }
            // move the newly spawned slicer to where its parent died
            slicer.move(this.getCenter().asVector().sub(slicer.getCenter().asVector()), slicer.getRect());
            slicer.setTargetPointIndex(this.getTargetPointIndex());
        }
    }

    /**
     * set target point index
     * @param target the new index
     */
    public void setTargetPointIndex(int target) {
        this.targetPointIndex = target;
    }

    /**
     * get index of the target point
     * @return index of the target point
     */
    public int getTargetPointIndex() {
        return targetPointIndex;
    }
}
