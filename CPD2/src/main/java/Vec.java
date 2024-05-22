import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * A class representing a geometric vector with double precision components.
 */
public class Vec {
    protected double[] data;
    private static final String SAME_LEN_MESSAGE = "Vectors must have the same length";

    /**
     * Constructs a vector with the given components.
     * @param components the components of the vector
     */
    public Vec(double... components) {
        this.data = components;
    }

    /**
     * Constructs a vector with the specified number of elements, filled with zeros.
     * @param size the number of elements in the vector
     */
    public Vec(int size) {
        this.data = new double[size];
        this.uniform(0.0);
    }

    /**
     * Constructs a vector with the specified number of elements, filled with a uniform value.
     * @param uniformValue  the value to fill the vector with
     */
    public void uniform(double uniformValue) {
        Arrays.fill(data, uniformValue);
        /*IntStream.range(0, size())
                .parallel()
                .forEach(i -> this.data[i] = uniformValue);*/
    }

    /**
     * Returns the component at the specified index.
     * @param index the index of the component
     * @return the component value
     */
    public double get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return data[index];
    }

    /**
     * Sets the component at the specified index to the given value.
     * @param index the index of the component
     * @param value the new value of the component
     */
    public void set(int index, double value) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        data[index] = value;
    }

    /**
     * Returns the number of components in the vector.
     * @return the number of components
     */
    public int size() {
        return data.length;
    }

    /**
     * Calculates the dot product of this vector and another vector.
     * @param other the other vector
     * @return the dot product of the two vectors
     * @throws IllegalArgumentException if the vectors have different lengths
     */
    public double dot(Vec other) {
        if (this.size() != other.size()) {
            throw new IllegalArgumentException(SAME_LEN_MESSAGE);
        }
        return IntStream.range(0, this.size())
                .parallel()
                .mapToDouble(i -> this.get(i) * other.get(i))
                .sum();
    }

    /**
     * Calculates the cross product of this 3D vector and another 3D vector.
     * @param other the 3D other vector
     * @return the dot product of the two 3D vectors
     * @throws IllegalArgumentException if the vectors have different lengths
     */
    public Vec cross(Vec other) {
        if (this.size() != 3 || other.size() != 3) {
            throw new IllegalArgumentException("Cross product is only defined for 3D vectors");
        }

        double x = this.get(1) * other.get(2) - this.get(2) * other.get(1);
        double y = this.get(2) * other.get(0) - this.get(0) * other.get(2);
        double z = this.get(0) * other.get(1) - this.get(1) * other.get(0);

        return new Vec(x, y, z);
    }

    /**
     * Calculates the Euclidean length (magnitude) of this vector.
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(Arrays.stream(this.data)
                .parallel()
                .map(d -> d * d)
                .sum());
    }

    public Vec add(Vec other) {
        if (this.size() != other.size()) {
            throw new IllegalArgumentException(SAME_LEN_MESSAGE);
        }
        double[] result = new double[this.size()];
        IntStream.range(0, this.size())
                .parallel()
                .forEach(i -> result[i] = this.get(i) + other.get(i));
        return new Vec(result);
    }

    public Vec sub(Vec other) {
        if (this.size() != other.size()) {
            throw new IllegalArgumentException(SAME_LEN_MESSAGE);
        }
        double[] result = new double[this.size()];
        IntStream.range(0, this.size())
                .parallel()
                .forEach(i -> result[i] = this.get(i) - other.get(i));
        return new Vec(result);
    }

    public Vec mul(Vec other) {
        if (this.size() != other.size()) {
            throw new IllegalArgumentException(SAME_LEN_MESSAGE);
        }
        double[] result = new double[this.size()];
        IntStream.range(0, this.size())
                .parallel()
                .forEach(i -> result[i] = this.get(i) * other.get(i));
        return new Vec(result);
    }

    public Vec div(Vec other) {
        if (this.size() != other.size()) {
            throw new IllegalArgumentException(SAME_LEN_MESSAGE);
        }
        double[] result = new double[this.size()];
        IntStream.range(0, this.size())
                .parallel()
                .forEach(i -> result[i] = this.get(i) / other.get(i));
        return new Vec(result);
    }

    public Vec add(double value) {
        double[] result = new double[this.size()];
        IntStream.range(0, this.size())
                .parallel()
                .forEach(i -> result[i] = this.get(i) + value);
        return new Vec(result);
    }

    public Vec sub(double value) {
        double[] result = new double[this.size()];
        IntStream.range(0, this.size())
                .parallel()
                .forEach(i -> result[i] = this.get(i) - value);
        return new Vec(result);
    }

    public Vec mul(double value) {
        double[] result = new double[this.size()];
        IntStream.range(0, this.size())
                .parallel()
                .forEach(i -> result[i] = this.get(i) * value);
        return new Vec(result);
    }

    public Vec div(double value) {
        double[] result = new double[this.size()];
        IntStream.range(0, this.size())
                .parallel()
                .forEach(i -> result[i] = this.get(i) / value);
        return new Vec(result);
    }

    /**
     * Expands the vector by adding a value at the beginning.
     * @param value the value to be added at the beginning of the vector
     */
    public void expandStart(double value) {
        double[] newData = new double[data.length + 1];
        newData[0] = value;
        System.arraycopy(data, 0, newData, 1, data.length);
        data = newData;
    }

    /**
     * Expands the vector by adding a value at the end.
     * @param value the value to be added at the end of the vector
     */
    public void expandEnd(double value) {
        double[] newData = Arrays.copyOf(data, data.length + 1);
        newData[data.length] = value;
        data = newData;
    }

    /**
     * Shrinks the vector by removing the first value.
     * @throws IllegalStateException if the vector has only one element
     */
    public void shrinkStart() {
        if (data.length <= 1) {
            throw new IllegalStateException("Vector cannot be further shrunk");
        }
        double[] newData = new double[data.length - 1];
        System.arraycopy(data, 1, newData, 0, data.length - 1);
        data = newData;
    }

    /**
     * Shrinks the vector by removing the last value.
     * @throws IllegalStateException if the vector has only one element
     */
    public void shrinkEnd() {
        if (data.length <= 1) {
            throw new IllegalStateException("Vector cannot be further shrunk");
        }
        data = Arrays.copyOf(data, data.length - 1);
    }

    /**
     * Returns a string representation of the vector.
     * @return a string representation of the vector
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]);
            if (i < data.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}