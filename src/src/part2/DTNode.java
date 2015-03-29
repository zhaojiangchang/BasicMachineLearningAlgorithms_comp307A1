package part2;

public class DTNode implements Node{
private String attName;
private Node left;
private Node right;

public DTNode(String attName, Node left, Node right){
	this.attName = attName;
	this.left = left;
	this.right = right;
	
}


	
public Node getLeft() {
	return left;
}

public void setLeft(DTNode left) {
	this.left = left;
}

public Node getRight() {
	return right;
}

public void setRight(DTNode right) {
	this.right = right;
}

public String getAttName() {
	return attName;
}



public void setAttName(String attName) {
	this.attName = attName;
}



public void report(String indent){
		System.out.format("%s%s = True:\n",
		indent, attName);
		left.report(indent+" ");
		System.out.format("%s%s = False:\n",
		indent, attName);
		right.report(indent+" ");
		}



@Override
public int getClassName() {
	// TODO Auto-generated method stub
	return -1;
}

}
 