package automata;

import java.util.Set;

/**
 * Class to represent a nondeterministic finite automata.
 *
 * @author John Paul Welsh
 */
public class NFA {
    private Set<State> states;
    private Set<String> alphabet;
    private Set<Connection> transitionFunction;
    private State startingState;
    private Set<State> acceptStates;

    public NFA(Set<State> states,
               Set<String> alphabet,
               Set<Connection> transitionFunction,
               State startingState,
               Set<State> acceptStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.alphabet.add("epsilon");
        this.transitionFunction = transitionFunction;
        this.startingState = startingState;
        this.acceptStates = acceptStates;
    }


    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<String> alphabet) {
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
