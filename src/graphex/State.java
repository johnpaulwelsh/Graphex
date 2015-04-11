package graphex;

/**
 * A class to represent a state in a finite automaton.
 *
 * @author John Paul Welsh
 */
public class State {
    private String name;

    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
