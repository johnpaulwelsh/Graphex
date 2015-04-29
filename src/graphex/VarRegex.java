package graphex;

public class VarRegex implements Variable {
    private VarBlock block;
    private boolean hasPipe;
    private VarRegex regex;
    private NFA nfa;

    public VarRegex() {
        char c = Parser.regex.get(0);
        block = new VarBlock();
    }

    public void makeNFA() {
        nfa = combineBlockAndRegexNFA();
    }

    public NFA getNFA() {
        return nfa;
    }

    private NFA combineBlockAndRegexNFA() {

    }
}
