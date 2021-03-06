/**
 *
 */
package part1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author JackyChang
 * ID:300282984
 *
 */
public class Factory {
	private String trainingSetAdd;
	private String testSetAdd;
	int k;
	private List<Instance> trainingSet;
	private List<Instance> testSet;
	private List<Double> f1;
	private List<Double> f2;
	private List<Double> f3;
	private List<Double> f4;
	private List<List<Double>> features;
	private Set<String>classLabels;
	Instance instance;

	public Factory(){
		trainingSet =  new ArrayList<Instance>();
		testSet =  new ArrayList<Instance>();
		f1 = new ArrayList<Double>();
		f2 = new ArrayList<Double>();
		f3 = new ArrayList<Double>();
		f4 = new ArrayList<Double>();
		features = new ArrayList<List<Double>>();
		classLabels = new HashSet<String>();
	}
	public void readTrainingData(){
		try{
			FileInputStream fstream = new FileInputStream(trainingSetAdd);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				String[] tokens = strLine.split("  ");

				if(tokens[0].equals("")) break;
				double sLength = Double.parseDouble(tokens[0]);
				double sWidth = Double.parseDouble(tokens[1]);
				double pLength = Double.parseDouble(tokens[2]);
				double pWidth = Double.parseDouble(tokens[3]);
				String className = tokens[4];
				f1.add(sLength);
				f2.add(sWidth);
				f3.add(pLength);
				f4.add(pWidth);
				classLabels.add(className);
				instance = new Instance(sLength,sWidth,pLength,pWidth,className);
				trainingSet.add(instance);
			}
			features.add(f1);
			features.add(f2);
			features.add(f3);
			features.add(f4);
			in.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}


	}
	public void readTestData(){

		try{
			FileInputStream fstream = new FileInputStream(testSetAdd);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				String[] tokens = strLine.split("  ");

				if(tokens[0].equals("")) return;
				double sLength = Double.parseDouble(tokens[0]);
				double sWidth = Double.parseDouble(tokens[1]);
				double pLength = Double.parseDouble(tokens[2]);
				double pWidth = Double.parseDouble(tokens[3]);
				String className = tokens[4];
				instance = new Instance(sLength,sWidth,pLength,pWidth,className);
				testSet.add(instance);

			}
			in.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}


	public String getTestSetAdd() {
		return testSetAdd;
	}
	public void setTestSetAdd(String testSetAdd) {
		this.testSetAdd = testSetAdd;
	}
	public String getTrainingSetAdd() {
		return trainingSetAdd;
	}
	public void setTrainingSetAdd(String trainingSetAdd) {
		this.trainingSetAdd = trainingSetAdd;
	}
	public void setTrainingSet(List<Instance> trainingSet) {
		this.trainingSet = trainingSet;
	}
	public List<Instance> getTrainingSet() {
		return trainingSet;
	}
	public List<Instance> getTestSet() {
		return testSet;
	}
	public List<List<Double>> getFeatures() {
		return features;
	}
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}

	public Set<String> getClassLabels() {
		return classLabels;
	}

}


