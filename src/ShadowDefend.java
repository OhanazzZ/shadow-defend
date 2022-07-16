import bagel.AbstractGame;
import bagel.Input;
import bagel.Keys;
import java.util.List;

/**
 * a tower defend game
 */
public class ShadowDefend extends AbstractGame {
    // window size
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    // set up levels
    private static final int nLevels = 2;
    private Level currLevel;
    private final List<Level> levels;
    private int currLevelIndex;
    // set up map and map files
    private static final String MAP_SOURCE_BASE = "res/levels/";
    private static final String MAP_FILE_TYPE = ".tmx";
    // game status is made constant and available to other game elements
    public static final String WINNER = "WINNER!";
    public static final String PLACING = "Placing";
    public static final String WAVE_IN_PROGRESS = "Wave In Progress";
    public static final String AWAITING_START = "Awaiting Start";
    private static String status = AWAITING_START;
    // set up panels
    private final BuyPanel buyPanel;
    private final StatusPanel statusPanel;
    // time control control
    private static final int MIN_TIME_SCALE = 1;
    private static int timeScale = MIN_TIME_SCALE;


    /**
     * Creates a new instance of the ShadowDefend game
     */
    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
        // set up panels
        buyPanel = new BuyPanel(HEIGHT, WIDTH);
        statusPanel = new StatusPanel(HEIGHT, WIDTH);
        // plane needs to know the boundary
        Plane.getWindowSize(HEIGHT, WIDTH);
        // initialise all levels
        levels = Level.getLevels(nLevels, MAP_SOURCE_BASE, MAP_FILE_TYPE, HEIGHT, WIDTH);
        // assign current level
        currLevelIndex = 0;
        currLevel = levels.get(currLevelIndex);
        currLevel.initialise();
    }

    /**
     * The entry-point for the game
     *
     * @param args Optional command-line arguments
     */
    public static void main(String[] args) {
        new ShadowDefend().run();
    }

    /**
     * Update the state of the game, potentially reading from input
     *
     * @param input The current mouse/keyboard state
     */
    @Override
    protected void update(Input input) {
        // check if the time scale needs to be adjusted and enable such adjustment
        adjustTimeScale(input);

        // render the map and towers
        currLevel.render();

        // if the game is won, there is no need to update
        if (!status.equals(WINNER)){
            // otherwise, if the current wave is in progress, update it
            if (status.equals(WAVE_IN_PROGRESS)) {
                currLevel.update();
                checkProgress();
            } else {
                // otherwise, if keyboard input to start (key S) is received
                if (input.wasPressed(Keys.S)) {
                    status = WAVE_IN_PROGRESS;
                    currLevel.start();
                }
            }
        }

        // check if the player is buying and enable the purchase
        buyPanel.buy(input, currLevel.getMap(), statusPanel.getRect());

        // draw the buy panel and status panel
        buyPanel.draw();
        statusPanel.draw(currLevel.getCurrWaveIndex(), status, buyPanel.isBuying());
    }


    /* ********************** extra helper functions ********************** */

    /**
     * adjust time scale
     * @param input keyboard input
     */
    private void adjustTimeScale(Input input){
        // L corresponds to increasing the time scale multiplier
        if (input.wasPressed(Keys.L)){
            timeScale++;
        }
        // K corresponds to decreasing the time scale multiplier
        if (input.wasPressed(Keys.K) && timeScale > MIN_TIME_SCALE){
            timeScale--;
        }
    }

    /**
     * get time scale
     * @return time scale
     */
    public static int getTimescale() {
        return timeScale;
    }

    /**
     * check game progress, update the current level and status wherever appropriate
     */
    private void checkProgress(){
        if (currLevel.isCurrWaveFinished()){
            status = AWAITING_START;
        }
        // check if the current level has finished
        if (currLevel.isCompleted()){
            // check if the map has finished
            if (currLevelIndex == levels.size() - 1){
                status = WINNER;
                return;
            }
            reset();
        }
    }

    /**
     * helper function to reset the game
     */
    private void reset(){
        status = AWAITING_START;
        timeScale = MIN_TIME_SCALE;
        buyPanel.stopBuying();
        currLevel = levels.get(++currLevelIndex);
        currLevel.initialise();
    }

}