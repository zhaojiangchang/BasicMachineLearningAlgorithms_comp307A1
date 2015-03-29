package part3;

public class Feature {
	private int[] row;
	private int[] col;
	private boolean[] sgn;
	private Image image = null;
	private double weight;
	private int foValue;

	public Feature(int[] row, int[] col, boolean[] sgn, Image image, double weight) {
		this.row = row;
		this.col = col;
		this.sgn = sgn;
		this.image = image;
		this.weight = weight;
	}
	
	public Feature(){
	}
	public int value(){
		if(image==null) return 1;
		else{
			int sum=0;
			for(int i=0; i < 4; i++){
				if ((image.getPixels()[row[i]][col[i]])==(sgn[i])){
					sum++;
				}
			}
			return (sum>=3)?1:0;
		}

	}
	public double getWeight() {
		return weight;
	}
	public int getValue(){
		return this.value();
	}
	
}
