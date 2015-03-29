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

	public void load(String file){
		try{
			java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
			Scanner f = new Scanner(new File(file));
			while(f.hasNextLine()){
				if (!f.next().equals("P1")) System.out.println("Not a P1 PBM file" );
				category = f.next().substring(1);
				if (!category.equals("other")) categoryName=category;
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
	


	public List<Image> getImages() {
		return images;
	}



	// Main
	public static void main(String[] args){
		LoadImage loadImage = new LoadImage();
		loadImage.load("image.data");
		List<Image>images = loadImage.getImages();
		for(int i = 0; i<images.size(); i++){
			Perceptron perceptron = new Perceptron(images.get(i));
		}

	}	


}
