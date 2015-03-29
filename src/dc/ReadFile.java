package dc;

import java.text.NumberFormat;
import java.util.*;
import java.io.*;

public class ReadFile{
	// some bits of java code that you may use if you wish.
	// assumes that the enclosing class has fields:
	int numCategories;
	int numAtts;
	private List<String> trainingCategoryNames;
	private List<String> trainingAttNames;
	private List<ReadFile.Instance> allTrainingInstances;
	private List<String> testCategoryNames;
	private static List<String> testAttNames;
	private static DTLeaf DTLeaf;
	private List<ReadFile.Instance> allTestInstances;
	static int matched = 0;

	public ReadFile(){

	}

	private void readTrainingDataFile(String fname){
		/* format of names file:
		 * names of categories, separated by spaces
		 * names of attributes
		 * category followed by true's and false's for each instance
		 */

		System.out.println("Reading data from file "+fname);
		try {
			Scanner din = new Scanner(new File(fname));

			trainingCategoryNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) trainingCategoryNames.add(s.next());
			numCategories=trainingCategoryNames.size();
			System.out.println(numCategories +" categories");

			trainingAttNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) trainingAttNames.add(s.next());
			numAtts = trainingAttNames.size();
			System.out.println(numAtts +" attributes");

			allTrainingInstances = readInstances(din);
			din.close();
		}
		catch (IOException e) {
			throw new RuntimeException("Data training File caused IO exception");
		}
	}

	private void readTestDataFile(String fname){
		/* format of names file:
		 * names of categories, separated by spaces
		 * names of attributes
		 * category followed by true's and false's for each instance
		 */

		System.out.println("Reading data from file "+fname);
		try {
			Scanner din = new Scanner(new File(fname));

			testCategoryNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) testCategoryNames.add(s.next());
			numCategories=testCategoryNames.size();
			System.out.println(numCategories +" categories");

			testAttNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) testAttNames.add(s.next());
			numAtts = testAttNames.size();
			System.out.println(numAtts +" attributes");

			allTestInstances = readInstances(din);
			din.close();
		}
		catch (IOException e) {
			throw new RuntimeException("Data test File caused IO exception");
		}
	}
	private List<ReadFile.Instance> readInstances(Scanner din){
		/* instance = classname and space separated attribute values */
		List<ReadFile.Instance> instances = new ArrayList<ReadFile.Instance>();
		String ln;
		while (din.hasNext()){ 
			Scanner line = new Scanner(din.nextLine());
			instances.add(new Instance(trainingCategoryNames.indexOf(line.next()),line));
		}
		System.out.println("Read " + instances.size()+" instances");
		return instances;
	}

	public List<ReadFile.Instance> getAllTrainingInstances() {
		return allTrainingInstances;
	}

	public List<String> getTrainingAttNames() {
		return trainingAttNames;
	}
	public List<ReadFile.Instance> getAllTestInstances() {
		return allTestInstances;
	}

	public List<String> getTestAttNames() {
		return testAttNames;
	}

	class Instance {

		private int category;
		private List<Boolean> vals;

		public Instance(int cat, Scanner s){
			category = cat;
			vals = new ArrayList<Boolean>();
			while (s.hasNextBoolean()) vals.add(s.nextBoolean());
		}

		public boolean getAtt(int index){
			return vals.get(index);
		}

		public int getCategory(){
			return category;
		}

		public String toString(){
			StringBuilder ans = new StringBuilder(trainingCategoryNames.get(category));
			ans.append(" ");
			for (Boolean val : vals)
				ans.append(val?"true  ":"false ");
			return ans.toString();
		}

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadFile rf = new ReadFile();
		matched = 0;
		//System.out.print("Enter fileName(Format: xxxx.dat   yyyy.dat: ");
		// hepatitis-training.dat hepatitis-test.dat

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//
		String argu;
		String trainingAdd = null;
		String testAdd = null;
		//		try {
		//			argu = br.readLine();
		//			String[] tokens = argu.split(" ");
		//			trainingAdd = tokens[0];
		//			testAdd = tokens[1];
		//		
		//
		//
		//		} catch (IOException ioe) {
		//			System.exit(1);
		//		}
		trainingAdd = "hepatitis-training.dat";
		testAdd = "hepatitis-test.dat";
		rf.readTrainingDataFile(trainingAdd);
		rf.readTestDataFile(testAdd);
		DtAlgorithm dt = new DtAlgorithm();
		Node node = dt.buildTree(rf.getAllTrainingInstances(), rf.getTrainingAttNames());
		System.out.println(node.getAttName());
		System.out.println(node.getLeft().getAttName());
		System.out.println(node.getRight().getAttName());
		for(int i = 0; i<rf.getAllTestInstances().size(); i++){
			checkInstance(rf.getAllTestInstances().get(i),node);
		}	
		
		double accuracy = (double)matched/rf.getAllTestInstances().size();
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(2);
		System.out.println(" DT Accuracy: " +defaultFormat.format(accuracy)
					+"  Error: "+ (rf.getAllTestInstances().size() - matched)
					+"  Total: "+ rf.getAllTestInstances().size() +" test instances.");


	}

	private static void checkInstance(Instance instance, Node root) {
		//System.out.println("attr: " +root.getAttName()+ "  index: "+testAttNames.indexOf(root.getAttName()));
		if(root.getClassName()!=-1 && root.getClassName()== instance.getCategory()){
			System.out.println("check match: "+root.getClassName() + "    " +instance.getCategory());
			matched++;
		}
		if (root.getAttName()!=null && instance.getAtt(testAttNames.indexOf(root.getAttName()))&& testAttNames.indexOf(root.getAttName())>-1 ){
			checkInstance (instance, root.getLeft());
		}
		else if(root.getAttName()!=null && !instance.getAtt(testAttNames.indexOf(root.getAttName()))&& testAttNames.indexOf(root.getAttName())>-1 ){
			checkInstance (instance, root.getRight());
		}
	}

}
