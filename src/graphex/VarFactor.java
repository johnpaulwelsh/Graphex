package graphex;

public class VarFactor implements Variable {
    private VarBase base;
    private boolean hasStar;
    private NFA nfa;

    public VarFactor() {
        char c = Parser.regex.get(0);
        base = new VarBase();
    }
}
