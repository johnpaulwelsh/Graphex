package graphex;

import java.util.HashSet;
import java.util.Set;

public class VarBase implements Variable {
    private char character;
    private VarRegex regex;
    private NFA nfa;

    /**
     * A Base removes the character it's been given no matter what,
     * and if it's a ( it will make
     */
    public VarBase() {
        if (Parser.regex.size() > 0) {
            char next = Parser.regex.get(0);

            if (next == '(') {
                Parser.regex.remove(0);
                this.regex = new VarRegex();
            } else if (next == ')') {
                Parser.regex.remove(0);
            } else {
                this.character = next;
                Parser.regex.remove(0); // #butwhatifRubyisbetter‽
            }
        }
    }

    @Override
    public void makeNFA() {
        if (regex == null) {
            NFAState s0 = new NFAState(Grep.makeNextStateName());
            NFAState s1 = new NFAState(Grep.makeNextStateName());
            Connection c = new Connection(s0, character, s1);

            Set<State> states = new HashSet<State>();
            states.add(s0);
            states.add(s1);

            Set<Character> alphabet = Parser.alphabet;

            Set<Connection> transitionFunction = new HashSet<Connection>();
            transitionFunction.add(c);

            State start = s0;

            Set<State> accepts = new HashSet<State>();
            accepts.add(s1);

            this.nfa = new NFA(states, alphabet, transitionFunction, start, accepts);
        } else {

        }
    }

    @Override
    public NFA getNFA() {
        return this.nfa;
    }
}
