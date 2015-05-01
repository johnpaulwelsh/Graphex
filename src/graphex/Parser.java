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
    private DFA dfa;

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
     * The primary functionality of Parser. Will create a tree-structure
     * of productions out of the regex string, and will tell those
     * productions to create their respective NFAs. Will then retrieve
     * top-level NFA.
     */
    public void parse() {
        // Makes the parse-tree (super recursion)
        regexTree = new VarRegex();
        // Tell the parse-tree to make an NFA of itself (also super-recursion)
        regexTree.makeNFA();
        // Sets the parse-tree's NFA to be our NFA
        nfa = getParsedNFA();

        for (Connection c : nfa.getTransitionFunction()) {
            System.out.println(c);
        }
        for (State s : nfa.getAcceptStates()) {
            System.out.println("Accept: " + s.getName());
        }

        dfa = translateNFAIntoDFA();
    }

    private DFA translateNFAIntoDFA() {

        // The NFA's data
        Set<State> nfaStates     = nfa.getStates();
        Set<Connection> nfaConns = nfa.getTransitionFunction();
        State nfaStartState      = nfa.getStartingState();
        Set<State> nfaAccepts    = nfa.getAcceptStates();

        // The DFA's states represent the power set of the NFA's states
        Set<DFAState> dfaStates = makePowerSetOfNFAStates(nfa.getStates());

        // The DFA's accept states include any DFAStates that have as a member
        // any of the NFA's accept states
        Set<DFAState> dfaAccepts = new HashSet<DFAState>();
        for (DFAState s : dfaStates) {
            for (State x : nfaAccepts) {
                if (s.getStates().contains(x)) {
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


        Set<State>


        return null;
    }


    /**
     * TODO CHANGE THIS CODE SO IT DOESN'T LOOK LIKE I STOLE IT
     *
     * @param nfaStates the set of states for which we want the power set
     * @return          the set of DFAStates that represent the power set
     *                  of the NFAStates
     */
    private Set<DFAState> makePowerSetOfNFAStates(Set<State> nfaStates) {

        State[] stateArray = (State[]) nfaStates.toArray(new State[nfaStates.size()]);
        Set<DFAState> powerSet = new HashSet<DFAState>();

        int len = stateArray.length;
        int elements = (int) Math.pow(2, len);

        for (int i = 0; i < elements; i++) {

            String str = Integer.toBinaryString(i);
            int value = str.length();
            String pset = str;

            for (int k = value; k < len; k++) {
                pset = "0" + pset;
            }

            Set<State> set = new HashSet<State>();

            for (int j = 0; j < pset.length(); j++) {
                if (pset.charAt(j) == '1')
                    set.add(stateArray[j]);
            }

            powerSet.add(new DFAState(Grep.makeNextStateName(), set));
        }

        return powerSet;
    }

    public List<Character> getRegex() {
        return regex;
    }

    public NFA getParsedNFA() {
        return regexTree.getNFA();
    }
}
