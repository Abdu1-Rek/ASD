import java.util.Comparator;

/**
 * A class representing a 2D geometric vector with double precision components.
 */
public class Vec2 extends Vec implements Comparable<Vec2> {

    /**
     * Compares by polar angle w.r.t this Vector2
     */
    private class Vec2PolarAngleComparator implements Comparator<Vec2> {
        public int compare(Vec2 v1, Vec2 v2) {
            return -Vec2.isTurnedCW(Vec2.this, v1, v2);
        }
    }

    public Vec2(double x, double y) {
        super(x, y);
    }

    public Vec2(Vec v) {
        super(v.get(0), v.get(1));
    }

    public double getX() {
        return data[0];
    }

    public double getY() {
        return data[1];
    }

    public void setX(double value) {
        data[0] = value;
    }

    public void setY(double value) {
        data[1] = value;
    }

    public int compareTo(Vec2 other) {
        // compare by y
        int yComparison = Double.compare(this.getY(), other.getY());
        if (yComparison != 0) {
            return yComparison;
        }
        // if y values are equal, compare by x
        return Double.compare(this.getX(), other.getX());
    }

    /**
     * Checks what kind of turn the sequence of points a-b-c makes.
     * @param a first 2D point
     * @param b second 2D point
     * @param c third 2D point
     * @return (1): CW, (-1): CCW, (0): collinear
     */
    public static int isTurnedCW(Vec2 a, Vec2 b, Vec2 c) {
        // convert 2D to 3D
        Vec va = new Vec(a.getX(), a.getY(), 0.0);
        Vec vb = new Vec(b.getX(), b.getY(), 0.0);
        Vec vc = new Vec(c.getX(), c.getY(), 0.0);

        // compute the cross product and analyze its z-component
        Vec ba = va.sub(vb);
        Vec bc = vc.sub(vb);
        double cross = ba.cross(bc).get(2); // z-component f the cross product

        return (Math.abs(cross) < Utils.EPS)
                ? 0 // collinear
                : ((cross > 0) ? 1 : -1); // CW > 0, CCW < 0
    }

    /**
     * A comparator
     * that compares two Vector2's by polar angle w.r.t this Vector2.
     * @return the comparator
     */
    public Comparator<Vec2> byPolarAngle() {
        return new Vec2PolarAngleComparator();
    }
}
