package part2;

import java.text.NumberFormat;
import java.util.*;
import java.io.*;
/**
 * @author JackyChang
 * ID:300282984
 *
 */
public class ReadFile{
	// some bits of java code that you may use if you wish.
	// assumes that the enclosing class has fields:
	int numCategories;
	int numAtts;
	private List<String> trainingCategoryNames;
	private List<String> trainingAttNames;
	private List<ReadFile.Instance> allTrainingInstances;
	private List<String> testCategoryNames;
	private List<String> testAttNames;
	private static DTLeaf DTLeaf;
	private List<ReadFile.Instance> allTestInstances;
	private int numCorrectLive;
	private int matched = 0;

	public ReadFile(){

	}

	public int getMatched() {
		return matched;
	}

	public void setMatched(int matched) {
		this.matched = matched;
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
			//System.out.println(numCategories +" categories");

			trainingAttNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) trainingAttNames.add(s.next());
			numAtts = trainingAttNames.size();
			//System.out.println(numAtts +" attributes");

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

			testAttNames = new ArrayList<String>();
			for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) testAttNames.add(s.next());
			numAtts = testAttNames.size();

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
		private int predicted = -1;

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

		public int getPredicted() {
			return predicted;
		}

		public void setPredicted(int predicted) {
			this.predicted = predicted;
		}

	}



	public void runTrails(ReadFile rf){
		String[]index = {"01", "02", "03", "04", "05","06","07","08","09","10"};
		List<Double>accuracys = new ArrayList<Double>();
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(2);
		for(int add = 0; add<10; add++){
			matched = 0;

			rf.readTrainingDataFile("data/hepatitis-training-run"+index[add]+".dat");
			rf.readTestDataFile("data/hepatitis-test-run"+index[add]+".dat");
			DtAlgorithm dt = new DtAlgorithm(rf.getAllTrainingInstances(), rf.getTrainingAttNames());
			Node node = dt.buildTree(rf.getAllTrainingInstances(), rf.getTrainingAttNames());
			double accuracy = rf.process(rf, node);

			System.out.println("----------------------------------------------------");
			accuracys.add(accuracy);
		}
		double total = 0;
		for(double accur: accuracys){
			total +=accur;
		}
		double average =  (double)total/accuracys.size();
		System.out.println("----------------------------------------------------");
		System.out.println("average accuracy of the classifiers over the 10 trials: "+defaultFormat.format(average));
	}

	public double process(ReadFile rf, Node node){
		for(int i = 0; i<rf.getAllTestInstances().size(); i++){
			rf.checkTestInstance(rf.getAllTestInstances().get(i),node);
		}

		double accuracy = (double)matched/rf.getAllTestInstances().size();
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(2);
		int numCorrectLive = 0;
		int numCorrectDie = 0;
		int numTestInsLive = 0;
		int numTestInsDie = 0;

		for(int j = 0; j<rf.getAllTestInstances().size(); j++){

			if(rf.getAllTestInstances().get(j).getCategory()==0){
				numTestInsLive++;
			}
			else if(rf.getAllTestInstances().get(j).getCategory()==1){
				numTestInsDie++;
			}

			if(rf.getAllTestInstances().get(j).getPredicted()==rf.getAllTestInstances().get(j).getCategory()
					&& rf.getAllTestInstances().get(j).getCategory()==0)
				numCorrectLive++;
			else if(rf.getAllTestInstances().get(j).getPredicted()==rf.getAllTestInstances().get(j).getCategory()
					&& rf.getAllTestInstances().get(j).getCategory()==1)
				numCorrectDie++;
		}
		matched = 0;

		System.out.println("Live: " + numCorrectLive + "  correct out of " + numTestInsLive);
		System.out.println("Die: " + numCorrectDie + "  correct out of " + numTestInsDie);
		System.out.println("Accuracy: ");
		System.out.println("Decision Tree Accuracy: " +defaultFormat.format(accuracy));
		System.out.println("Baseline Accuracy (live): "+ defaultFormat.format((double)numTestInsLive/rf.getAllTestInstances().size()));
		System.out.println("Decision Tree constructed: ");
		return accuracy;
	}

	private void checkTestInstance(Instance instance, Node root) {

		if(root instanceof DTLeaf && root.getClassName()== instance.getCategory()){
			instance.setPredicted(root.getClassName());
			matched++;
		}
		if(root instanceof DTNode){
			for(int i = 0; i<testAttNames.size(); i++){
				if(testAttNames.get(i).equals(root.getAttName())){
					if(instance.getAtt(i)){
						checkTestInstance (instance, root.getLeft());
					}else{
						checkTestInstance (instance, root.getRight());

					}
				}
			}
		}
	}

	public void runTrainingTestData(ReadFile rf, String trainingAdd, String testAdd) {
		rf.readTrainingDataFile(trainingAdd);
		rf.readTestDataFile(testAdd);
		DtAlgorithm dt = new DtAlgorithm(rf.getAllTrainingInstances(), rf.getTrainingAttNames());
		Node node = dt.buildTree(rf.getAllTrainingInstances(), rf.getTrainingAttNames());
		rf.process(rf, node);
		node.report("\t");

	}
}


