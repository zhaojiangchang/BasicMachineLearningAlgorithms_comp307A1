package part3;

public class Feature {
	private int[] row;
	private int[] col;
	private boolean[] sgn;
	private Image image = null;
	private int value;

	public Feature(int[] row, int[] col, boolean[] sgn, Image image, int value) {
		this.row = row;
		this.col = col;
		this.sgn = sgn;
		this.image = image;
		this.value = value;
	}
	
	public Feature(){
		
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	public int getValue(){
		return this.value;
	}

	
}
