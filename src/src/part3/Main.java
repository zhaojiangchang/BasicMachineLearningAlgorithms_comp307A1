package part3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import part2.ReadFile;
/**
 * @author JackyChang
 * ID:300282984
 *
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Enter fileName: ");

				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
				String argu;
				String fileName = null;
						try {
							argu = br.readLine();
							String[] tokens = argu.split(" ");
							fileName = "data/"+tokens[0];						
						} catch (IOException ioe) {
							System.exit(1);
						}
	
		LoadImage loadImage = new LoadImage();
		loadImage.load(fileName);
		List<Image>images = loadImage.getImages();
		loadImage.setWeights();
		int time = 0;
		for(int j = 0; j<1000; j++){
			loadImage.setWeightsUpdated(false);

			for(int i = 0; i<images.size(); i++){

				Perceptron perceptron = new Perceptron(images.get(i), loadImage.getWeights());
				int value = perceptron.getResult();

				if((value==1 && images.get(i).getCategoryName().equalsIgnoreCase("Yes"))||(value==0 && images.get(i).getCategoryName().equalsIgnoreCase("Other"))){
				}
				else if(value==0 && images.get(i).getCategoryName().equalsIgnoreCase("yes")){
					for(int a = 0; a<images.get(i).getFeatures().size(); a++){
						loadImage.setWeightsUpdated(true);
						loadImage.getWeights().set(a, loadImage.getWeights().get(a) + images.get(i).getFeatures().get(a).getValue());
					}
				}
				else if(value==1 && images.get(i).getCategoryName().equalsIgnoreCase("other")){
					loadImage.setWeightsUpdated(true);
					for(int a = 0; a<images.get(i).getFeatures().size(); a++){
						loadImage.getWeights().set(a, loadImage.getWeights().get(a) - images.get(i).getFeatures().get(a).getValue());
					}
				}
			}
			if(!loadImage.isWeightsUpdated()) break;
			time++;

		}
		System.out.println("Runs: "+ time+" times");
		System.out.println("Weights after corrected: ");
		for(Double weight: loadImage.getWeights()){
			System.out.println(weight);
		}

	}

}
