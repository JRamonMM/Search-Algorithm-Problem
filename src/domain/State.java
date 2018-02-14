package domain;

import static java.lang.Math.abs;
import java.util.ArrayList;

/*********************************************************************************
 * Class name: State
 * Description: This class implements an internal representation of a state of a field.
 * @author Pablo Moraga Navas, Javier Ruiz Mora and Jose Ramon Moratalla Munoz 
 * Subject: Intelligent Systems
 * Group: A4
 ********************************************************************************/
public class State {
	private Square tractor; //Square that represents the position of the tractor
	private int k; //Ideal quantity of sand that must be in each square
	private int max; //Max quantity of sand that there can be in a square
	private int rows; //Number of rows of the field
	private int columns; //Number of columns of the field
	private Square cells[][]; //Array of squares that represents the field

	/*****************************************************************************
	 * Method name: State
	 * Description: Create a field with the information passed by parameters
	 * @param tractor -> Square that represents the position of the tractor
	 * @param k -> Ideal quantity of sand that must be in each square
	 * @param max -> Max quantity of sand that there can be in a square
	 * @param rows -> Number of rows of the field
	 * @param columns -> Number of columns of the field
	 ****************************************************************************/
	public State(Square tractor, int k, int max, int rows, int columns) {
		this.tractor = tractor;
		this.k = k;
		this.max = max;
		this.rows = rows;
		this.columns = columns;
		cells = new Square [rows][columns];   
	}

	/*********************************************************************************
	 * Method name: getTractor
	 * Description: Returns the attribute 'tractor'
	 * @return tractor -> Square that represents the position of the tractor
	 ********************************************************************************/
	public Square getTractor() {
		return tractor;
	}
	
	public void SetTractor(int row, int column) {
		this.tractor = cells[row][column];
	}

	/*********************************************************************************
	 * Method name: getK
	 * Description: Returns the attribute 'k'
	 * @return k -> Ideal quantity of sand that must be in each square
	 ********************************************************************************/
	public int getK() {
		return k;
	}

	/*********************************************************************************
	 * Method name: getMax
	 * Description: Returns the attribute 'max'
	 * @return max -> Max quantity of sand that there can be in a square
	 ********************************************************************************/
	public int getMax() {
		return max;
	}

	/*********************************************************************************
	 * Method name: getRows
	 * Description: Returns the attribute 'rows'
	 * @return rows -> Number of rows of the field
	 ********************************************************************************/
	public int getRows() {
		return rows;
	}

	/*********************************************************************************
	 * Method name: getColumns
	 * Description: Returns the attribute 'columns'
	 * @return columns -> Number of columns of the field
	 ********************************************************************************/
	public int getColumns() {
		return columns;
	}

	/*********************************************************************************
	 * Method name: getSquare
	 * Description: Returns the square that has the same position than the cell passed by
	 * parameters, with all its information 
	 * @param cell -> Cell we want to get all the information
	 * @return square -> Square with all the information that fits with the cell passed
	 * by parameters
	 ********************************************************************************/
	public Square getSquare(Square cell) {
		return (Square) cells[cell.getRow()][cell.getColumn()];
	}

	/*********************************************************************************
	 * Method name: setSquare
	 * Description: Replace the square that has the same position than the cell passed by
	 * parameters by the cell passed by parameters 
	 * @param cell -> Cell we want to save
	 ********************************************************************************/
	public void setSquare(Square cell) {
		cells[cell.getRow()][cell.getColumn()] = cell;     
	}

	/*********************************************************************************
	 * Method name: getS
	 * Description: Returns the 's' value. S is the quantity of sand that we can move from
	 * the current tractor's cell to the adjacent cells of tractor's current cell in order
	 * to left 'k' quantity of sand in the current tractor's cell
	 * @return s -> Quantity of sand that we can move from the current tractor's cell to
	 * the adjacent cells of tractor's current cell in order to left 'k' quantity of sand
	 * in the current tractor's cell
	 ********************************************************************************/
	public int getS() {
		return (Math.max(getSquare(tractor).getSand()-getK(),0));
	}

	/*********************************************************************************
	 * Method name: startField
	 * Description: Fill the matrix of squares that represents the field from the vector
	 * passed by parameters that has been filled (previously) with the information of the
	 * source file
	 * @param v -> Vector that has been filled (previously) with the information of the
	 * source file.
	 ********************************************************************************/
	public void startField(int v[]) {        
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				cells[i][j] = new Square(i, j, v[getRows()*i+j]);                 
			}
		}    
	}

	/*********************************************************************************
	 * Method name: posibleAmount
	 * Description: If the current sand of the square passed by parameters is lower than
	 * the max limit of sand, this method returns the quantity of sand that this square can
	 * get without exceed the max limit of sand. On the contrary, if the current sand of
	 * the square passed by parameters is greater than the max limit of sand this method
	 * returns 0, because no sand can be admitted in the square
	 * @param cell -> Cell we wan to check the sand it can admit
	 * @return possibleAmound -> Quantity of sand that this square can get without exceed
	 * the max limit of sand
	 ********************************************************************************/
	public int posibleAmount(Square cell) {
		return ((getSquare(cell).getSand()<max) ? max-getSquare(cell).getSand() : 0);
	}

	/*********************************************************************************
	 * Method name: isInside
	 * Description: Returns true or false depending on if the position of the square passed
	 * by parameters is inside the field or it's out of the field
	 * @param cell -> Cell we wan to check if is inside the field
	 * @return inside -> Boolean true or false depending on if the position of the square
	 * passed by parameters is inside the field or it's out of the field
	 ********************************************************************************/
	public boolean isInside(Square cell) {
		return (cell.getRow()>=0 && cell.getRow()<getRows() && cell.getColumn()>=0 && cell.getColumn()<getColumns());
	}

	/*********************************************************************************
	 * Method name: adjacentsCells
	 * Description: Returns the adjacent cells with respect to the position of the tractor.
	 * @return adjacentCells -> An array that contains the adjacent cells with respect to
	 * the position of the tractor. Its size depends on the number of adjacent cells, it
	 * can be 4 as maximum
	 ********************************************************************************/
	public Square[] adjacentCells() {
		ArrayList<Square> cells = new ArrayList <Square>();
		Square aux;
		for (int i = -1;i <= 1; i++) {
			for (int j = -1; j <= 1 ; j++) {
				aux = new Square(tractor.getRow()+i, tractor.getColumn()+j);                
				if (isInside(aux) && abs(i+j) == 1) {                   
					cells.add(getSquare(aux));
				}
			}
		}
		return cells.toArray(new Square[cells.size()]);
	}

	/*********************************************************************************
	 * Method name: distributeSand
	 * Description: This method is the backtracking algorithm we need to get the possible
	 * different distribution of sand among the adjacent cells.
	 * @param stage -> Integer that indicates which position of the array sol we are working in
	 * @param s -> Quantity of sand we are going to distribute
	 * @param adjacents -> Array with the adjacent cells with respect to the position of the tractor
	 * @param sol -> Array we are going to use to build the possible distributions that have
	 * to satisfy some constraints in order to turn into a solution
	 * @param solutions -> ArrayList where we are going to save the possible distributions
	 * that satisfy the needed constraints to turn into a solution
	 ********************************************************************************/
	public void distributeSand(int stage, int s, Square[] adyacentes, int[] sol, ArrayList solutions) {
		int i;
		if (stage == sol.length) {
			if (isSolution(sol,adyacentes,s)) {
				int copy[] = new int[sol.length];
				System.arraycopy(sol, 0, copy, 0, sol.length);
				solutions.add(copy);              
			}
		}
		else {
			for (i = 0; i <= s; i++) {
				if (i <= posibleAmount(getSquare(adyacentes[stage])) && isPossible(i, stage, sol, s)) {
					sol[stage]=i;
					distributeSand(stage+1, s, adyacentes, sol, solutions);
				}                    
			}  
		}
	}

	/*********************************************************************************
	 * Method name: isPossible
	 * Description: This method checks if the accumulated amount of sand of a possible distribution
	 * generated exceed the 's' value. It returns true or false depending on if the accumulated
	 * amount of sand of the possible distribution generated exceed or not the 's' value
	 * @param i -> Sand we distribute in this stage
	 * @param stage -> Integer that indicates which position of the array sol we are working in
	 * @param sol -> Possible distribution we want to check if it can be a solution
	 * @param s -> Quantity of sand we are going to distribute
	 ********************************************************************************/
	public boolean isPossible(int i, int stage, int[] sol, int s) {
		int accumulated = 0;
		for (int k = 0; k < stage; k++) 
			accumulated += sol[k];
		return ((accumulated+i)<=s);
	}

	/*********************************************************************************
	 * Method name: isSolution
	 * Description: This method returns true or false depending on if the possible distribution sol
	 * is a solution or not. Also it takes into account the possibility of the 's' value can not be
	 * distributed into the adjacent cells. For example you can distribute three units of sand (s = 3)
	 * but the adjacent cells only admit two units of sand. In this case you can only distribute
	 * two units of sand instead of three.
	 * @param sol -> Possible distribution we want to check if it can be a solution
	 * @param adjacents -> Array with the adjacent cells with respect to the position of the tractor
	 * @param s -> Quantity of sand we are going to distribute
	 ********************************************************************************/
	public boolean isSolution(int sol[], Square adjacents[], int s) {
		int accumulated=0,total=0;        
		for (int k = 0; k < sol.length; k++) {
			accumulated += sol[k];
			total += posibleAmount(getSquare(adjacents[k]));
		}       
		return (accumulated==Integer.min(total, s));
	}

	/*********************************************************************************
	 * Method name: getActions()
	 * Description: This method returns a list with the possible actions we can perform from
	 * the current state, that are basically the solutions that we reach in the backtracking
	 * algorithm but turned into action objects
	 * @return actions -> Possible actions we can perform from the current state
	 ********************************************************************************/
	public ArrayList<Action> getActions() {
		Square [] adjacents = adjacentCells();       
		int sol[]=new int[adjacents.length];
		ArrayList solutions = new ArrayList();           

		distributeSand(0,getS(),adjacents,sol,solutions);

		ArrayList<Action> actions=new ArrayList<Action>();

		int aux[];  
		for (int k=0;k<adjacents.length;k++) {
			for (int i=0; i<solutions.size();i++){
				aux=(int[]) solutions.get(i);
				actions.add(new Action(adjacents[k],adjacents,aux));
			}  
		}        
		return actions;
	}

	/*********************************************************************************
	 * Method name: printActions()
	 * Description: This method prints the possible actions we can perform from the current state
	 * @param actions -> Possible actions we can perform from the current state
	 ********************************************************************************/
	public void printActions(ArrayList<Action> actions){
		for (int i = 0; i < actions.size(); i++)          
			System.out.println(actions.get(i));
	}

	/*********************************************************************************
	 * Method name: getState()
	 * Description: This method apply the action passed by parameters and return the state
	 * resulting of applying that action
	 * @param action -> Action we can apply to the current field
	 * @return newState -> New state resulting of apply the action passed by parameters
	 * to the current state
	 ********************************************************************************/
	public State getState(Action action)  {  
		State newstate = new State(getTractor(), getK(), getMax(), getRows(), getColumns());
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				newstate.cells[i][j] = new Square(i, j, cells[i][j].getSand());                 
			}
		}        
		newstate.getSquare(newstate.getTractor()).setSand(newstate.getSquare(newstate.getTractor()).getSand()-action.distributedAmount());
		newstate.tractor=(Square) action.getMovement();
		Square[] adjacents = action.getAdjacents();
		int[] values = action.getValues();

		for (int i = 0; i < adjacents.length; i++) {
			newstate.getSquare(adjacents[i]).setSand(newstate.getSquare(adjacents[i]).getSand()+values[i]);
		}
		return newstate;
	}

	/*********************************************************************************
	 * Method name: getCost()
	 * Description: Returns the cost of an action
	 * @param action -> The action we want to calculate the cost
	 * @return cost -> The cost of the action
	 ********************************************************************************/
	public int getCost (Action action) {        
		return action.distributedAmount()+1;
	}

	/*********************************************************************************
	 * Method name: isGoal()
	 * Description: This method returns true or false depending on if this state is the goal
	 * state or not. A state is the goal state if the quantity of sand that are in each cell
	 * is equal to k
	 * @return ok -> Boolean that indicate if this state is the goal state or not
	 ********************************************************************************/
	public boolean isGoal(){
		boolean ok = true;
		for (int i = 0; i < getRows() && ok; i++) {
			for (int j = 0; j < getColumns() && ok; j++) {
				if (cells[i][j].getSand() != getK()) 
					ok = false;
			}
		}    
		return ok;
	}

	/*********************************************************************************
	 * Method name: getHeuristic
	 * Description: This method calculate the heuristic of the current state and return it
	 * @return h -> Heuristic of the current state
	 ********************************************************************************/
	public int getHeuristic(){
		int count = 0;
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				if (cells[i][j].getSand() != getK()) 
					count++;
			}
		}    
		return count;
	}

	/*********************************************************************************
	 * Method name: equals
	 * Description: This method compare the current state with the state passed by
	 * parameters. First check the position of the tractor (two states with a different
	 * position of the tractor are not equal). If the position of the tractor is equal
	 * then check the quantity of sand of the matrix of squares
	 * @return isEqual -> Boolean value (true if equals, false if not equals)
	 ********************************************************************************/
	@Override
	public boolean equals(Object obj) {
		State State = (State) obj;
		Square aux = null;
		boolean isequal = true;
		if (getTractor().getColumn()!=State.getTractor().getColumn() || getTractor().getRow()!=State.getTractor().getRow())
			isequal = false;
		else {
			for (int i = 0;i<getRows() && isequal; i++) {
				for (int j = 0;j < getColumns() && isequal; j++) {
					aux = new Square (i,j);
					if (cells[i][j].getSand() != State.getSquare(aux).getSand()) 
						isequal = false;                    
				}
			}    
		}        
		return isequal;
	}

	/*********************************************************************************
	 * Method name: toString
	 * Description: Returns a string with the information of the current state of the field
	 * @return cad -> String with the information of the current state of the field
	 ********************************************************************************/
	@Override
	public String toString() {
		String cad=tractor.getRow() + " " + tractor.getColumn() + " " + getK() + " " + getMax() + " " + getRows() + 
				" " + getColumns() + "\n";
		for (int i=0;i<cells.length;i++) {
			for (int j=0;j<cells[0].length;j++) {
				cad+=cells[i][j].getSand()+ " ";
			}
			cad+="\n";
		}    
		return cad;
	}   
}