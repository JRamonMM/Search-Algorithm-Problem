package domain;

/*********************************************************************************
 * Class name: Successor
 * Description: This class implements an internal representation of a succesor.
 * @author Pablo Moraga Navas, Javier Ruiz Mora and Jose Ramon Moratalla Munoz 
 * Subject: Intelligent Systems
 * Group: A4
 ********************************************************************************/
public class Successor {
	Action action; //Action to perform
	State state; //State resulting of perform the action
	int cost; //Cost of performing the action

	/*****************************************************************************
	 * Method name: Successor
	 * Description: Create a successor with the information passed by parameters
	 * @param action -> Action to perform
	 * @param state -> State resulting of perform the action
	 * @param cost -> Cost of performing the action
	 ****************************************************************************/
	public Successor(Action action, State state, int cost) {
		this.action = action;
		this.state = state;
		this.cost = cost;
	}

	/*****************************************************************************
	 * Method name: getAction
	 * Description: Returns the attribute 'action'
	 * @return action -> Action to perform
	 ****************************************************************************/
	public Action getAction() {
		return action;
	}

	/*****************************************************************************
	 * Method name: getState
	 * Description: Returns the attribute 'state'
	 * @return state -> State resulting of perform the action
	 ****************************************************************************/
	public State getState() {
		return state;
	}

	/*****************************************************************************
	 * Method name: getCost
	 * Description: Returns the attribute 'cost'
	 * @return cost -> Cost of performing the action
	 ****************************************************************************/
	public int getCost() {
		return cost;
	}

	/*********************************************************************************
	 * Method name: toString
	 * Description: Returns a string with the information of a successor
	 * @return cad -> String with the information of the successor
	 ********************************************************************************/
	@Override
	public String toString() {        
		return "Action: " + getAction().toString() + "\n" + getState().toString() + "Coste: " + getCost() +"\n";
	}
}