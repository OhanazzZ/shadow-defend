/**
 * a wave consists of events
 */
public abstract class Event {
    public static final String SPAWN = "spawn";
    public static final String DELAY = "delay";

    protected double FRAME_PER_MILLISECONDS = 60.0 / 1000;
    private double frameCount;
    private boolean finished;
    protected final double delay;

    /**
     * constructor of an event
     * @param delayMSecond delay measured in seconds
     */
    protected Event(int delayMSecond){
        this.frameCount = 0;
        this.finished = false;
        // convert milliseconds to frames
        this.delay = FRAME_PER_MILLISECONDS * delayMSecond;
    }

    /**
     * update frames passed
     */
    protected void update(){
        frameCount += ShadowDefend.getTimescale();
    }


    /* ************************ getters & setters ************************ */

    /**
     * check whether the even is finished
     * @return whether the even is finished
     */
    protected boolean isFinished(){
        return finished;
    }

    /**
     * get frame count
     * @return frame count
     */
    protected double getFrameCount() {
        return frameCount;
    }

    /**
     * reset the frame count of this event
     * @param offset the amount of to be offset
     */
    public void resetFrameCount(int offset) {
        this.frameCount -= offset;
    }

    /**
     * update the status to be finished
     */
    protected void finish() {
        this.finished = true;
    }
}
