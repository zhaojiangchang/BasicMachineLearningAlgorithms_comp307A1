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
		for(int i = 1; i< numFeatures-1; i++){
			int[] row = new int[4];
			int[] col = new int[4];
			boolean[] sgn = new boolean[4];
			for (int j = 0; j<4; j++){
				row[j] = rand.nextInt(rows);
				col[j] = rand.nextInt(cols);
				sgn[j] = rand.nextBoolean();
			}
			int value = featureValue(row, col, sgn);
			features.add(new Feature(value));

		}
	}
	public int featureValue(int[]row, int[]col, boolean[]sgn){
		int sum=0;
		for(int i=0; i < 4; i++){
			if ((pixels[row[i]][col[i]])==(sgn[i])){
				sum++;
			}
		}
		return (sum>=3)?1:0;
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
	


}
