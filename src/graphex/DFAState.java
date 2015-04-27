package graphex;

import java.util.Set;

/**
 * A class to represent a state in a DFA, which
 * consists of one or more NFA states, or a null
 * state.
 *
 * @author John Paul Welsh
 */
public class DFAState implements State {
    private Set<NFAState> states;
    private String name;

    public DFAState(String name, Set<NFAState> states) {
        this.name   = name;
        this.states = states;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<NFAState> getStates() {
        return states;
    }

    public void setStates(Set<NFAState> newStates) {
        this.states = newStates;
    }
}
