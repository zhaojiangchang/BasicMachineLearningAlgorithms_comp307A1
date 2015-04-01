package part3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Image {
	private String categoryName;
	private boolean[][] pixels;
	private int rows;
	private int cols;
	private int numFeatures = 50;
	private List<Feature> features;
	private Random rand;

	public Image(String categoryName, boolean[][] pixels, int rows, int cols, Random rand) {
		this.categoryName = categoryName;
		this.pixels = pixels;
		this.rows = rows;
		this.cols = cols;
		this.rand = rand;
		features= new ArrayList<Feature>();
		createFeatures();

	}

	public void createFeatures(){
		Feature f = new Feature(1);
		features.add(f);
		for(int i = 1; i< numFeatures; i++){
			features.add(new Feature(rows, cols, rand, pixels));
		}
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public boolean[][] getPixels() {
		return pixels;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public Random getRand() {
		return rand;
	}
	


}
