package domain;

/*********************************************************************************
 * Class name: Square
 * Description: This class implements an internal representation of a field's square
 * @author Pablo Moraga Navas, Javier Ruiz Mora and Jose Ramon Moratalla Munoz 
 * Subject: Intelligent Systems
 * Group: A4
 ********************************************************************************/
public class Square {
	private int row; //Row where the square is
    private int column; //Column where the square is
    private int sand;//Quantity of sand that there is in the square

    /*****************************************************************************
   	 * Method name: Square
   	 * Description: Create a box and initialize its variables.
   	 * @param x -> Row where the square is
   	 * @param y -> Column where the square is
   	 ****************************************************************************/
    public Square(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /*****************************************************************************
   	 * Method name: Square
   	 * Description: Create a box and initialize its variables.
   	 * @param x -> Row where the square is
   	 * @param y -> Column where the square is
   	 * @param sand -> Quantity of sand there is in the square
   	 ****************************************************************************/
    public Square(int row, int column, int sand) {
        this.row = row;
        this.column = column;
        this.sand = sand;
    }

    /*********************************************************************************
	 * Method name: getRow
	 * Description: Returns the attribute 'row'
	 * @return row -> Row where the square is
	 ********************************************************************************/
    public int getRow() {
        return row;
    }

    /*********************************************************************************
	 * Method name: getColumn
	 * Description: Returns the attribute 'column'
	 * @return column -> Column where the square is
	 ********************************************************************************/
    public int getColumn() {
        return column;
    }

    /*****************************************************************************
	 * Method name: getSand
	 * Description: Returns the attribute 'amount'
	 * @return sand -> Quantity of sand there is in the square
	 ****************************************************************************/
    public int getSand() {
        return sand;
    }    
    
    /*****************************************************************************
	 * Method name: setSand
	 * Description: Set a value to the attribute 'amount'
	 * @param sand -> Quantity of sand there is in the square
	 ****************************************************************************/
    public void setSand(int sand) {
        this.sand = sand;
    }
   
    /*********************************************************************************
     * Method name: toString
     * Description: Returns a string with the information of a square
     ********************************************************************************/
    @Override
    public String toString() {
        return "Square{" + "row=" + row + ", column=" + column + ", amount=" + sand + '}';
    }
}