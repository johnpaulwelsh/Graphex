package graphex;

import java.util.Set;

/**
 * Entry point for the program, fool.
 *
 * @author John Paul Welsh
 */
public class Grep {

    public static int stateName = 0;
    public static char epsilon = 'ɛ';

    public static String makeNextStateName() {
        return "s" + stateName++;
    }

    public static Set<State> makePowerSetOfNFAStates(Set<State> nfaStates) {
        return null;
    }

    private static void withNfaFile(String[] args) {

    }

    private static void withDfaFile(String[] args) {

    }

    private static void withBothFiles(String[] args) {

    }

    private static void withNoFiles(String[] args) {
        Parser p = new Parser(args[0]);
        p.learnAlphabet(args[1]);
        p.parse();
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
