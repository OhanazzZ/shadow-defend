import bagel.*;
import bagel.map.TiledMap;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * Represents the Buy Panel. Contains the draw method to draw it.
 */
public class BuyPanel extends Panel {

    private static final String IMAGE_ADDRESS = "res/images/buypanel.png";
    private static final Point LOCATION = new Point(0,0);
    private static final String MONEY_SYMBOL = "$";
    private static final String BLOCKED_PROPERTY = "blocked";

    private static boolean isBuying = false;
    private static String item;

    private static final String TANK = "tank";
    private static final String SUPER_TANK = "super tank";
    private static final String PLANE = "plane";
    private static final Image TANK_IMAGE = new Image("res/images/tank.png");
    private static final Image SUPER_TANK_IMAGE = new Image("res/images/supertank.png");
    private static final Image PLANE_IMAGE = new Image("res/images/airsupport.png");
    private static final int OFFSET = 64;
    private static final int ABOVE_PANEL = 10;
    private static final int GAP = 120;

    private final Point TANK_POSITION = new Point(OFFSET, imageCenter().y - ABOVE_PANEL);
    private final Point SUPER_TANK_POSITION = new Point(OFFSET + GAP, imageCenter().y - ABOVE_PANEL);
    private final Point PLANE_POSITION = new Point(OFFSET + GAP + GAP, imageCenter().y - ABOVE_PANEL);
    private final Rectangle TANK_RECT = TANK_IMAGE.getBoundingBoxAt(TANK_POSITION);
    private final Rectangle SUPER_TANK_RECT = TANK_IMAGE.getBoundingBoxAt(SUPER_TANK_POSITION);
    private final Rectangle PLANE_RECT = TANK_IMAGE.getBoundingBoxAt(PLANE_POSITION);

    private final int TANK_PRICE = 250;
    private final int SUPER_TANK_PRICE = 600;
    private final int PLANE_PRICE = 500;

    /**
     * Create the Buy Panel
     * @param HEIGHT the height of the game window
     * @param WIDTH the width of the game window
     */
    public BuyPanel(int HEIGHT, int WIDTH){
        super(HEIGHT, WIDTH, IMAGE_ADDRESS, LOCATION);
    }

    /**
     * process and handle buy request
     *
     * @param input mouse input
     * @param map the map
     * @param statusPanelRect the rectangle of status panel
     */
    public void buy(Input input, TiledMap map, Rectangle statusPanelRect){
        int money = Level.getMoney();
        Point mouse = input.getMousePosition();
        // if is buying
        if(isBuying){
            // if it is a valid place to put the tower
            if (validPlace(mouse, map, statusPanelRect)) {
                // draw the item at where the mouse is, allow the purchase if the mouse left click
                switch (item) {
                    case TANK:
                        TANK_IMAGE.draw(mouse.x, mouse.y);
                        if (input.wasPressed(MouseButtons.LEFT)){
                            Level.setMoney(-TANK_PRICE);
                            new Tank(mouse);
                            isBuying = false;
                        }
                        break;
                    case SUPER_TANK:
                        SUPER_TANK_IMAGE.draw(mouse.x, mouse.y);
                        if (input.wasPressed(MouseButtons.LEFT)){
                            Level.setMoney(-SUPER_TANK_PRICE);
                            new SuperTank(mouse);
                            isBuying = false;
                        }
                        break;
                    case PLANE:
                        PLANE_IMAGE.draw(mouse.x, mouse.y);
                        if (input.wasPressed(MouseButtons.LEFT)){
                            Level.setMoney(-PLANE_PRICE);
                            new Plane(mouse);
                            isBuying = false;
                        }
                        break;
                }
            }
            // cancel the purchase
            if (input.wasPressed(MouseButtons.RIGHT)){
                isBuying = false;
            }
        } else{
            // if left click, determined the tank to be purchased
            if (input.wasPressed(MouseButtons.LEFT)){
                if (TANK_RECT.intersects(mouse) && money >= TANK_PRICE){
                    item = TANK;
                    isBuying = true;
                } else if (SUPER_TANK_RECT.intersects(mouse) && money >= SUPER_TANK_PRICE){
                    item = SUPER_TANK;
                    isBuying = true;
                } else if (PLANE_RECT.intersects(mouse) && money >= PLANE_PRICE){
                    item = PLANE;
                    isBuying = true;
                }
            }
        }
    }

    // check whether it is valid to place the tower
    // add tower interception check to it after implementing towers

    /**
     * check whether it is valid to place the tower
     *
     * @param mouse where the mouse is at
     * @param map the map
     * @param statusPanelRect the rectangle occupied by the status panel
     * @return check whether it is valid to place the tower
     */
    private boolean validPlace(Point mouse, TiledMap map, Rectangle statusPanelRect){
        return !map.hasProperty((int) mouse.x, (int) mouse.y, BLOCKED_PROPERTY)
                && !statusPanelRect.intersects(mouse)
                && !this.getRect().intersects(mouse)
                && !Tower.overlap(mouse);
    }


    /**
     * Draw the content of the Buy Panel
     */
    public void draw(){
        super.draw();
        drawItems();
        drawKeyBinds();
        drawMoney();
    }

    /**
     * display money
     */
    private void drawMoney(){

        // constant definition
        final int MONEY_H_OFFSET = 200;
        final int MONEY_V_OFFSET = 65;
        final int MONEY_FONT_SIZE = 50;
        final Font MONEY_FONT = new Font(FONT_ADDRESS, MONEY_FONT_SIZE);

        int money = Level.getMoney();

        // draw money
        MONEY_FONT.drawString(MONEY_SYMBOL + money, getWindowWidth() - MONEY_H_OFFSET, MONEY_V_OFFSET);
    }

    /**
     * display key binds
     */
    private void drawKeyBinds(){

        // constant definition
        final String KEY_BIND_HEADER = "Key Binds:";
        final String KEY_BIND_LINE1 = "S - Start Wave";
        final String KEY_BIND_LINE2 = "L - Increase Timescale";
        final String KEY_BIND_LINE3 = "K - Decrease Timescale";

        final int KEY_BIND_H_OFFSET = 50;
        final int KEY_BIND_V_OFFSET = 18;
        final int KEY_BIND_FONT_SIZE = 14;
        final Font KEY_BIND_FONT = new Font(FONT_ADDRESS, KEY_BIND_FONT_SIZE);

        final Point HEADER_LOCATION = new Point((double) getWindowWidth()/2 - KEY_BIND_H_OFFSET, KEY_BIND_V_OFFSET);
        final Point LINE1_LOCATION = new Point((double) getWindowWidth()/2 - KEY_BIND_H_OFFSET, KEY_BIND_V_OFFSET * 3);
        final Point LINE2_LOCATION = new Point((double) getWindowWidth()/2 - KEY_BIND_H_OFFSET, KEY_BIND_V_OFFSET * 4);
        final Point LINE3_LOCATION = new Point((double) getWindowWidth()/2 - KEY_BIND_H_OFFSET, KEY_BIND_V_OFFSET * 5);


        // draw key binds
        KEY_BIND_FONT.drawString(KEY_BIND_HEADER, HEADER_LOCATION.x, HEADER_LOCATION.y);
        KEY_BIND_FONT.drawString(KEY_BIND_LINE1, LINE1_LOCATION.x, LINE1_LOCATION.y);
        KEY_BIND_FONT.drawString(KEY_BIND_LINE2, LINE2_LOCATION.x, LINE2_LOCATION.y);
        KEY_BIND_FONT.drawString(KEY_BIND_LINE3, LINE3_LOCATION.x, LINE3_LOCATION.y);
    }


    /**
     * draw items and their prices
     */
    private void drawItems(){

        /* ********************** constant definition ********************** */
        final int PRICE_FONT_SIZE = 25;
        final Font PRICE_FONT = new Font(FONT_ADDRESS, PRICE_FONT_SIZE);
        final DrawOptions RED = new DrawOptions().setBlendColour(255, 0,0);
        final DrawOptions GREEN = new DrawOptions().setBlendColour(0, 128,0);

        final Point TANK_PRICE_POSITION = TANK_POSITION.asVector()
                .sub(new Vector2(TANK_IMAGE.getWidth()/2, - TANK_IMAGE.getHeight()/2 - ABOVE_PANEL * 2))
                .asPoint();
        final Point SUPER_TANK_PRICE_POSITION = SUPER_TANK_POSITION.asVector()
                .sub(new Vector2(SUPER_TANK_IMAGE.getWidth()/2, - SUPER_TANK_IMAGE.getHeight()/2 - ABOVE_PANEL * 2))
                .asPoint();
        final Point PLANE_PRICE_POSITION = PLANE_POSITION.asVector()
                .sub(new Vector2(PLANE_IMAGE.getWidth()/2, - PLANE_IMAGE.getHeight()/2 - ABOVE_PANEL * 2))
                .asPoint();

        int money = Level.getMoney();

        /* ************************ actual function ************************ */

        // draw tank and its price
        TANK_IMAGE.draw(TANK_POSITION.x, TANK_POSITION.y);
        if (money < TANK_PRICE){
            PRICE_FONT.drawString(MONEY_SYMBOL + TANK_PRICE, TANK_PRICE_POSITION.x, TANK_PRICE_POSITION.y, RED);
        } else {
            PRICE_FONT.drawString(MONEY_SYMBOL + TANK_PRICE, TANK_PRICE_POSITION.x, TANK_PRICE_POSITION.y, GREEN);
        }

        // draw super tank and its price
        SUPER_TANK_IMAGE.draw(SUPER_TANK_POSITION.x, SUPER_TANK_POSITION.y);
        if (money < SUPER_TANK_PRICE){
            PRICE_FONT.drawString(MONEY_SYMBOL + SUPER_TANK_PRICE, SUPER_TANK_PRICE_POSITION.x, SUPER_TANK_PRICE_POSITION.y, RED);
        } else {
            PRICE_FONT.drawString(MONEY_SYMBOL + SUPER_TANK_PRICE, SUPER_TANK_PRICE_POSITION.x, SUPER_TANK_PRICE_POSITION.y, GREEN);
        }

        // draw plane and its price
        PLANE_IMAGE.draw(PLANE_POSITION.x, PLANE_POSITION.y);
        if (money < PLANE_PRICE){
            PRICE_FONT.drawString(MONEY_SYMBOL + PLANE_PRICE, PLANE_PRICE_POSITION.x, PLANE_PRICE_POSITION.y, RED);
        } else {
            PRICE_FONT.drawString(MONEY_SYMBOL + PLANE_PRICE, PLANE_PRICE_POSITION.x, PLANE_PRICE_POSITION.y, GREEN);
        }
    }

    /**
     * check whether the player is buying
     * @return whether the player is buying
     */
    public boolean isBuying(){
        return isBuying;
    }

    public void stopBuying(){
        isBuying = false;
    }
}
