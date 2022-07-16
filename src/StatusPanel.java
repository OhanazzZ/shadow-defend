import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Point;

/**
 * Represents the Status Panel. Contains the draw method to draw it.
 */
public class StatusPanel extends Panel {
    private static final String IMAGE_ADDRESS = "res/images/statuspanel.png";
    private static final int FONT_SIZE = 14;
    private static final Font FONT = new Font(FONT_ADDRESS, FONT_SIZE);

    private static final int VERTICAL_OFFSET = 8;

    /**
     * Create the Status Panel
     * @param HEIGHT the height of the game window
     * @param WIDTH the width of the game window
     */
    public StatusPanel(int HEIGHT, int WIDTH){
        super(HEIGHT, WIDTH, IMAGE_ADDRESS, new Point(0, HEIGHT - new Image(IMAGE_ADDRESS).getHeight()));
    }

    /**
     * Draw the content of the Status Panel
     * @param currWaveNumber the current wave number
     */
    public void draw(int currWaveNumber, String status, boolean isBuying){
        super.draw();
        drawWave(currWaveNumber);
        drawTimeScale();
        drawStatus(status, isBuying);
        drawLives();
    }

    /**
     * draw the wave section
     */
    private void drawWave(int currWaveNumber){
        final int WAVE_OFFSET = 10;
        final String WAVE = "Wave: ";
        FONT.drawString(WAVE + (currWaveNumber + 1), WAVE_OFFSET, getWindowHeight() - VERTICAL_OFFSET);
    }

    /**
     * draw the time scale section
     */
    private void drawTimeScale(){
        int timeScale = ShadowDefend.getTimescale();
        final int TIMESCALE_OFFSET = 200;
        final int MIN_TIMESCALE = 1;
        final DrawOptions GREEN = new DrawOptions().setBlendColour(0, 128,0);
        final String TIMESCALE = "Time Scale: ";
        if (timeScale > MIN_TIMESCALE) {
            FONT.drawString(TIMESCALE + timeScale, TIMESCALE_OFFSET, getWindowHeight() - VERTICAL_OFFSET, GREEN);
        } else {
            FONT.drawString(TIMESCALE + timeScale, TIMESCALE_OFFSET, getWindowHeight() - VERTICAL_OFFSET);
        }
    }

    /**
     * draw the status section
     */
    private void drawStatus(String status, boolean isBuying){
        final int STATUS_OFFSET = 450;
        final String STATUS = "Status: ";
        if (!status.equals(ShadowDefend.WINNER)) {
            if (isBuying){
                status = ShadowDefend.PLACING;
            }
        }
        FONT.drawString(STATUS + status, STATUS_OFFSET, getWindowHeight() - VERTICAL_OFFSET);
    }

    /**
     * draw the live section
     */
    private void drawLives(){
        int lives = Level.getLives();
        final int LIVES_OFFSET = 930;
        final String LIVES = "Lives: ";
        FONT.drawString(LIVES + lives, LIVES_OFFSET, getWindowHeight() - VERTICAL_OFFSET);
    }
}
