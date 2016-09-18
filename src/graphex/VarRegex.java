package graphex;

import java.util.HashSet;
import java.util.Set;

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
                Parser.regex.remove(0);
                regex = new VarRegex();
            }
        }
    }

    @Override
    public void makeNFA() {
        block.makeNFA();
        NFA blockNFA = block.getNFA();

        // If we have a Regex, then we need to perform union
        // on the Block and Regex
        if (regex != null) {
            regex.makeNFA();
            NFA regexNFA = regex.getNFA();

            // The Block's data
            Set<State> blockStates     = blockNFA.getStates();
            Set<Connection> blockConns = blockNFA.getTransitionFunction();
            State blockStart           = blockNFA.getStartingState();
            Set<State> blockAccepts    = blockNFA.getAcceptStates();

            // The Regex's data
            Set<State> regexStates     = regexNFA.getStates();
            Set<Connection> regexConns = regexNFA.getTransitionFunction();
            State regexStart           = regexNFA.getStartingState();
            Set<State> regexAccepts    = regexNFA.getAcceptStates();

            // Make a new start state
            State newStart = new NFAState(Grep.makeNextStateName());

            // The new set of states consist of all the states from the
            // Block and the Regex, as well as the new start state
            Set<State> newStates = new HashSet<State>();
            newStates.addAll(blockStates);
            newStates.addAll(regexStates);
            newStates.add(newStart);

            // The new transition function consists of all connections from
            // the Block and Regex, plus any new ones (below)
            Set<Connection> newConns = new HashSet<Connection>();
            newConns.addAll(blockConns);
            newConns.addAll(regexConns);

            // Make an epsilon transition from the new start to each of the old starts
            Connection c1 = new Connection(newStart, Grep.epsilon, blockStart);
            Connection c2 = new Connection(newStart, Grep.epsilon, regexStart);
            newConns.add(c1);
            newConns.add(c2);

            // The new accept states are simply all the old accept states
            Set<State> newAccepts = new HashSet<State>();
            newAccepts.addAll(blockAccepts);
            newAccepts.addAll(regexAccepts);

            this.nfa = new NFA(newStates, Parser.alphabet, newConns, newStart, newAccepts);

        // Otherwise, we don't have a union so the NFA is just the Block's NFA
        } else {
            this.nfa = blockNFA;
        }
    }

    @Override
    public NFA getNFA() {
        return nfa;
    }
}
