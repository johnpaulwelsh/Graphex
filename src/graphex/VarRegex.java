package graphex;

/**
 * Class to represent a 'regex' variable in the CFG
 * used to parse the input regex-string.
 *
 * @author John Paul Welsh
 */
public class VarRegex implements Variable {
    private String str;

    public VarRegex(String s) {
        this.str = s;
    }

    @Override
    public String getString() {
        return str;
    }
}
