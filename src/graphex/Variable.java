package graphex;

/**
 * An interface to give each production variable in the parsing
 * some common utility functionality.
 */
public interface Variable {

    /**
     * Tells the production to create an NFA out of itself,
     * possibly by using its members' NFAs as starting points.
     */
    void makeNFA();

    /**
     * Returns the NFA from a given production.
     *
     * @return the NFA
     */
    NFA getNFA();
}
