package graphex;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Class that can parse an input string
 * into a structured, object-oriented
 * regular expression, which uses productions
 * to represent a context-free grammar.
 *
 * @author John Paul Welsh
 */
public class Parser {
    public static List<Character> regex;
    public static Set<Character> alphabet;
    private VarRegex regexTree;
    private NFA nfa;
    private NFA dfa;
    // A global null state for the DFA, so we don't waste states for every
    // transition that ends up needing the null state.
    public static State nullState;

    public Parser(String s) {
        System.out.println(s);
        String noDoubleStars = consolidateStars(s);
        regex = new ArrayList<Character>();
        for (char c : noDoubleStars.toCharArray()) {
            regex.add(c);
        }
        alphabet = new HashSet<Character>();
    }

    /**
     * Folds all doubled-up stars in the regex string into one star.
     *
     * @param s the regex string
     * @return  the altered string, with only single stars
     */
    public String consolidateStars(String s) {
        while(s.contains("**")) {
            s = s.replace("**", "*");
        }
        return s;
    }

    /**
     * Reads through input file one character at a time, adding
     * each new character to a Set of characters. Removes newlines
     * because they don't count. Called in Grep.
     *
     * @param path the file path to the input file
     */
    public void learnAlphabet(String path) {
        try {
            int ch;
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(path));
            while ((ch = bis.read()) >= 0) {
                char chch = (char) ch;
                if (!alphabet.contains(chch)) {
                    alphabet.add(chch);
                }
            }
            alphabet.remove('\n');
            bis.close();

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * Will create a tree-structure of productions out of the regex string.
     */
    public void parse() {
        regexTree = new VarRegex();
    }

    /**
     * Will tell the productions to create their respective NFAs, and then
     * retrieve the top-level NFA.
     */
    public void createNFA() {
        regexTree.makeNFA();
        nfa = getParsedNFA();
    }

    public NFA getParsedNFA() {
        return regexTree.getNFA();
    }

    /**
     * Will translate the NFA into a DFA.
     */
    public void createDFA() {
        dfa = translateNFAIntoDFA();
    }

    private DFA translateNFAIntoDFA() {

        // The NFA's data
        Set<State> nfaStates     = nfa.getStates();
        Set<Connection> nfaConns = nfa.getTransitionFunction();
        State nfaStartState      = nfa.getStartingState();
        Set<State> nfaAccepts    = nfa.getAcceptStates();

        // The DFA's states represent the power set of the NFA's states
        Set<State> dfaStates = makePowerSetOfNFAStates(nfa.getStates());

        // Find and hold onto the null state so we don't waste time and space
        for (State s : dfaStates) {
            if (s.getStates().isEmpty()) {
                nullState = s;
            }
        }

        // The DFA's accept states include any DFAStates that have as a member
        // any of the NFA's accept states
        Set<State> dfaAccepts = new HashSet<State>();
        for (State s : dfaStates) {
            for (State acc : nfaAccepts) {
                if (s.getStates().contains(acc)) {
                    dfaAccepts.add(s);
                }
            }
        }

        // The DFA's connections are complicated. For each state,
        // follow the connection to the second state, and check for any
        // epsilon transitions from those to any other states. All of those
        // final states will be in one DFAState: find it and use it as the end
        // connection.
        Set<Connection> dfaConns = new HashSet<Connection>();

        for (State powerSetState : dfaStates) {
            for (char c : Parser.alphabet) {

                State toState = makeDFAState(powerSetState.getStates(), nfaConns);

                Connection newConn;
                if (toState.getName().equals("null")) {
                    newConn = new Connection(powerSetState, c, Parser.nullState);
                } else {
                    newConn = new Connection(powerSetState, c, toState);
                }

                dfaConns.add(newConn);
            }
        }

        // The DFA's start state is the DFAState that gets gotten to by following
        // all epsilon transitions from the NFA's start state
        Set<State> singleton = new HashSet<State>();
        singleton.add(nfaStartState);
        State dfaStart = makeDFAState(singleton, nfaConns);

        return new DFA(dfaStates, Parser.alphabet, dfaConns, dfaStart, dfaAccepts);
    }


    /**
     * Given a set of states, will generate a set of DFAs representing the power
     * set of the original set of states.
     *
     * @param nfaStates the set of states for which we want the power set
     * @return          the set of DFAStates that represent the power set
     *                  of the NFAStates
     */
    private Set<State> makePowerSetOfNFAStates(Set<State> nfaStates) {

        State[] stateArray = nfaStates.toArray(new State[nfaStates.size()]);
        Set<State> powerSet = new HashSet<State>();

        int len = stateArray.length;
        int numElements = (int) Math.pow(2, len);

        for (int i = 0; i < numElements; i++) {

            String str = Integer.toBinaryString(i);
            int value = str.length();
            String pwrSet = str;

            for (int k = value; k < len; k++) {
                pwrSet = "0" + pwrSet;
            }

            Set<State> set = new HashSet<State>();

            for (int j = 0; j < pwrSet.length(); j++) {
                if (pwrSet.charAt(j) == '1') {
                    set.add(stateArray[j]);
                }
            }

            powerSet.add(new DFAState(Grep.makeNextStateName(), set));
        }

        return powerSet;
    }

    /**
     * Given a set of fromStates and the transition function that might
     * involve those states, return a DFAState consisting of all the
     * states each of them can get to through either character transitions
     * or epsilon transitions.
     *
     * @param fromStates   the states in question
     * @param transitions the transition function from the automaton that this
     *                    state belongs to
     * @return            a DFAState
     */
    private State makeDFAState(Set<State> fromStates, Set<Connection> transitions) {
        Set<State> results = new HashSet<State>();

        // Finds all toStates by following all transitions where
        // any of the fromStates is the parameter
        for (State s : fromStates) {
            for (Connection c : transitions) {
                if (c.getFromState().equals(s)) {
                    State toState = c.getToState();
                    results.add(toState);
                }
            }
        }

        // For all of those result states, follow any epsilon transitions
        // that they have and add those toStates to the results too.
        // This updates the set as it goes so it should be exhaustive.
        for (State resultState : results) {
            for (Connection c : transitions) {
                if (c.getFromState().equals(resultState) && c.getInput() == Grep.epsilon) {
                    State toStateAfterEpsilon = c.getToState();
                    results.add(toStateAfterEpsilon);
                }
            }
        }

        if (results.size() > 0) {
            return new DFAState(Grep.makeNextStateName(), results);
        } else {
            return Parser.nullState;
        }

    }

    /**
     * Given a line of text from the input file, returns whether the line
     * matches the regular expression (determined by following the DFA).
     * @param line the line of text in question
     * @return     true if the regex matches on the line, false otherwise
     */
    public boolean dfaMatchesInput(String line) {
        return false;
    }

    public NFA getNFA() {
        return this.nfa;
    }

    public NFA getDFA() {
        return this.dfa;
    }
}
