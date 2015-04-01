package part3;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.io.*;


/** Simple editor to construct and edit pmb image files.
    Not very robust!!!
 */

public class LoadImage{
	// Fields

	private static int margin = 10;
	private static int wd = 20;

	private int rows = 10;
	private int cols = 10;
	private String category;
	private String categoryName;
	private int otherCount=0;
	private int categoryCount=0;
	private List<Image>images = new ArrayList<Image>();
	private List<Double>weights = new ArrayList<Double>();


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
				Image image = new Image(categoryName, pixels, rows, cols);

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
				weight = new Random().nextDouble();
			}
			if(i==0){
				weight = -weight;
				weights.add(weight);
			}
			weights.add(weight);
		}
	}
	public List<Image> getImages() {
		return images;
	}

	public List<Double> getWeights() {
		return weights;
	}
	// Main
	public static void main(String[] args){
		LoadImage loadImage = new LoadImage();
		loadImage.load("image.data");
		List<Image>images = loadImage.getImages();
		loadImage.setWeights();
		int time = 0;
		for(int j = 0; j<1000; j++){

		for(int i = 0; i<images.size(); i++){

			Perceptron perceptron = new Perceptron(images.get(i), loadImage.getWeights());
			int value = perceptron.getResult();

			if((value==1 && images.get(i).getCategoryName().equalsIgnoreCase("Yes"))||(value==0 && images.get(i).getCategoryName().equalsIgnoreCase("Other"))){
				//System.out.println(value+"   "+ images.get(i).getCategoryName());

			}
			else if(value==0 && images.get(i).getCategoryName().equalsIgnoreCase("yes")){
				//System.out.println("value == 1	+	"+value+"   "+ images.get(i).getCategoryName());

				for(int a = 1; a<images.get(i).getFeatures().size(); a++){

					loadImage.getWeights().set(a, loadImage.getWeights().get(a) - images.get(i).getFeatures().get(a).getValue());
				}
				//

			}
			else if(value==1 && images.get(i).getCategoryName().equalsIgnoreCase("other")){
				//System.out.println("value == 1	-	"+value+"   "+ images.get(i).getCategoryName());

				for(int a = 1; a<images.get(i).getFeatures().size(); a++){
					loadImage.getWeights().set(a, loadImage.getWeights().get(a)+images.get(i).getFeatures().get(a).getValue());

				}
				//i = 0;

			}
			//if(time==1000){
				//System.out.println("--------  1000 times!");
				//break;
			//}
		}
		
		time++;
		}
		System.out.println("--------  -------------------"+ time);

		//						for(Double weight: loadImage.getWeights()){
		//							System.out.println(weight);
		//						}




	}
}
