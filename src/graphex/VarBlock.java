package graphex;

import java.util.HashSet;
import java.util.Set;

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
        factor.makeNFA();
        NFA factNFA  = factor.getNFA();

        // If we have a Block, we need to perform concatenation between
        // the Factor and the Block
        if (block != null) {
            NFA blockNFA = block.getNFA();

            // The Factor's data
            Set<State> factStates     = factNFA.getStates();
            Set<Connection> factConns = factNFA.getTransitionFunction();
            State factStart           = factNFA.getStartingState();
            Set<State> factAccepts    = factNFA.getAcceptStates();

            // The Block's data
            Set<State> blockStates     = blockNFA.getStates();
            Set<Connection> blockConns = blockNFA.getTransitionFunction();
            State blockStart           = blockNFA.getStartingState();
            Set<State> blockAccepts    = blockNFA.getAcceptStates();

            // The new set of states consist of all the old ones from
            // both pieces
            Set<State> newStates = new HashSet<State>();
            newStates.addAll(factStates);
            newStates.addAll(blockStates);

            // The new transition function has all the old ones from both
            // pieces, as well as any new ones (below)
            Set<Connection> newConns = new HashSet<Connection>();
            newConns.addAll(factConns);
            newConns.addAll(blockConns);

            // Make an epsilon transition from each of the Factor's
            // accept states to the Block's start state
            for (State acc : factAccepts) {
                Connection c = new Connection(acc, Grep.epsilon, blockStart);
                newConns.add(c);
            }

            // The new start state is the Factor's start state
            State newStart = factStart;

            // The new accept states include all of the Block's accepts, but
            // excludes all the Factor's accepts
            Set<State> newAccepts = new HashSet<State>();
            newAccepts.addAll(blockAccepts);

            this.nfa = new NFA(newStates, Parser.alphabet, newConns, newStart, newAccepts);

        // Otherwise, we don't have a block, so our NFA is just the factor's NFA
        } else {
            nfa = factNFA;
        }
    }

    @Override
    public NFA getNFA() {
        return this.nfa;
    }
}
