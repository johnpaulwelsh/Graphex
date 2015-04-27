package graphex;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
