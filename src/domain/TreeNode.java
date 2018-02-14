package domain;

/*********************************************************************************
 * Class name: TreeNode
 * Description: This class implements an internal representation of a node of the tree
 * @author Pablo Moraga Navas, Javier Ruiz Mora and Jose Ramon Moratalla Munoz 
 * Subject: Intelligent Systems
 * Group: A4
 ********************************************************************************/
public class TreeNode implements Comparable<TreeNode> {
	private TreeNode parent; //The parent of the TreeNode, null in the case of be the root
	private State state; //State associated to the TreeNode
	private Action action; //Action associated the TreeNode
	private int cost; //Cost accumulated
	private int depth; //Depth of the TreeNode
	private double value; //Value

	/*****************************************************************************
	 * Method name: TreeNode
	 * Description: Create a TreeNode with the information passed by parameters
	 * @param parent -> The parent of the TreeNode, null in the case of be the root
	 * @param state -> State associated to the TreeNode
	 * @param action -> Action associated the TreeNode
	 * @param cost -> Cost accumulated
	 * @param depth -> Depth of the tree node
	 * @param value -> Value (by now a random value)
	 ****************************************************************************/
	public TreeNode(TreeNode parent, State state, Action action, int cost, int depth, int value) {
		this.parent = parent;
		this.state = state;        
		this.action = action;
		this.cost = cost;
		this.depth = depth;        
		this.value = value;  
	}

	/*****************************************************************************
	 * Method name: TreeNode
	 * Description: Create a TreeNode with the information passed by parameters
	 * @param parent -> The parent of the TreeNode, null in the case of be the root
	 * @param state -> State associated to the TreeNode
	 * @param action -> Action associated the TreeNode
	 * @param cost -> Cost accumulated
	 * @param depth -> Depth of the tree node
	 * @param value -> Value (by now a random value)
	 ****************************************************************************/
	public TreeNode(TreeNode parent, State state, Action action, int cost, int depth, double value) {
		this.parent = parent;
		this.state = state;        
		this.action = action;
		this.cost = cost;
		this.depth = depth;        
		this.value = value;  
	}

	
	/*********************************************************************************
	 * Method name: getParent
	 * Description: Returns the attribute 'parent'
	 * @return parent -> The parent of the TreeNode, null in the case of be the root
	 ********************************************************************************/
	public TreeNode getParent() {
		return parent;
	}

	/*********************************************************************************
	 * Method name: getState
	 * Description: Returns the attribute 'state'
	 * @return parent ->   State associated to the TreeNode
	 ********************************************************************************/
	public State getState() {
		return state;
	}

	/*********************************************************************************
	 * Method name: getAction
	 * Description: Returns the attribute 'action'
	 * @return action -> Action associated the TreeNode
	 ********************************************************************************/
	public Action getAction() {
		return action;
	}

	/*********************************************************************************
	 * Method name: getCost
	 * Description: Returns the attribute 'cost'
	 * @return cost -> Cost accumulated
	 ********************************************************************************/
	public int getCost() {
		return cost;
	}   

	/*********************************************************************************
	 * Method name: getDepth
	 * Description: Returns the attribute 'depth'
	 * @return depth -> Depth of the tree node
	 ********************************************************************************/
	public int getDepth() {
		return depth;
	}

	/*********************************************************************************
	 * Method name: getValue
	 * Description: Returns the attribute 'value'
	 * @return value -> Value
	 ********************************************************************************/
	public double getValue() {
		return value;
	}

	/*****************************************************************************
	 * Method name: compareTo
	 * Description: Compare the value of the current node and the node passed by parameters
	 * @param node -> TreeNode we want to compare to
	 * @return k -> Integer value depending (1 or -1) if the current node is equals or not
	 ****************************************************************************/
	public int compareTo(TreeNode node) {
		int k = 0;
		if (node.getValue() < getValue())
			k = +1;
		else
			if (node.getValue() > getValue())
				k = -1;
		return k;
	}     

	/*********************************************************************************
	 * Method name: toString
	 * Description: Returns a string with the information of a TreeNode
	 * @return cad -> String with the information of the TreeNode
	 ********************************************************************************/
	@Override
	public String toString() {
		return "TreeNode{" + "parent=" + parent + ", state=" + state + ", accion=" + action + ", cost=" + cost + ", depth=" + depth + ", value=" + value + '}';
	}  
}