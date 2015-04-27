package graphex;

/**
 * Interface for a state in a finite automaton.
 *
 * @author John Paul Welsh
 */
public interface State {
    /**
     * Returns the name of the state.
     *
     * @return the name of the state
     */
    String getName();

    /**
     * Updates the name of the state.
     *
     * @param newName the new name for the state
     */
    void setName(String newName);
}
