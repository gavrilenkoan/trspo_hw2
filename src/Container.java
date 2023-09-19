import java.util.concurrent.CountDownLatch;

public class Container {

    private double PI;
    private CountDownLatch event;

    public double getPI() {
        return PI;
    }
    public void setPI(double PI) {
        this.PI = PI;
    }

    public CountDownLatch getEvent() {
        return event;
    }
    public void setEvent(CountDownLatch event) {
        this.event = event;
    }
}
