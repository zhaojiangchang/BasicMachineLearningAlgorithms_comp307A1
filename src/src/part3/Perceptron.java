package part3;

import java.util.List;
import java.util.Random;
/**
 * @author JackyChang
 * ID:300282984
 *
 */
public class Perceptron {
	private List<Feature>features;
	private Image image;
	private int result = -1;
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
			i = i + (features.get(a).getValue() * weights.get(a));
		}
		if (i>0.0) result = 1;
		else result = 0;

	}

	public int getResult() {
		return result;
	}


}
