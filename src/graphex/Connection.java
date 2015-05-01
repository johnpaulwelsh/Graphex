package graphex;

/**
 * Class to represent a connection between two states in
 * a finite automaton, and the input character that it takes
 * to get from the first state to the second.
 *
 * @author John Paul Welsh
 */
public class Connection {
    private State fromState;
    private char  input;
    private State toState;

    public Connection(State fromState, char input, State toState) {
        this.fromState = fromState;
        this.input     = input;
        this.toState   = toState;
    }

    public State getFromState() {
        return fromState;
    }

    public void setFromState(State fromState) {
        this.fromState = fromState;
    }

    public char getInput() {
        return input;
    }

    public void setInput(char input) {
        this.input = input;
    }

    public State getToState() {
        return toState;
    }

    public void setToState(State toState) {
        this.toState = toState;
    }

    public String toString() {
        return fromState.getName() + " -> " + toState.getName() + "[ label=\"" + input + "\"]";
    }
}
