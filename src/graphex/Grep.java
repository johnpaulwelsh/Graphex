package graphex;

/**
 * Entry point for the program, fool.
 *
 * @author John Paul Welsh
 */
public class Grep {

    private static void withNfaFile(String[] args) {

    }

    private static void withDfaFile(String[] args) {

    }

    private static void withBothFiles(String[] args) {

    }

    private static void withNoFiles(String[] args) {
        Parser p = new Parser(args[0]);
    }

    public static void main(String[] args) {
        if (args[0].equals("-n")) {
            if (args[2].equals("-d"))
                withBothFiles(args);
            else
                withNfaFile(args);
        } else if (args[0].equals("-d")) {
            withDfaFile(args);
        } else {
            withNoFiles(args);
        }
    }
}
