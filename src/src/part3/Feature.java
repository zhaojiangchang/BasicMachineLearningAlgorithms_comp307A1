package part3;

import java.util.Random;

public class Feature {
	private int[] row = new int[4];
	private int[] col= new int[4];
	private boolean[] sgn= new boolean[4];
	private boolean[][]image;
	private int value = -1;
	private int rows;
	private int cols;
	private Random rand;
	public Feature(int rows, int cols, Random rand, boolean[][]image) {
		this.rows = rows;
		this.cols = cols;
		this.rand = rand;
		this.image = image;
	}
	
	public Feature(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
	public void value(){
		for (int j = 0; j<4; j++){
			row[j] = rand.nextInt(rows);
			col[j] = rand.nextInt(cols);
			sgn[j] = rand.nextBoolean();
		}
		int sum=0;
		for(int i=0; i < 4; i++){
			if ((image[row[i]][col[i]])==(sgn[i])){
				sum++;
			}
		}
		value = (sum>=3)?1:0;
	}

}
