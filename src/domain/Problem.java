package domain;

/*********************************************************************************
 * Class name: Problem
 * Description: This class implements an internal representation of a search problem
 * @author Pablo Moraga Navas, Javier Ruiz Mora and Jose Ramon Moratalla Munoz 
 * Subject: Intelligent Systems
 * Group: A4
 ********************************************************************************/
public class Problem {
	StateSpace stateSpace; //stateSpace of the problem
    State initialState; //initial State of the problem

    /*****************************************************************************
	 * Method name: Problem
	 * Description: Create a problem with the information passed by parameters
	 * @param stateSpace -> State Space of the problem
	 * @param initialState -> Initial State of the problem
	 ****************************************************************************/
    public Problem(StateSpace statespaces, State first) {
        this.stateSpace = statespaces;
        this.initialState = first;
    }
    
    /*****************************************************************************
	 * Method name: goalState
	 * Description: Checks if a state is the goal state or not
	 * @param State -> State we want to check if is the goal state or not
	 * @return isGoal -> Boolean value depending on if the state passed by parameters
	 * is the goal state or not
	 ****************************************************************************/
    public boolean goalState(State State ) {
        return State.isGoal();
    }
    
    /*****************************************************************************
	 * Method name: heuristic
	 * Description: Returns the heuristic of a state
	 * @param State -> A state we want to calculate the heuristic
	 * @return h -> Heuristic of the state
	 ****************************************************************************/
    public int heuristic(State State) {
        return State.getHeuristic();
    }
    
    /*********************************************************************************
	 * Method name: getStateSpace
	 * Description: Returns the attribute 'stateSpace'
	 * @return stateSpace -> State Space of the problem
	 ********************************************************************************/
    public StateSpace getStateSpace() {
        return stateSpace;
    }

    /*********************************************************************************
	 * Method name: getInitialState
	 * Description: Returns the attribute 'initialState'
	 * @return initialState -> Initial State of the problem
	 ********************************************************************************/
    public State getInitialState() {
        return initialState;
    }

    /*********************************************************************************
	 * Method name: toString
	 * Description: Returns a string with the information of the problem
	 * @return cad -> String with the information of the problem
	 ********************************************************************************/
    @Override
    public String toString() {
        return "Problem{" + "statespaces=" + stateSpace + ", first=" + initialState + '}';
    }
}