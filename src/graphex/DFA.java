package graphex;

import java.util.Set;

/**
 * Class to represent a deterministic finite automaton.
 * It extends from an NFA so it uses almost all the same
 * methods and fields.
 *
 * @author John Paul Welsh
 */
public class DFA extends NFA {

    private Set<State> states;

    public DFA(Set<State>    states,
                Set<String>     alphabet,
                Set<Connection> transitionFunction,
                State           startingState,
                Set<State>      acceptStates) {
        super(states, alphabet, transitionFunction, startingState, acceptStates);
        // Take out the epsilons
        alphabet.remove("epsilon");
        this.setAlphabet(alphabet);
    }

    /**
     * Given a "from" state and a transition character, returns the
     * state resulting from following that transition.
     *
     * @param from  the starting state for this connection
     * @param input the input character symbol for the connection
     * @return      the resulting state
     */
    public State followTransition(State from, String input) {
        Connection c = getTransitionFromStateWithInput(from, input);
        return c.getToState();
    }

    //
    // Specific Getters
    //

    /**
     * Given a "from" state and a transition character, returns the
     * transition function that maps that state, through that character,
     * to another state.
     *
     * @param from  the starting state for this connection
     * @param input the input character symbol for the connection
     * @return      the connection that features these members
     */
    public Connection getTransitionFromStateWithInput(State from, String input) {
        for (Connection c : this.getTransitionFunction()) {
            if (c.getFromState().equals(from) && c.getInput().equals(input)) {
                return c;
            }
        }
        throw new TransitionNotFoundException();
    }
}
