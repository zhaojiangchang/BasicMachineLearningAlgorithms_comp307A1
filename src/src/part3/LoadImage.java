package part3;
import java.util.*;
import java.io.*;


/** Simple editor to construct and edit pmb image files.
    Not very robust!!!
 */

public class LoadImage{
	// Fields
	private int rows = 10;
	private int cols = 10;
	private String categoryName;
	private List<Image>images = new ArrayList<Image>();
	private List<Double>weights = new ArrayList<Double>();
	boolean weightsUpdated = false;
	private Random rand = new Random(1637);

	public LoadImage(){
	}

	public void load(String file){
		try{
			java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
			Scanner f = new Scanner(new File(file));
			while(f.hasNextLine()){
				if (!f.next().equalsIgnoreCase("P1")) System.out.println("Not a P1 PBM file" );
				categoryName = f.next().substring(1);
				rows = f.nextInt();
				cols = f.nextInt();
				boolean[][]pixels = new boolean[rows][cols];
				for (int r=0; r<rows; r++){
					for (int c=0; c<cols; c++){
						int num = Integer.parseInt(f.findWithinHorizon(bit,0));
						pixels[r][c] = (num == 1)? true:false;
						//	pixels[r][c] = f.next();

					}
				}
				Image image = new Image(categoryName, pixels, rows, cols, rand);

				images.add(image);
			}
			f.close();
		}
		catch(IOException e){System.out.println("Load from file failed"); }
	}
	public void setWeights(){
		for(int i = 0; i<images.get(0).getFeatures().size();i++){
			double weight = 0;
			while (weight==0 ||weight==1){
				weight =rand.nextDouble();
			}
			if(i==0){
				weight = -weight;
				weights.add(weight);
			}
			else
				weights.add(weight);
		}
	}
	public List<Image> getImages() {
		return images;
	}

	public List<Double> getWeights() {
		return weights;
	}


	public boolean isWeightsUpdated() {
		return weightsUpdated;
	}
	public void setWeightsUpdated(boolean weightsUpdated) {
		this.weightsUpdated = weightsUpdated;
	}
	// Main
	public static void main(String[] args){

		LoadImage loadImage = new LoadImage();
		loadImage.load("image.data");
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
		//}
		for(Double weight: loadImage.getWeights()){
			System.out.println(weight);
		}



	}
}
