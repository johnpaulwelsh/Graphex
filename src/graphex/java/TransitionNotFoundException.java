package graphex.java;

/**
 * A custom throwable exception that occurs when
 * trying to follow a transition that is not present
 * in the NFA or DFA in question.
 *
 * @author John Paul Welsh
 */
public class TransitionNotFoundException extends RuntimeException {
    public TransitionNotFoundException() {
        System.err.println("The requested transition was not found in the automaton.");
    }
}
