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
    private Set<State> states;
    private String name;

    public DFAState(String name, Set<State> states) {
        this.name   = name;
        this.states = states;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> newStates) {
        this.states = newStates;
    }
}
