package domain;

import java.util.PriorityQueue;

/*************************************************************************************
 * Class name: Frontier
 * Description: This class implements an internal representation of the frontier
 * @author Pablo Moraga Navas, Javier Ruiz Mora and Jose Ramon Moratalla Munoz 
 * Subject: Intelligent Systems
 * Group: A4
 ************************************************************************************/
public class Frontier {
	PriorityQueue<TreeNode> queue; //Data structure decided in our test

	/*********************************************************************************
	 * Method name: Frontier
	 * Description: Create a frontier with the information passed by parameters
	 ********************************************************************************/
	public Frontier() {
		queue = new PriorityQueue();
	}

	/*********************************************************************************
	 * Method name: createFrontier
	 * Description: Initializes the frontier
	 ********************************************************************************/
	public void createFrontier() {
		queue.clear();
	}

	/*****************************************************************************
     * Method name: insert
     * Description: Insert a treeNode into the frontier
     * @param treeNode -> TreeNode that is going to be inserted in the frontier
     ****************************************************************************/
	public void insert(TreeNode treeNode) {
		queue.add(treeNode);
	}
	
	/*****************************************************************************
     * Method name: removeFirst
     * Description: Remove the initialState element of the frontier
     * @return treeNode -> TreeNode that is removed from the frontier
     ****************************************************************************/
	public TreeNode removeFirst() {
		return (TreeNode) queue.poll();
	}

	/*****************************************************************************
     * Method name: isEmpty
     * Description: Checks if the frontier is empty or not
     * @return boolean -> true if the frontier is empty. If the frontier is not
     * empty it returns false
     ****************************************************************************/
	public boolean isEmpty() {
		return queue.isEmpty();
	}           
}