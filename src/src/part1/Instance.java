/**
 * 
 */
package part1;

/**
 * @author JackyChang
 * ID:300282984
 *
 */
public class Instance {
	
	private double sepalLength;
	private double sepalWidth;
	private double petalLength;
	private double petalWidth;
	private String className;
	private String predictionClassName;
	
public Instance(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String className){
	this.sepalLength = sepalLength;
	this.sepalWidth = sepalWidth;
	this.petalLength = petalLength;
	this.petalWidth = petalLength;
	this.className = className;
}

public String getPredictionClassName() {
	return predictionClassName;
}

public void setPredictionClassName(String predictionClassName) {
	if(!predictionClassName.equals(className)){
		this.predictionClassName = predictionClassName;
	}
}

public double getSepalLength() {
	return sepalLength;
}

public void setSepalLength(double sepalLength) {
	this.sepalLength = sepalLength;
}

public double getSepalWidth() {
	return sepalWidth;
}

public void setSepalWidth(double sepalWidth) {
	this.sepalWidth = sepalWidth;
}

public double getPetalLength() {
	return petalLength;
}

public void setPetalLength(double petalLength) {
	this.petalLength = petalLength;
}

public double getPetalWidth() {
	return petalWidth;
}

public void setPetalWidth(double petalWidth) {
	this.petalWidth = petalWidth;
}

public String getClassName() {
	return className;
}

public void setClassName(String className) {
	this.className = className;
}

}
