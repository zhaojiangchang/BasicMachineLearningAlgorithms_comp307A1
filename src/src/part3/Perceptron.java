package part3;

import java.util.List;
import java.util.Random;

public class Perceptron {
	private List<Feature>features;
	private Image image;
	private double threshold = new Random().nextDouble();
	public Perceptron(Image image) {
		this.features = image.getFeatures();
		this.image = image;
		algorithm();
	}
	public void algorithm(){
		double num = classifyCal();
		if(num!=0){
			calWeight(num);
		}
	}
	private double classifyCal() {
		double i = 0.0;
		i += features.get(0).getValue()*threshold;
		for(int a = 1; a<features.size(); a++){
			i += features.get(a).getValue()*features.get(a).getWeight();
		}
		System.out.println("aaaaaaaaaa: "+ i);
		return i;
		
	}
	public void calWeight(double value){
		int i = 100;
		while(i>0){
			if(value>0 && image.getCategoryName().equals("Yes")||value<=0 && image.getCategoryName().equals("Other")){
				System.out.println("CORRECT");

			}
			if(value<=0 && !image.getCategoryName().equals("Other")){
				System.out.println("not CORRECT");
				double weight = features.get(0).getWeight();  
				weight -= features.get(0).getValue();
				for(int a = 1; a<features.size(); a++){
					weight -= features.get(a).getValue();  
				}

			}
			if(value>0 && !image.getCategoryName().equals("Yes")){
				System.out.println("not CORRECT");
				double weight = features.get(0).getWeight();  
				weight += features.get(0).getValue();
				for(int b = 1; b<features.size(); b++){
					weight += features.get(b).getValue();  
				}

			}
			i--;
		}
	}
}
