package part1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
/**
 * @author JackyChang
 * ID:300282984
 *
 */
public class Main {

	public static void main(String[] args) {
		Factory factory = new Factory();
		System.out.print("Enter fileName(Format: iris-training.txt iris-test.txt k-value): ");

		//  open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String argu;
		try {
			argu = br.readLine();
			String[] tokens = argu.split(" ");
			factory.setTrainingSetAdd("data/"+tokens[0]);
			factory.setTestSetAdd("data/"+tokens[1]);
			if(!tokens[2].equals("") && Integer.parseInt(tokens[2])>0){
				factory.setK(Integer.parseInt(tokens[2]));
			}

		} catch (IOException ioe) {
			System.exit(1);
		}
		factory.readTrainingData();
		factory.readTestData();
		KNNAlgorithm knn = new KNNAlgorithm(factory.getTrainingSet(),factory.getTestSet(),factory.getK(),factory.getFeatures(), factory.getClassLabels());
		knn.orderedDistanceList();
		int totalTestSetSize = factory.getTestSet().size();
		for(int i = 0; i<factory.getTestSet().size();i++){
			if(factory.getTestSet().get(i).getPredictionClassName()!=null){
				totalTestSetSize--;
			}
		}
		double accuracy = (double)totalTestSetSize/factory.getTestSet().size();
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(2);
		System.out.println("K= "+ factory.getK() + "  Accuracy: " +defaultFormat.format(accuracy)
					+"  Error: "+ (factory.getTestSet().size()-totalTestSetSize)
					+"  Total: "+ factory.getTestSet().size()+" instances.");

	}

}
