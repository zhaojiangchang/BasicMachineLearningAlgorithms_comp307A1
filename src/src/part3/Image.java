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

	public Image(String categoryName, boolean[][] pixels, int rows, int cols) {
		this.categoryName = categoryName;
		this.pixels = pixels;
		this.rows = rows;
		this.cols = cols;		
		features= new ArrayList<Feature>();
		features();
	}

	public List<Feature> features(){
		features.add(new Feature());
		for(int i = 0; i< numFeatures-1; i++){
			int[] row = new int[4];
			int[] col = new int[4];
			boolean[] sgn = new boolean[4];
			for (int j = 0; j<4; j++){
				row[j] = new Random().nextInt(rows);
				col[j] = new Random().nextInt(cols);
				sgn[j] = new Random().nextBoolean();
			}
			double weight = 0;
			while (weight==0 ||weight==1){
				weight = new Random().nextDouble();
			}
			features.add(new Feature(row, col, sgn, this, weight));
		}
		return features;
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
