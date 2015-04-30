package graphex;

public class VarBlock implements Variable {
    private VarFactor factor;
    private VarBlock block;
    private NFA nfa;

    /**
     * A Block won't remove anything, but will check that the next character
     * isn't a | or a * before going on to make a second Block.
     */
    public VarBlock() {
        factor = new VarFactor();
        if (Parser.regex.size() > 0) {
            char c = Parser.regex.get(0);
            if (c != '*' && c != '|') {
                block = new VarBlock();
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
