package graphex;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private List<Character> alphabet;
    private VarRegex regexTree;
    private NFA nfa;

    public Parser(String s) {
        String noDoubleStars = consolidateStars(s);
        regex = new ArrayList<Character>();
        for (char c : noDoubleStars.toCharArray()) {
            regex.add(c);
        }
        alphabet = new ArrayList<Character>();
    }

    public String consolidateStars(String s) {
        while(s.contains("**")) {
            s = s.replace("**", "*");
        }
        return s;
    }

    public void learnAlphabet(String path) { // called from Grep
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
            bis.close();

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void parse() {
        // ... and thus begins recursion hell
        regexTree = new VarRegex();
    }

    public List<Character> getRegex() {
        return regex;
    }

    public List<Character> getAlphabet() {
        return alphabet;
    }

    public NFA getNFA() {
        return regexTree.getNFA();
    }
}
