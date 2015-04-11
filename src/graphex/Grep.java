package graphex;


public class Grep {

    public static void regexToNfa() {

    }

    public static void nfaToDfa() {

    }

    public static boolean solveWithDfa(String input) {
        return true;
    }

    public static String applyAutomataToInput(String input) {
        boolean accept = solveWithDfa(input);
        return accept ? input : "";
    }

    public static void main(String[] args) {
	    Utils.println("dingo");

        // args
        // 0 -> "-n"       | "-d"       | "REGEX"
        // 1 -> "NFA-FILE" | "DFA-FILE" | "FILE"
        // 2 -> REGEX
        // 3 -> FILE
    }
}
