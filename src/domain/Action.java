package domain;

/*************************************************************************************
 * Class name: Action
 * Description: This class implements an internal representation of an action.
 * @author Pablo Moraga Navas, Javier Ruiz Mora and Jose Ramon Moratalla Munoz 
 * Subject: Intelligent Systems
 * Group: A4
 ************************************************************************************/
public class Action {
	Square movement; //The square where we are going to move the tractor
    Square [] adjacents; //The adjacent squares of the state we want to generate the possible actions
    int [] values; //Amount of sand we are going to move associated to each adjacent square
    
    /*********************************************************************************
     * Method name: Action
     * Description: Create an action object with the information passed by parameters.
     * @param movement -> The square where we are going to move the tractor
     * @param adjacents -> Vector with the adjacent squares of the state we want to generate
     * the possible actions
     * @param values -> Vector with the amount of sand we are going to move associated to
     * each adjacent square
     ********************************************************************************/
	public Action(Square movement, Square[] adjacents, int[] values) {
		this.movement = movement;
		this.adjacents = new Square [adjacents.length];
		System.arraycopy(adjacents, 0, this.adjacents, 0, adjacents.length);
		this.values = new int[values.length];
		System.arraycopy(values, 0, this.values, 0, values.length);                
	}

	/*********************************************************************************
     * Method name: getMovement
     * Description: Returns the attribute 'movement'
     * @return movement -> The square where we are going to move the tractor
     ********************************************************************************/
	public Square getMovement() {
		return movement;
	}

	/*********************************************************************************
     * Method name: getAdjacents
     * Description: Returns the attribute 'adjacents'
     * @return adjacents -> Vector with the adjacent squares of the state we want to generate
     * the possible actions
     ********************************************************************************/
	public Square[] getAdjacents() {
		return adjacents;
	}

	 /*********************************************************************************
     * Method name: getValues
     * Description: Returns the attribute 'adjacents'
     * @return values -> Vector with the amount of sand we are going to move associated to
     * each adjacent square
     ********************************************************************************/
	public int[] getValues() {
		return values;
	}

	/*********************************************************************************
     * Method name: distributedAmount
     * Description: Returns the total amount of sand that we are going to move if this
     * action is applied (the sum of the vector attribute values).
     * @return total -> Total amount of sand that we are going to move if this action is 
     * applied (the sum of the vector attribute values).
     ********************************************************************************/
	public int distributedAmount() {
		int total = 0;
		for (int i = 0; i < getValues().length; i++)
			total += values[i];
		return total;
	}

	 /*********************************************************************************
     * Method name: toString
     * Description: Returns a string with the information of an action
     * @return cad -> String with the information of the action
     ********************************************************************************/
	@Override
	public String toString() {
		String cadena = "((" + movement.getRow() + "," + movement.getColumn() + "), [";
		for (int j = 0; j < adjacents.length; j++)                 
			cadena += "(" + values[j] + ",(" + adjacents[j].getRow() + "," + adjacents[j].getColumn() + "))";               
		cadena += "]," + (distributedAmount()+1) + ")";
		return cadena;
	}
}