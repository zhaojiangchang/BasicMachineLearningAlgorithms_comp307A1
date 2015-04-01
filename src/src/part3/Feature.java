package part3;

import java.util.Random;

public class Feature {
	private int value = -1;
	private Random rand;
	private boolean[][]pixels;
	private int row;
	private int col;
	private int[] rows = new int[4];
	private int[] cols = new int[4];
	private boolean[] sgn = new boolean[4];
	
	public Feature(Random rand, boolean[][] pixels, int row, int col) {
		this.rand = rand;
		this.pixels = pixels;
		this.row = row;
		this.col = col;
		
		value = featureValue();
	}

	public Feature(int i) {
		this.value = i;
	}


	public int getValue(){
		return value;
	}
	public int featureValue(){
		for (int j = 0; j<4; j++){
			rows[j] = rand.nextInt(row);
			cols[j] = rand.nextInt(col);
			sgn[j] = rand.nextBoolean();
		}
		int sum=0;
		for(int i=0; i < 4; i++){
			if ((pixels[rows[i]][cols[i]])==(sgn[i])){
				sum++;
			}
		}
		return (sum>=3)?1:0;
	}
}
