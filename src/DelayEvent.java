public class DelayEvent extends Event {

    public DelayEvent(int delayMSecond) {
        super(delayMSecond);
    }

    public void update(){
        super.update();
        if (getFrameCount() >= delay){
            finish();
        }
    }
}
