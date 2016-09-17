package graphex.java;

/**
 * A custom throwable exception that occurs when
 * confronted with an issue in parsing the regex.
 * Means that the regex wasn't properly formed.
 *
 * @author John Paul Welsh
 */
public class BadRegexException extends RuntimeException {
    public BadRegexException() {
        System.err.println("The given regex was not properly formed.");
    }
}
