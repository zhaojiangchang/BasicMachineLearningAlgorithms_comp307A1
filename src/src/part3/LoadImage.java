package part3;
import java.util.*;
import java.io.*;
/**
 * @author JackyChang
 * ID:300282984
 *
 */
public class LoadImage{
	// Fields
	private int rows = 10;
	private int cols = 10;
	private String categoryName;
	private List<Image>images = new ArrayList<Image>();
	private List<Double>weights = new ArrayList<Double>();
	private boolean weightsUpdated = false;
	private Random rand = new Random(845);


	public LoadImage(){
	}

	public void load(String file){
		try{
			java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
			Scanner f = new Scanner(new File(file));
			while(f.hasNextLine()){
				if (!f.next().equalsIgnoreCase("P1"))
					System.out.println("Not a P1 PBM file" );
				categoryName = f.next().substring(1);
				rows = f.nextInt();
				cols = f.nextInt();
				boolean[][]pixels = new boolean[rows][cols];
				for (int r=0; r<rows; r++){
					for (int c=0; c<cols; c++){
						int num = Integer.parseInt(f.findWithinHorizon(bit,0));
						pixels[r][c] = (num == 1)? true:false;

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

			}else
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

}
