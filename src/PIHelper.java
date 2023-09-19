import java.util.List;
import java.util.Random;

public class PIHelper {

    private static final Random random = new Random();

    public static Point generatePoint() {
        return new Point(random.nextDouble(), random.nextDouble());
    }

    public static boolean isInternalPoint(Point point) {
        return Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY()) <= 1;
    }

    public static double calculatePI(List<Point> points) {
        int internalPointsAmount = (int) points.stream().filter(PIHelper::isInternalPoint).count();
        return internalPointsAmount / (double) points.size() * 4.0;
    }

    public static void run(Container container, List<Point> points) {
        double PI = calculatePI(points);
        container.setPI(PI);
        container.getEvent().countDown();
    }
}
