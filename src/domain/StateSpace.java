package domain;

import java.util.ArrayList;

/*********************************************************************************
 * Class name: StateSpace
 * Description: This class implements an internal representation of a StateSpace
 * @author Pablo Moraga Navas, Javier Ruiz Mora and Jose Ramon Moratalla Munoz 
 * Subject: Intelligent Systems
 * Group: A4
 ********************************************************************************/
public class StateSpace { 
	/*****************************************************************************
	 * Method name: StateSpace
	 * Description: Create a StateSpace with the information passed by parameters
	 ****************************************************************************/
	public StateSpace() {
		
	}
	
	/*********************************************************************************
	 * Method name: getSuccessor()
	 * Description: This method returns a list with the successors of the state passed by
	 * parameters
	 * @param state -> The state whose goals we want to get
	 * @return successors -> list with the successors of the state passed by parameters
	 ********************************************************************************/
    public ArrayList<Successor> getSuccessor(State State) {           
        ArrayList<Successor> successors = new ArrayList<Successor>();
        ArrayList<Action> actions = new ArrayList<Action>(State.getActions());        
        for (int i = 0; i < actions.size(); i++) {           
            successors.add(new Successor(actions.get(i),State.getState(actions.get(i)),State.getCost(actions.get(i))));
        }
        return successors;            
    }
}