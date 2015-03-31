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
	private String category = "other";
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
				category = f.next().substring(1);
				if (!category.equalsIgnoreCase("other")) categoryName=category;
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
		loadImage.setWeights();
		List<Image>images = loadImage.getImages();
		int time = 0;
		for(int i = 0; i<images.size(); i++){
			Perceptron perceptron = new Perceptron(images.get(i), loadImage.getWeights());
			
			double result = perceptron.getResults();
			System.out.println("222222222222222:  " + result);
			if(loadImage.calWeight(result,images.get(i))==-1){
				i = 0;
				time++;
				
			}
			if(time==1000){
				System.out.println("--------  1000 times!");
				break;
			}
		}
		for(Double weight: loadImage.getWeights()){
			System.out.println(weight);
		}

	}
	public int calWeight(double value, Image image){
		if((value>0 && image.getCategoryName().equalsIgnoreCase("Yes"))||(value<=0 && image.getCategoryName().equalsIgnoreCase("Other"))){


		}
		else if(value<=0 && image.getCategoryName().equalsIgnoreCase("Yes")){
			for(int a = 0; a<image.getFeatures().size(); a++){
				//System.out.println("222");
				
				weights.set(a, image.getFeatures().get(a).getValue() + weights.get(a));

			}
			return -1;

		}
		else if(value>0 && image.getCategoryName().equalsIgnoreCase("Other")){
			for(int a = 0; a<image.getFeatures().size(); a++){ 
				System.out.println("111");
				weights.set(a, image.getFeatures().get(a).getValue() - weights.get(a));

				//image.getFeatures().get(a).setWeight(image.getFeatures().get(a).getValue() - image.getFeatures().get(a).getWeight());

			}
			return -1;

		}
		return 0;

	}


}
