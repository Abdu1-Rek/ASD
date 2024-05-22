import org.junit.Test;
import static org.junit.Assert.*;

public class FastNeighborsTest {

    @Test
    public void testClosestPairSimpleCase() {
        Vec2[] points = {
                new Vec2(1, 2),
                new Vec2(3, 4),
                new Vec2(5, 6),
                new Vec2(7, 8),
                new Vec2(9, 10)
        };

        FastNeighbors fastNeighbors = new FastNeighbors(points);
        Vec2[] result = fastNeighbors.findClosestPair();

        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue((result[0].compareTo(new Vec2(5, 6)) == 0 && result[1].compareTo(new Vec2(7, 8)) == 0) ||
                (result[0].compareTo(new Vec2(7, 8)) == 0 && result[1].compareTo(new Vec2(5, 6)) == 0));
    }

    @Test
    public void testClosestPairWithNegativeCoordinates() {
        Vec2[] points = {
                new Vec2(-1, -2),
                new Vec2(-3, -4),
                new Vec2(-5, -6),
                new Vec2(-7, -8),
                new Vec2(-9, -10)
        };

        FastNeighbors fastNeighbors = new FastNeighbors(points);
        Vec2[] result = fastNeighbors.findClosestPair();

        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue((result[0].compareTo(new Vec2(-5, -6)) == 0 && result[1].compareTo(new Vec2(-3, -4)) == 0) ||
                (result[0].compareTo(new Vec2(-3, -4)) == 0 && result[1].compareTo(new Vec2(-5, -6)) == 0));
    }

    @Test
    public void testClosestPairWithMixedCoordinates() {
        Vec2[] points = {
                new Vec2(-1, 2),
                new Vec2(0, 0),
                new Vec2(-5, 6),
                new Vec2(7, -8),
                new Vec2(9, 10)
        };

        FastNeighbors fastNeighbors = new FastNeighbors(points);
        Vec2[] result = fastNeighbors.findClosestPair();

        assertNotNull(result);
        assertEquals(2, result.length);
        assertTrue((result[0].compareTo(new Vec2(-1, 2)) == 0 && result[1].compareTo(new Vec2(0, 0)) == 0) ||
                (result[0].compareTo(new Vec2(0, 0)) == 0 && result[1].compareTo(new Vec2(-1, 2)) == 0));
    }
}
