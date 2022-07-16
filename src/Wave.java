import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The game consists of waves
 */
public class Wave {
    private int currEventIndex;
    private Event currEvent;
    private final List<Event> events;
    private boolean finished;

    /**
     * constructor for wave
     *
     * @param events the list of events in this wave
     */
    private Wave(List<Event> events) {
        this.currEventIndex = 0;
        this.events = events;
        this.currEvent = events.get(currEventIndex);
        this.finished = false;
    }

    /**
     * read the wave source file and initialise all waves
     *
     * @param waveSrc address of the wave file
     * @return a list of waves
     */
    public static List<Wave> createWave(String waveSrc){
        List<Wave> waves = new ArrayList<>();
        int currWave = 0;
        String[] eventInput;
        Wave wave;
        Event event;
        List<Event> events = new ArrayList<>();
        // read the file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(waveSrc))) {
            String text;
            while ((text=br.readLine())!=null) {
                eventInput = text.split(",");
                // if this is a new wave
                if (Integer.parseInt(eventInput[0]) != currWave){
                    // add this to waves if it is not the first wave
                    if (currWave != 0){
                        wave = new Wave(events);
                        waves.add(wave);
                        events = new ArrayList<>();
                    }
                    currWave ++;
                }
                // add event according to its type
                // indexing is done according to its position as described in the spec
                if (eventInput[1].equals(Event.SPAWN)){
                    event = new SpawnEvent(Integer.parseInt(eventInput[2]), eventInput[3],Integer.parseInt(eventInput[4]));
                }else{
                    event = new DelayEvent(Integer.parseInt(eventInput[2]));
                }
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // add the final wave to the list
        wave = new Wave(events);
        waves.add(wave);
        return waves;
    }

    /**
     * update the current wave
     */
    public void update(){
        currEvent.update();
        if (currEvent.isFinished()){
            // check if it is the last event in this wave
            if (currEventIndex == events.size() - 1){
                // check if all slicers are finished
                if (Slicer.allFinished()){
                    finished = true;
                    return;
                }
            } else{
                // advance to next event
                currEvent = events.get(++currEventIndex);
            }
        }
        // projectiles and slicers are part of the wave
        Projectile.update();
        Slicer.update();
    }

    /**
     * check if the wave has finished
     * @return whether the wave has finished
     */
    public boolean hasFinished() {
        return finished;
    }
}
