package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

enum searchType {BFS, DFS, UCS, A_Asterik, variantA_Asterik};

/*************************************************************************************
 * Class name: Main
 * Description: In this class we perform all tasks required by the problem.
 * @author Pablo Moraga Navas, Javier Ruiz Mora and Jose Ramon Moratalla Munoz 
 * Subject: Intelligent Systems
 * Group: A4
 ************************************************************************************/
public class Main {
	/*******************************************************************************
	 * Method name: main
	 * Description: First of all we create a field reading it from a file whose name
	 * is passed by parameters. Then we print the field information. After this we 
	 * generate the possible actions we can do and we print it. Later we apply the 
	 * initialState generated action to the existing field and we print the status of the 
	 * field after apply the action. Finally we write the information of the previous
	 * modified field in a file called modifiedField.txt.
	 * @param args -> arguments of the program
	 ******************************************************************************/
	public static void main(String[] args) throws IOException {  
		State initialState = readField("field.txt");
		initialState.SetTractor(0, 1);
		writeMovements(initialState);
		initialState.SetTractor(0, 0);
		System.out.println("FIRST STATE\n" + initialState);
		Problem prob = new Problem (new StateSpace(),initialState);
		int Infinitive = 100000;
		int maxDepth = 20;
		int depthIncrement = 1;
		boolean solution = false;
		boolean opt = false;        
		Scanner sc = new Scanner(System.in);
		System.out.println("Optimization s/n");
		String select = sc.next();
		opt = select.equals("s") || select.equals("S")?true:false;
		System.out.println ("Prompt 1,2,3,4,5 (Breath, Depth, Regular Cost, A*, variant A*):");
		int option = sc.nextInt();
		switch (option) {
		case 1: 
			solution = boundedSearch(prob, searchType.BFS, maxDepth,opt);
			break;
		case 2:
			System.out.println("Prompt 1,2,3 (Simple, Bounded, Iterative):");
			int option2 = sc.nextInt();
			switch (option2) {
			case 1:
				solution = boundedSearch(prob, searchType.DFS, Infinitive,opt);
				break;
			case 2:
				solution = boundedSearch(prob, searchType.DFS, maxDepth,opt);
				break;
			case 3:
				solution = Search (prob, searchType.DFS, maxDepth,depthIncrement,opt);
				break;
			}          
			break;
		case 3:
			solution = boundedSearch (prob, searchType.UCS, maxDepth,opt);
			break;
		case 4:
			solution = boundedSearch (prob, searchType.A_Asterik, maxDepth,opt);
			break;
		case 5:
			solution = boundedSearch(prob, searchType.variantA_Asterik, maxDepth, opt);
			break;
		default:
			System.out.println("Uncorrect option");
		}

		if (!solution)
			System.out.println("No solution has been found");
	}

	/*************************************************************************************
	 * Method name: readFile
	 * Description: Create a field with the information contained in a file. This method 
	 * also checks some errors that can happen when a field is read from a file.
	 * These errors that can happen are specified in the report of the laboratory project.
	 * @param fileName -> The name of the file we want to read from
	 * @return field -> The field filled with the information of the file
	 ************************************************************************************/
	private static State readField(String fileName) {	
		String line;
		State field = null;
		int v[] = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			line = br.readLine();
			StringTokenizer st = new StringTokenizer (line); 
			if(st.countTokens() == 6) {
				Square tractor=new Square (Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
				checkNegativeValue(tractor.getColumn(), fileName);
				checkNegativeValue(tractor.getRow(), fileName);
				int k = Integer.parseInt(st.nextToken());
				checkNegativeValue(k, fileName);
				int max=Integer.parseInt(st.nextToken());
				checkNegativeValue(max, fileName);
				checkIncorrectK(k, max, fileName);
				int columns = Integer.parseInt(st.nextToken());
				checkNegativeValue(columns, fileName);
				int rows = Integer.parseInt(st.nextToken());
				checkNegativeValue(rows, fileName);
				field = new State(tractor, k, max, columns, rows); 
				v = new int[columns*rows];
				int index = 0;
				int totalSand = 0;
				int minSand = k*columns*rows;
				while ((line = br.readLine())!=null){
					st = new StringTokenizer (line);
					for(int i = 0; i < columns; i++) {
						v[index]=Integer.parseInt(st.nextToken());
						checkNegativeValue(v[index], fileName);
						checkMaxLimit(v[index], max, fileName);
						totalSand += v[index];
						index++;
					}
				}
				checkIfThereIsSolution(minSand, totalSand, fileName);
			}else {
				System.out.println("FORMAT FILE ERROR: Invalid number of field's attributes in file "
						+ fileName + " (first line of the file)."
						+ "\nPlease, check that the format of " + fileName + " is correct.");
				System.exit(0);
			}
			br.close();
			field.startField(v);
		}catch (IOException e) {
			System.out.println("READING FILE ERROR: " + fileName + " not found. "
					+ "\nPlease, check the location of your file.");
			System.exit(0);
		}catch(NumberFormatException e) {
			System.out.println("FORMAT FILE ERROR: Non-Integer value has been found in file "
					+ fileName + "."
					+ "\nPlease, check that the format of " + fileName + " is correct.");
			System.exit(0);
		}catch(NoSuchElementException e) {	
			System.out.println("FORMAT FILE ERROR: Quantity table doesn't match with the size "
					+ "read from file " + fileName + "."
					+ "\nPlease, check that the format of " + fileName + " is correct.");
			System.exit(0);
		}
		return field;
	}

	/*************************************************************************************
	 * Method name: checkNegativeValue
	 * Description: This method check if the value passed by parameters is negative. If
	 * the value is negative it prints an error message and stops the program.
	 * @param field -> The field we want to detect errors from
	 * @param fileName -> The name of the file we want to read from
	 ************************************************************************************/
	private static void checkNegativeValue(int value, String fileName) {
		if(value < 0) {
			System.out.println("FORMAT FILE ERROR: A negative value has been found in file "
					+ fileName + "."
					+ "\nPlease, check that the format of " + fileName + " is correct.");
			System.exit(0);
		}
	}

	/*************************************************************************************
	 * Method name: checkIncorrectK
	 * Description: This method checks if the attribute 'k' of the field passed by
	 * parameters is greater than its attribute 'max'. This cannot happen so if this 
	 * situation is detected this method prints an error message and stops the program.
	 * @param state -> The field we want to detect errors from
	 * @param fileName -> The name of the file we want to read from
	 ************************************************************************************/
	private static void checkIncorrectK(int k, int max, String fileName) {
		if(k > max) {
			System.out.println("FORMAT FILE ERROR: K is greater than max limit "
					+ "in file "+ fileName + "."
					+ "\nPlease, check if k and max values are correct.");
			System.exit(0);
		}
	}

	/*************************************************************************************
	 * Method name: checkMaxLimit
	 * Description: This method checks if the sand of a field's square of the field passed by
	 * parameters is greater than field's square max limit. This cannot happen so if this 
	 * situation is detected this method prints an error message and stops the program.
	 * @param currentSand -> Sand of a field's square
	 * @param max -> State's square max limit
	 * @param fileNam -> The name of the file we want to read from
	 * @param i -> Column of the current field's square
	 * @param j -> Row of the current field's square
	 ************************************************************************************/
	private static void checkMaxLimit(int currentSand, int max, String fileName) {
		if(currentSand > max) {
			System.out.println("FORMAT FILE ERROR: Negative sand found in a square of the file "+ fileName + "."
					+ "\nPlease, check if the sand of the squares are correct.");
			System.exit(0);
		}
	}

	/*************************************************************************************
	 * Method name: checkIfThereIsSolution
	 * Description: There is a minimum quantity of sand in a field in order to we can
	 * reach a solution. This minimum is determined by the field size. For example in a
	 * field of 3x3 and k equal to 5, the minimum quantity of sand is 3*3*5 = 45.
	 * This method checks that there is enough sand in a field to reach a solution. If 
	 * there isn't enough sand to reach a solution this method prints an error message and
	 * stops the program. 
	 * @param minSand -> Sand's quantity needed in a field in order to be able to reach a solution
	 * @param currentSand -> Quantity of sand of the field we are checking
	 * @param fileName -> The name of the file we want to read from
	 ************************************************************************************/
	private static void checkIfThereIsSolution(int minSand, int currentSand, String fileName) {
		if(currentSand < minSand) {
			System.out.println("WRONG FIELD ERROR: There is no enough sand to solve the "
					+ "field read from file " + fileName + ".");
			System.exit(0);
		}
	}

	/*************************************************************************************
	 * Method name: boundedSearch
	 * Description: This method is the method that are in pseudocode in moodle. Basically
	 * it create the frontier, create the node with the initial state and insert it in the
	 * frontier. Then we start the loop. Within the loop we check if the first node of the 
	 * frontier has the goal State. If it has the goal state solution = true and in the next
	 * iteration the loop will stop. If it hasn't the goal state we create a list of successors
	 * of the state of the current node and we turn them into a nodes. The we iterate over the
	 * list of nodes and we are adding this nodes (that are the successors) to the frontier.
	 * This method also check if a node which is going to be inserted in the frontier is already
	 * in the frontier and if it is in the frontier we don't insert it into the frontier and in 
	 * the case of be already in the frontier and has a value greater than the node we are going
	 * to insert we update the value of the node to the lower value of both (that it's basically
	 * pruning).
	 * @param prob -> The name of the file we want to read from
	 * @param strategy -> Chosen strategy
	 * @param maxDepth -> Max depth the problem can reach
	 * @param opt -> Boolean value that indicates if the user want optimization or not
	 ************************************************************************************/
	private static boolean boundedSearch (Problem prob, searchType strategy, int maxDepth, boolean opt) throws IOException {        
		long timeStart, timeEnd, executionTime = 0;
		timeStart = System.currentTimeMillis();
		Frontier frontier = new Frontier();
		frontier.createFrontier();
		TreeNode initialState = new TreeNode(null, prob.getInitialState(), null, 0, 0, 0);
		frontier.insert(initialState);
		boolean sol = false;
		TreeNode n_current = null;
		Map<String, Double> hash = new HashMap<String, Double>(); 
		ArrayList<TreeNode> solution = new ArrayList<TreeNode>();
		while (!sol && !frontier.isEmpty()) {
			n_current = frontier.removeFirst();            
			if (prob.goalState(n_current.getState()))
				sol = true;
			else {
				ArrayList<Successor> LS = prob.getStateSpace().getSuccessor(n_current.getState());               
				ArrayList<TreeNode> LN = makeListTreeNode(LS,n_current,maxDepth,strategy);                
				for (int i = 0; i < LN.size(); i++) {    
					if (opt) {
						String n = getMD5(LN.get(i).getState().toString());
						if (hash.containsKey(n)) {                         
							if (LN.get(i).getValue()<hash.get(n).intValue()){                           
								frontier.insert(LN.get(i));
								hash.replace(n, LN.get(i).getValue());                                                        
							}
						}
						else {                       
							hash.put(n, LN.get(i).getValue());
							frontier.insert(LN.get(i));
						}
					}
					else
						frontier.insert(LN.get(i));
				}
			}            
		}
		if (sol) {
			while (n_current.getParent()!=null) {
				solution.add(n_current);
				n_current=n_current.getParent();
			}
			solution.add(initialState);
			timeEnd = System.currentTimeMillis();
			executionTime = timeEnd - timeStart;
			writeSolution(solution, strategy, opt, executionTime);
			System.out.println("Solution found. File Generated");

		}        
		return sol;
	}

	/*************************************************************************************
	 * Method name: makeListTreeNode
	 * Description: This method turn the list of successors passed by parameters in a list
	 * of TreeNode objects. It creates it with a different TreeNode value depending on the 
	 * strategy chosen.
	 * @param LS -> List of successor
	 * @param n_current -> Current node
	 * @param maxDepth -> Max depth the problem can reach
	 * @param strategy -> Chosen strategy
	 * @return LN -> List of successor turned into TreeNode objects
	 ************************************************************************************/
	private static ArrayList<TreeNode> makeListTreeNode(ArrayList<Successor> LS, TreeNode n_current, int maxDepth, searchType strategy) {
		ArrayList<TreeNode> LN = new ArrayList<TreeNode>();
		if (n_current.getDepth() < maxDepth) {
			TreeNode aux = null;
			Successor s = null;
			for (int i = 0; i < LS.size(); i++) {
				s = (Successor) LS.get(i);
				if (strategy == searchType.BFS)
					aux = new TreeNode(n_current, s.getState(), s.getAction(),n_current.getCost()+s.getCost(),n_current.getDepth()+1, n_current.getDepth()+1);                  
				if (strategy == searchType.DFS)
					aux = new TreeNode(n_current, s.getState(), s.getAction(),n_current.getCost()+s.getCost(),n_current.getDepth()+1, maxDepth-(n_current.getDepth()+1));
				if (strategy == searchType.UCS)
					aux = new TreeNode(n_current, s.getState(), s.getAction(),n_current.getCost()+s.getCost(),n_current.getDepth()+1, n_current.getCost()+s.getCost());
				if (strategy == searchType.A_Asterik)
					aux = new TreeNode(n_current, s.getState(), s.getAction(),n_current.getCost()+s.getCost(),n_current.getDepth()+1, n_current.getCost()+s.getCost()+s.getState().getHeuristic());
				if (strategy == searchType.variantA_Asterik)
					aux = new TreeNode(n_current, s.getState(), s.getAction(),n_current.getCost()+s.getCost(),n_current.getDepth()+1, ((n_current.getCost()+s.getCost())*0.3)+(s.getState().getHeuristic()*0.7));
				LN.add(aux);
			}
		}
		return LN;
	}

	/*************************************************************************************
	 * Method name: Search
	 * Description: This method is the search method that is in pseudocode in moodle. It 
	 * calls to boundedSearch in a loop until a solution is found or until the max depth
	 * is reach
	 * @param prob -> Problem object
	 * @param strategy -> Strategy chosen
	 * @param maxDepth -> Max depth the problem can reach
	 * @param incDepth -> Accumulated depth 
	 * @return solution -> Boolean value (true or false) depending on if a solution has 
	 * been found or not
	 ************************************************************************************/
	private static boolean Search (Problem prob, searchType strategy, int maxDepth, int depthIncrement, boolean opt) throws IOException {        
		int currentDepth = depthIncrement;
		boolean solucion = false;
		while (!solucion && currentDepth<=maxDepth) {
			solucion = boundedSearch(prob, strategy, currentDepth, opt);
			currentDepth += depthIncrement;
		}
		return solucion;        
	}

	/*************************************************************************************
	 * Method name: writeSolution
	 * Description: This method writes in a .txt file called solution.txt that contains
	 * the solutions obtained by the different strategies as well as the depth and cost data of the solutions 
	 * The file follows the same structure as provided in moodle
	 * @param solution -> Solution Found 
	 * @param strategy -> Strategy chosen
	 ************************************************************************************/
	private static void writeSolution (ArrayList<TreeNode> solution, searchType strategy, boolean opt, long executionTime) throws IOException{
		File archivo = new File("solution3.txt");
		State aux;

		FileWriter file = new FileWriter(archivo);
		PrintWriter pw = new PrintWriter(file); 
		pw.println("--------------------STRATEGY: " + strategy + "--------------------");
		pw.println("OPTIMIZATION: " + opt + "   ||   EXECUTION TIME: " + executionTime);
		pw.println("TOTAL DEPTH: " + (solution.get(0).getDepth()+1) + " TOTAL COST: " + solution.get(0).getCost());
		for (int i = solution.size()-1; i >= 0; i--) {
			aux = solution.get(i).getState();
			pw.println("Action: " + solution.get(i).getAction());
			pw.println("State: " + aux.getTractor().getRow() + " " + aux.getTractor().getColumn() + " " + 
					aux.getK() + " " + aux.getMax() + " " + aux.getRows() + " " + aux.getColumns());
			for (int j = 0; j < aux.getRows(); j++) {
				for (int k = 0; k < aux.getColumns(); k++) {
					pw.print(aux.getSquare(new Square(j, k)).getSand()+ " ");
				}
				pw.println(""); 
			}    
			pw.println("F: " + solution.get(i).getValue() + " Depth: " + solution.get(i).getDepth()
					+ " Cost: " + solution.get(i).getCost());
			pw.println("");  
		}        
		pw.close();        
	}

	/*************************************************************************************
	 * Method name: writeMovements
	 * Description: This method writes in a file called movements.txt the set of movements
	 * that are valid from the state passed by parameters
	 * @param state -> The state we want to know the possible movements
	 ************************************************************************************/
	private static void writeMovements (State State) throws IOException{
		ArrayList<Action> actions=new ArrayList<Action>(State.getActions());
		File archivo = new File("movements2.txt");
		FileWriter file = new FileWriter(archivo);
		PrintWriter pw = new PrintWriter(file);  
		for (int i = 0; i < actions.size();i++) 
			pw.println(actions.get(i));
		pw.close();        
	}

	/*************************************************************************************
	 * Method name: getMD5
	 * Description: This method is used for pruning. It creates a unique hash for the 
	 * state information passed by parameters in order to identify possible duplicates
	 * state and not to insert into the frontier.
	 * @param state -> the state we want to generate the hash
	 ************************************************************************************/
	private static String getMD5(String stateInfo) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(stateInfo.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashText = number.toString(16);
			while(hashText.length() < 32) {
				hashText = "0" + hashText;
			}
			return hashText;
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}