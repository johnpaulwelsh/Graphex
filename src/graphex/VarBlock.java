package graphex;

public class VarBlock implements Variable {
    private VarFactor factor;
    private VarBlock block;
    private NFA nfa;

    public VarBlock() {
        char c = Parser.regex.get(0);
        factor = new VarFactor();
    }
}
