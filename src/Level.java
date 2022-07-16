import bagel.Window;
import bagel.map.TiledMap;
import java.util.ArrayList;
import java.util.List;

/**
 * a game contains multiple levels
 */
public class Level {
    private boolean completed;
    private final TiledMap map;
    private final List<Wave> waves;
    private Wave currWave;
    private int currWaveIndex;
    private boolean currWaveFinished;
    private static final int REWARD_BASE = 150;
    private static final int REWARD_BONUS = 100;

    private static final int INITIAL_CASH = 500;
    private static final int INITIAL_LIVES = 25;
    private static int money = INITIAL_CASH;
    private static int lives = INITIAL_LIVES;

    private static int WINDOW_HEIGHT;
    private static int WINDOW_WIDTH;

    /**
     * create a level
     * @param map the tiled map of the this level
     */
    private Level(TiledMap map){
        this.map = map;
        waves = Wave.createWave("res/levels/waves.txt");
        currWaveIndex = 0;
        currWave = waves.get(currWaveIndex);
        currWaveFinished = false;
    }

    /**
     * the "real" constructor. Read all map files and initialise all levels
     * @param nlevels number of levels
     * @param mapSource map source
     * @param mapType map file type
     * @return return a list of levels
     */
    public static List<Level> getLevels(int nlevels, String mapSource, String mapType, int height, int width){
        WINDOW_HEIGHT = height;
        WINDOW_WIDTH = width;
        List<Level> levels = new ArrayList<>();
        TiledMap map;
        for (int i = 0; i < nlevels; i++){
            map = new TiledMap(mapSource + (i + 1) + mapType);
            levels.add(new Level(map));
        }
        return levels;
    }

    /**
     * return the current map
     *
     * @return the current map
     */
    public TiledMap getMap(){
        return map;
    }

    /**
     * update the current level
     */
    public void update(){
        // update the current wave
        currWave.update();
        // check if the wave has finished
        if (currWave.hasFinished()){
            // set reward
            money += REWARD_BASE + REWARD_BONUS * (currWaveIndex + 1);
            // set status
            currWaveFinished = true;
            // check if the level is completed
            if (currWaveIndex == waves.size() - 1){
                completed = true;
                return;
            }
            // move on to the next wave
            currWaveIndex++;
            currWave = waves.get(currWaveIndex);
        }
    }

    /**
     * render the map and towers and possibly explosives
     */
    public void render(){
        map.draw(0, 0, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        Tower.update();
    }

    /**
     * initialise the game
     */
    public void initialise(){
        Slicer.getPolyline(map.getAllPolylines().get(0));
        Explosive.reset();
        Projectile.reset();
        Tower.reset();
        currWaveIndex = 0;
        lives = INITIAL_LIVES;
        money = INITIAL_CASH;
    }

    /**
     * return current wave number
     *
     * @return current wave number
     */
    public int getCurrWaveIndex() {
        return currWaveIndex;
    }

    /**
     * start the wave
     */
    public void start(){
        currWaveFinished = false;
    }

    /**
     * return if the current wave has finished
     * @return if the current wave has finished
     */
    public boolean isCurrWaveFinished(){
        return currWaveFinished;
    }

    /**
     * check whether the level is completed
     * @return whether the level is completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * get money
     *
     * @return money
     */
    public static int getMoney() {
        return money;
    }

    /**
     * set money by adding the difference
     *
     * @param diff the difference between the new amount and the current one
     */
    public static void setMoney(int diff) {
        money += diff;
    }

    /**
     * get remaining number of lives
     *
     * @return number of lives
     */
    public static int getLives() {
        return lives;
    }

    /**
     * take away lives. If it falls below 1, game over
     *
     * @param penalty amount of lives to be taken away
     */
    public static void loseLives(int penalty) {
        lives -= penalty;
        if (lives < 1 ){
            Window.close();
        }
    }
}
