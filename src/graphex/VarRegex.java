package graphex;

public class VarRegex implements Variable {
    private VarBlock block;
    private VarRegex regex;
    private NFA nfa;

    /**
     * A Regex will be looking out for | as the next character
     * and remove it as it's making the second Regex.
     */
    public VarRegex() {
        block = new VarBlock();
        if (Parser.regex.size() > 0) {
            char c = Parser.regex.get(0);
            if (c == '|') {
                regex = new VarRegex();
                Parser.regex.remove(0);
            }
        }

    }

    @Override
    public void makeNFA() {
//        nfa = combineBlockAndRegexNFA();
    }

    @Override
    public NFA getNFA() {
        return nfa;
    }

//    private NFA combineBlockAndRegexNFA() {
//
//    }
}
