import java.awt.geom.Arc2D;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class FastNeighbors {
    private Vec2[] points;
    private Vec2[] closestPair;

    public FastNeighbors(Vec2[] points) {
        this.points = points;
        this.closestPair = new Vec2[2];
    }

    private double distance(Vec2 p1, Vec2 p2) {
        return Math.sqrt((p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY()));
    }

    private double bruteForce(Vec2[] points) {
        double minDist = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = distance(points[i], points[j]);
                if (dist < minDist) {
                    minDist = dist;
                    closestPair[0] = points[i];
                    closestPair[1] = points[j];
                }
            }
        }
        return minDist;
    }

    private double closestPairRecursive(Vec2[] points) {
        int n = points.length;
        if (n <= 3) {
            return bruteForce(points);
        }

        int mid = n / 2;
        Vec2 midPoint = points[mid];

        Vec2[] leftHalf = Arrays.copyOfRange(points, 0, mid);
        Vec2[] rightHalf = Arrays.copyOfRange(points, mid, n);

        double minDistLeft = closestPairRecursive(leftHalf);
        double minDistRight = closestPairRecursive(rightHalf);

        double minDist = Math.min(minDistLeft, minDistRight);

        Vec2[] strip = new Vec2[n];
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (Math.abs(points[i].getX() - midPoint.getX()) < minDist) {
                strip[j] = points[i];
                j++;
            }
        }

        Arrays.sort(strip, 0, j, Comparator.comparingDouble(p -> p.getY()));

        for (int i = 0; i < j; ++i) {
            for (int k = i + 1; k < j && (strip[k].getY() - strip[i].getY()) < minDist; ++k) {
                double dist = distance(strip[i], strip[k]);
                if (dist < minDist) {
                    minDist = dist;
                    closestPair[0] = strip[i];
                    closestPair[1] = strip[k];
                }
            }
        }

        return minDist;
    }

    public Vec2[] findClosestPair() {
        Vec2[] pointsSortedByX = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSortedByX, Comparator.comparingDouble(p -> p.getX()));
        closestPairRecursive(pointsSortedByX);
        return closestPair;
    }

    public static void main(String[] args) throws IOException {
        Vec2[] points = {
                new Vec2(10, 2),
                new Vec2(3, 40),
                new Vec2(1, 2),
                new Vec2(70, 8),
                new Vec2(9, 10)
        };

        int np = points.length;
        double[][] table = new double [points.length+2][2];
        for (int i = 0; i < points.length; i++) {
            table[i][0] = points[i].getX();
            table[i][1] = points[i].getY();
        }



        FastNeighbors fastNeighbors = new FastNeighbors(points);
        Vec2[] result = fastNeighbors.findClosestPair();
        System.out.println("Closest pair: " + result[0] + " and " + result[1]);
        System.out.println("Distance: " + fastNeighbors.distance(result[0], result[1]));

        table[np][0] = result[0].getX();
        table[np][1] = result[0].getY();
        table[np+1][0] = result[1].getX();
        table[np+1][1] = result[1].getY();

        System.out.println();
        CSVExporter.writeFile(table, "./data.csv",";" ,new String[]{"x", "y"});
    }
    }

