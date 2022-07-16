/**
 * spawn event is a type of event
 */
public class SpawnEvent extends Event{
    private final int nSlicers;
    private int nSpawned;
    private final String slicerType;
    private static final String REGULAR_SLICER = "slicer";
    private static final String SUPER_SLICER = "superslicer";
    private static final String Mega_SLICER = "megaslicer";
    private static final String APEX_SLICER = "apexslicer";

    /**
     * create a spawn event instance
     *
     * @param nSlicers number of slicers in this event
     * @param slicerType the type of slicers
     * @param delayMSecond delay measured in seconds
     */
    public SpawnEvent(int nSlicers, String slicerType, int delayMSecond) {
        super(delayMSecond);
        this.nSlicers = nSlicers;
        this.slicerType = slicerType;
        this.nSpawned = 0;
    }

    /**
     * update the event
     */
    public void update(){
        super.update();
        // spawn the first one
        if (nSpawned != 0) {
            // spawn the remaining ones if conditions are met
            if (getFrameCount() > delay && nSpawned < nSlicers) {
                resetFrameCount((int) delay);
            // early return if spawn conditions are not met
            } else {
                return;
            }
        }
        // spawn the slicer
        nSpawned ++;
        switch (slicerType){
            case REGULAR_SLICER:
                new RegularSlicer();
                break;
            case SUPER_SLICER:
                new SuperSlicer();
                break;
            case Mega_SLICER:
                new MegaSlicer();
                break;
            case APEX_SLICER:
                new ApexSlicer();
                break;
        }
        // if all spawned, the event is finished
        if (nSpawned==nSlicers){
            finish();
        }
    }
}
