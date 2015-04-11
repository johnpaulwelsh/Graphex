package graphex;

import java.util.Queue;

/**
 * Class to represent a regular expression.
 * Since I am essentially writing my own
 * regex matcher, I need to represent the
 * incoming regex string as a custom object.
 *
 * @author John Paul Welsh
 */
public class Regex {
    String       input;
    Queue<String> dividedInput;

    public Regex(String input) {
        this.input = input;
        divideInput(this.input);
    }

    private void divideInput(String input) {
        // loop through the regex one char at a time
        // have a counter to show how many sets of parens we are inside right now
        //   if ( found, count++
        //   if ) found, count--
        // TODO: how to deal with nested parens?

        int i = 0, parensCount = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            // switch statement inside an if-else?
        }
    }
}
