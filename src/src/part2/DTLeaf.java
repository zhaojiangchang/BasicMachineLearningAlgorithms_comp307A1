package part2;

public class DTLeaf implements Node{

	private String className;
	private double probability;
	public DTLeaf(int className, double probability){
		if(className==0){
			this.className = "live";
		}else this.className = "die";
		this.probability = probability;
	}
	
	
	public void report(String indent){
//		if (count==0)
//		System.out.format("%sUnknown\n", indent);
//		else
		System.out.format("%sClass %s, prob= %4.2f%n",
		indent, className, probability);
		}


	@Override
	public Node getLeft() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Node getRight() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getAttName() {
		// TODO Auto-generated method stub
		return null;
	}


	public int getClassName() {
		int classType;
		if(className.equals("live")){
			return classType = 0;
		}else return classType = 1;
	}


	public void setClassName(String className) {
		this.className = className;
	}

}
