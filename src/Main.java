import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {

    private static final int pointsAmount = (int) Math.pow(10, 8);
    private static final int threadsAmount = 6;

    private static List<Point> generatePoints() {
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < pointsAmount; i++) {
            Point point = PIHelper.generatePoint();
            points.add(point);
        }

        return points;
    }

    private static void oneThreadCalculations(List<Point> points) {
        long start = System.currentTimeMillis();
        double PI = PIHelper.calculatePI(points);
        long end = System.currentTimeMillis();

        printResult(1, PI, start, end);
    }

    private static void multiThreadCalculations(List<Point> points) {
        long start = System.currentTimeMillis();
        List<Container> containers = new ArrayList<>();

        CountDownLatch event = new CountDownLatch(threadsAmount);
        int threadPointsAmount = pointsAmount / threadsAmount;

        for (int i = 0; i < threadsAmount; i++) {
            Container container = new Container();
            containers.add(container);
            container.setEvent(event);

            int fromIndex = i * threadPointsAmount;
            List<Point> subPoints = points.subList(fromIndex, fromIndex + threadPointsAmount);
            Thread thread = new Thread(() -> PIHelper.run(container, subPoints));
            thread.start();
        }

        try {
            event.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        double PI = containers.stream().map(Container::getPI).reduce(0d, Double::sum) / threadsAmount;

        long end = System.currentTimeMillis();

        printResult(threadsAmount, PI, start, end);
    }

    private static void printResult(int threadAmount, double PI, long start, long end) {
        System.out.println(threadAmount + "-thread calculations:\nPI = " + PI +
                "\nexecution time - " + (end - start) / 1000.0 + "s\n");
    }

    public static void main(String[] args) {
        List<Point> points = generatePoints();
        oneThreadCalculations(points);
        multiThreadCalculations(points);
    }
}