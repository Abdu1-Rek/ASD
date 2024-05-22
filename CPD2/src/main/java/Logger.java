import java.io.IOException;

/**
 * A helper class to write information to the system console
 */
public class Logger {

    public static final String DELIMITER = " ";

    /**
     * Write all arguments sequentially in a single line
     * @param args A sequence of arguments
     */
    public static void write(Object... args) {
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[i]);
            if (i < args.length - 1) {
                System.out.print(DELIMITER);
            }
        }
    }

    /**
     * Write all arguments starting from a new line
     * @param args A sequence of arguments
     */
    public static void writeLine(Object... args) {
        for (Object arg : args) {
            System.out.println(arg);
        }
    }

    /**
     * Prevent the console window from auto-close
     */
    public static void ReadKey() {
        Logger.writeLine("Press any key to exit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}