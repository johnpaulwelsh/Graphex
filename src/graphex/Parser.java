package graphex;

/**
 * Class that can parse an input string
 * into a structured, object-oriented
 * regular expression, which uses productions
 * to represent a context-free grammar.
 *
 * @author John Paul Welsh
 */
public class Parser {
    private String str;

    public Parser(String s) {
        this.str = consolidateStars(s);
        parse(this.str);
    }

    public String consolidateStars(String s) {
        while(s.contains("**")) {
            s = s.replace("**", "*");
        }
        return s;
    }

    public void parse(String s) {
        char curr;
        int i = 0;
        while (i < s.length()) {
            curr = s.charAt(i);

            if (curr == '(') {

            } else if (curr == '*') {

            } else {

            }
        }
    }

    /*
     * Regex  -> Block|Regex
     *        -> Block
     * Block  -> FactorBlock
     *        -> Factor
     * Factor -> Base*
     *        -> Base
     * Base   -> (Regex)
     *        -> ASCII-character
     */
}
