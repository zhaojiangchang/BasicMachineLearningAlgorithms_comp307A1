package part3;

import java.util.List;
import java.util.Random;

public class Perceptron {
	private List<Feature>features;
	private Image image;
	private double results = 0;
	private List<Double>weights;
	public Perceptron(Image image, List<Double>weights) {
		this.features = image.getFeatures();
		this.image = image;
		this.weights = weights;
		classifyCal();
	}

	private void classifyCal() {
		double i = 0.0;
		for(int a = 0; a<features.size(); a++){
			i += features.get(a).getValue()*weights.get(a);
		}
		System.out.println("results: "+i);
		results = i;

	}

	public double getResults() {
		return results;
	}
	

}
