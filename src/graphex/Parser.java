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
    }

    public String consolidateStars(String s) {
        while(s.contains("**")) {
            s = s.replace("**", "*");
        }
        return s;
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
