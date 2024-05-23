import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {
    /**
     * Writes a 2D array into a CSV file, optionally including a header
     * @param tableData  A table as a 2D array
     * @param fileName   Path and name of the CSV file
     * @param delimiter  A string or character delimiting the column values
     * @param header     An 1D array of the column names
     */
    public static void writeFile(
            double[][] tableData, String fileName,
            String delimiter, String[] header) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {


            // Write the table data
            for (double[] row : tableData) {
                writer.write(composeLine(row, delimiter));
                writer.newLine();
            }
        }
    }

    /**
     * Creates a string containing the elements of a 1D array separated by a delimite
     * @param array     1D array
     * @param delimiter Delimiting string
     * @return A string of elements
     */
    private static String composeLine(double[] array, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}
