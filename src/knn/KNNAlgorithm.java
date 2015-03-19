package knn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class KNNAlgorithm {
	private List<Instance> trainingSet;
	private List<Instance> testSet;
	private List<List<Double>> features;
	private Set<String> classLabels;
	private int k;
	private double range1;
	private double range2;
	private double range3;
	private double range4;


	public KNNAlgorithm(List<Instance> trainingSet, List<Instance> testSet, int k, List<List<Double>>features, Set<String>classLabels){
		this.trainingSet = trainingSet;
		this.testSet = testSet;
		this.k = k;
		this.features = features;
		this.classLabels = classLabels;
		getRanges();
	}

	public void getRanges(){
		double f1Max = Collections.max(features.get(0));
		double f1Min = Collections.min(features.get(0));
		double f2Max = Collections.max(features.get(1));
		double f2Min = Collections.min(features.get(1));
		double f3Max = Collections.max(features.get(2));
		double f3Min = Collections.min(features.get(2));
		double f4Max = Collections.max(features.get(3));
		double f4Min = Collections.min(features.get(3));
		range1 = f1Max - f1Min;
		range2 = f2Max - f2Min;
		range3 = f3Max - f3Min;
		range4 = f4Max - f4Min;
	}

	public double dElement(double trainingSetValue, double testSetValue,double range){
		return (Math.pow((trainingSetValue-testSetValue), 2))/(Math.pow(range, 2));
	}

	public void orderedDistanceList(){
		double d = -1;
		for(int j = 0; j<testSet.size(); j++){
			Map<Integer, Double> distances = new HashMap<Integer, Double>();

			for(int i = 0; i<trainingSet.size(); i++){
				d = Math.sqrt(dElement(trainingSet.get(i).getSepalLength(),testSet.get(j).getSepalLength(),range1)
						+ dElement(trainingSet.get(i).getSepalWidth(),testSet.get(j).getSepalWidth(),range2)
						+ dElement(trainingSet.get(i).getPetalLength(),testSet.get(j).getPetalLength(),range3)
						+ dElement(trainingSet.get(i).getPetalWidth(),testSet.get(j).getPetalWidth(),range4));

				distances.put(i,d);
			}
			TreeMap<Integer,Double> sortedMap = sortByValue(distances);
			List<String>classNames = new ArrayList<String>();
			int c = 0;
			for ( Map.Entry<Integer, Double> entry : sortedMap.entrySet()) {
				if(c>k-1)break;
			    int index= entry.getKey();
			    double value = entry.getValue();
			    classNames.add(trainingSet.get(index).getClassName());
			    c++;
			}
			List<Integer>size = new ArrayList<Integer>(); 
			for(String label: classLabels){
				int i = 0;
				for(String name: classNames){
					if(label.equals(name)){
						i++;
					}
				}
				size.add(i);
			}
			int max = -1;
			int maxIndex = -1;
			for(int e = 0; e<size.size(); e++){
				if(size.get(e)>max) {
					max = size.get(e);
					maxIndex = e;
				}
			}
			if(maxIndex!=-1){
				testSet.get(j).setPredictionClassName((String)classLabels.toArray()[maxIndex]);

			}
			
		}
	}
	private TreeMap<Integer, Double> sortByValue(Map<Integer, Double> distances) {
		TreeMap<Integer, Double> sortedMap = new TreeMap<Integer, Double>(new ValueComparator(distances));
		sortedMap.putAll(distances);
		return sortedMap;
	}

}
class ValueComparator implements Comparator<Integer> {

	Map<Integer, Double> map;

	public ValueComparator(Map<Integer, Double> distances) {
		this.map = distances;
	}
	public int compare(Integer arg0, Integer arg1) {
		// TODO Auto-generated method stub
		double valueA =  map.get(arg0);
		double valueB =  map.get(arg1);
		if(valueA<valueB)return -1;
		else if(valueA==valueB) return 0;
		else return 1;	
		}


}