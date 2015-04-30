package graphex;

public class VarFactor implements Variable {
    private VarBase base;
    private boolean hasStar;
    private NFA nfa;

    /**
     * A Factor will be looking for * as the next character
     * and remove it.
     */
    public VarFactor() {
        base = new VarBase();
        if (Parser.regex.size() > 0) {
            char c = Parser.regex.get(0);
            if (c == '*') {
                hasStar = true;
                Parser.regex.remove(0);
            } else {
                hasStar = false;
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
