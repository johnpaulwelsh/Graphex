package graphex;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

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

    private static NFA runParser(String regex, String file) {
        Parser p = new Parser(regex);
        p.learnAlphabet(file);
        p.parse();
        p.createNFA();
        return p.getNFA();
    }

    private static void makeGraphViz(NFA nfa, String outputFile)
                        throws FileNotFoundException, UnsupportedEncodingException {

        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());

        gv.addln("rankdir=LR;");

        gv.addln("node [shape = none]; \"\";");

        // Add which states will be accept states
        String acceptStates = "";
        for (State acc : nfa.getAcceptStates()) {
            acceptStates += acc.getName() + ", ";
        }
        acceptStates = acceptStates.substring(0, acceptStates.length() - 2) + ";";
        gv.addln("node [shape = doublecircle]; " + acceptStates);

        gv.addln("node [shape = circle];");

        // Add the start state
        gv.addln("\"\" ->" + nfa.getStartingState().getName() +  ";");

        // Add all the other transitions
        for (Connection c : nfa.getTransitionFunction()) {
            gv.addln(c.toString() + ";");
        }

        gv.addln(gv.end_graph());
        System.out.println(gv.getDotSource());

        GraphViz.WriteGraphSource(gv, outputFile);
    }

    private static void grep(boolean isDFA, String outputFile, String regex, String file) {
        NFA nfa = runParser(regex, file);

        try{
            makeGraphViz(nfa, outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void grep(String nfaFile, String dfaFile, String regex, String file) {
        NFA nfa = runParser(regex, file);
        //NFA dfa = p.getDFA();

        try{
            makeGraphViz(nfa, nfaFile);
            //makeGraphViz(dfa, dfaFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void grep(String regex, String file) {
        runParser(regex, file);
    }

    public static void main(String[] args) {
        if (args[0].equals("-n")) {

            if (args[2].equals("-d")){
                grep(args[1], args[3], args[4], args[5]);
            } else {
                grep(false, args[1], args[2], args[3]);
            }

        } else if (args[0].equals("-d")) {
            grep(true, args[1], args[2], args[3]);
        } else {
            grep(args[0], args[1]);
        }
    }
}
