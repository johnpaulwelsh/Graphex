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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<State> getStates() {
        return states;
    }
}
