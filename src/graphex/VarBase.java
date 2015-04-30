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
            char next = Parser.regex.get(0);

            if (next == '(') {
                Parser.regex.remove(0);
                regex = new VarRegex();
            } else if (next == ')') {
                Parser.regex.remove(0);
            } else {
                character = next;
                Parser.regex.remove(0); // #butwhatifRubyisbetter‽
            }
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
