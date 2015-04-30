package graphex;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    }

    public List<Character> getRegex() {
        return regex;
    }

    public NFA getParsedNFA() {
        return regexTree.getNFA();
    }
}
