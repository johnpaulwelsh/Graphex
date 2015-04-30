package graphex;

import java.util.HashSet;
import java.util.Set;

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
        base.makeNFA();
        NFA baseNFA = base.getNFA();

        // If we had a star in this production, we need to apply the star
        // operation to the Base's NFA
        if (this.hasStar) {
            Set<State> oldStates     = baseNFA.getStates();
            Set<Connection> oldConns = baseNFA.getTransitionFunction();
            State oldStart           = baseNFA.getStartingState();
            Set<State> oldAccepts    = baseNFA.getAcceptStates();

            // Make a new start state
            State newStart = new NFAState(Grep.makeNextStateName());

            // The new states include the old ones, and the new start state
            Set<State> newStates = new HashSet<State>();
            newStates.addAll(oldStates);
            newStates.add(newStart);

            // The new accept states include the old ones, and the new start state
            Set<State> newAccepts = new HashSet<State>();
            newAccepts.addAll(oldAccepts);
            newAccepts.add(newStart);

            // The new transition function consists of the old connections, as well
            // as any new transitions (below)
            Set<Connection> newConns = new HashSet<Connection>();
            newConns.addAll(oldConns);

            // Make an epsilon transition from the new start state to the old one
            Connection newToOldStart = new Connection(newStart, Grep.epsilon, oldStart);
            newConns.add(newToOldStart);

            // Make an epsilon transition from each old accept state to the old start state
            for (State acc : oldAccepts) {
                Connection oldAcceptToOldStart = new Connection(acc, Grep.epsilon, oldStart);
                newConns.add(oldAcceptToOldStart);
            }

            this.nfa = new NFA(newStates, Parser.alphabet, newConns, newStart, newAccepts);

        // Otherwise, there was no star and we just make the NFA the base's NFA
        } else {
            this.nfa = baseNFA;
        }
    }

    @Override
    public NFA getNFA() {
        return this.nfa;
    }
}
