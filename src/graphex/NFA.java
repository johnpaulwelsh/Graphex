package graphex;

import java.util.Set;

/**
 * Class to represent a nondeterministic finite automaton.
 *
 * @author John Paul Welsh
 */
public class NFA {
    private Set<State>      states;
    private Set<Character>  alphabet;
    private Set<Connection> transitionFunction;
    private State           startingState;
    private Set<State>      acceptStates;

    public NFA(Set<State>      states,
               Set<Character>  alphabet,
               Set<Connection> transitionFunction,
               State           startingState,
               Set<State>      acceptStates) {
        this.states   = states;
        this.alphabet = alphabet;
        this.alphabet.add(Grep.epsilon);
        this.transitionFunction = transitionFunction;
        this.startingState      = startingState;
        this.acceptStates       = acceptStates;
    }

    //
    // Specific Boolean Getters
    //

    public boolean hasInAcceptStates(State s) {
        return states.contains(s);
    }

    public boolean hasAsStartState(State s) {
        return startingState.equals(s);
    }

    public boolean hasInAlphabet(char a) {
        return alphabet.contains(a);
    }


    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<Character> alphabet) {
        this.alphabet = alphabet;
    }

    public Set<Connection> getTransitionFunction() {
        return transitionFunction;
    }

    public void setTransitionFunction(Set<Connection> transitionFunction) {
        this.transitionFunction = transitionFunction;
    }

    public State getStartingState() {
        return startingState;
    }

    public void setStartingState(State startingState) {
        this.startingState = startingState;
    }

    public Set<State> getAcceptStates() {
        return acceptStates;
    }

    public void setAcceptStates(Set<State> acceptStates) {
        this.acceptStates = acceptStates;
    }
}
