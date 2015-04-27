package graphex;

/**
 * Interface to represent a variable in a context-free grammar.
 * Used in parsing the input regular-expression string.
 *
 * @author John Paul Welsh
 */
public interface Variable {
    /**
     * Method to return the string that the variable uses.
     *
     * @return the string contained in the variable
     */
    String getString();
}
