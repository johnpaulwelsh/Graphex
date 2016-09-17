package graphex.java;

import java.util.Set;

/**
 * A class to represent a state in an NFA.
 *
 * @author John Paul Welsh
 */
public class NFAState implements State {
    private String name;

    public NFAState(String name) {
        this.name = name;
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
        return null;
    }
}
