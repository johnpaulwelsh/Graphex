package graphex.java;

import java.util.HashSet;
import java.util.Set;

public class VarBase implements Variable {
    private char character;
    private VarRegex regex;
    private NFA nfa;

    /**
     * A Base removes the character it's been given no matter what,
     * and if it's a ( it will make a new regex. Base is one or the
     * other, unlike the other productions, so if regex is null then
     * we know we have a character-based NFA.
     */
    public VarBase() {
        if (Parser.regex.size() > 0) {
            char next = Parser.regex.get(0);

            if (next == '(') {
                this.character = Grep.epsilon;
                Parser.regex.remove(0);
                this.regex = new VarRegex();
            } else if (next == ')') {
                this.character = Grep.epsilon;
                Parser.regex.remove(0);
            } else {
                this.character = next;
                Parser.regex.remove(0); // #butwhatifRubyisbetter‽
            }
        }
    }

    @Override
    public void makeNFA() {
        // If the regex member is null, then we have a character literal.
        if (regex == null) {
            NFAState s0 = new NFAState(Grep.makeNextStateName());
            NFAState s1 = new NFAState(Grep.makeNextStateName());
            Connection c = new Connection(s0, character, s1);

            Set<State> states = new HashSet<State>();
            states.add(s0);
            states.add(s1);

            Set<Connection> transitionFunction = new HashSet<Connection>();
            transitionFunction.add(c);

            Set<State> accepts = new HashSet<State>();
            accepts.add(s1);

            this.nfa = new NFA(states, Parser.alphabet, transitionFunction, s0, accepts);

        // Otherwise, we do NOT have a character literal and therefore have
        // a grouping with a regex inside it.
        } else {
            regex.makeNFA();
            this.nfa = regex.getNFA();
        }
    }

    @Override
    public NFA getNFA() {
        return this.nfa;
    }
}
