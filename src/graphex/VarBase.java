package graphex;

public class VarBase implements Variable {
    private char character;
    private VarRegex regex;
    private NFA nfa;

    /**
     * A Base removes the character it's been given no matter what,
     * and if it's a ( it will make
     */
    public VarBase() {
        if (Parser.regex.size() > 0) {
            character = Parser.regex.get(0);
            if (character == '(') {
                regex = new VarRegex();
            }
            Parser.regex.remove(0);
        }
    }

    @Override
    public void makeNFA() {

    }

    @Override
    public NFA getNFA() {
        return null;
    }
}
