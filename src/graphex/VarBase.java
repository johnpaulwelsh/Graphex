package graphex;

public class VarBase implements Variable {
    private char character;
    private boolean hasParens;
    private VarRegex regex;
    private NFA nfa;

    public VarBase() {
        char c = Parser.regex.get(0);

        if (c == '(') {
            regex = new VarRegex();
        }
        Parser.regex.remove(0);
    }

    public void makeNFA() {

    }
}
