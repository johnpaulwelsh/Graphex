package graphex;

import java.util.Set;

/**
 * Class to represent a deterministic finite automata.
 * It extends from an NFA so it uses almost all the same
 * methods and fields.
 *
 * @author John Paul Welsh
 */
public class DFA extends NFA {

    public DFA(Set<State> states,
                Set<String> alphabet,
                Set<Connection> transitionFunction,
                State startingState,
                Set<State> acceptStates) {
        super(states, alphabet, transitionFunction, startingState, acceptStates);
        // Take out the epsilons
        alphabet.remove("epsilon");
        super.setAlphabet(alphabet);
    }
}
