package com.example.cookbuddy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.*;


public class RecipeReader {
	private int nrSteps;
	private ArrayList<String> ingredients, steps;
	private ArrayList<Bitmap> bitmaps;
	private String id;
	
	@SuppressLint("SdCardPath")
	public RecipeReader(String id) {
		File recipeFile = new File("/sdcard/cookbuddy/" + id + "/recipe.txt");
		
		this.id = id;
		try {
			BufferedReader input = new BufferedReader(
		            new InputStreamReader(new FileInputStream(recipeFile)));
			
			nrSteps = Integer.parseInt(input.readLine());
			
			System.out.println(nrSteps);
			
			ingredients = new ArrayList<String>();
			
			for (;;) {
				String ingredient = input.readLine();
				
				if (ingredient == null) {
					break;
				}
				
				ingredients.add(ingredient);
			}
			
			input.close();
			
			bitmaps = new ArrayList<Bitmap>();
			steps = new ArrayList<String>();
			
			for (int i = 1; i <= nrSteps; i++) {
				File stepFile = new File("/sdcard/cookbuddy/" + id + "/step" + i + ".txt");
				input = new BufferedReader(
			            new InputStreamReader(new FileInputStream(stepFile)));
				
				String step = "";
				
				for (;;) {
					String line = input.readLine();
					
					if (line == null) {
						break;
					}
					
					step += line;
				}
				
				steps.add(step);
				bitmaps.add(BitmapFactory.decodeFile("/sdcard/cookbuddy/" + id + "/step" + i + ".jpg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getNrSteps() {
		return nrSteps;
	}
	
	public ArrayList<String> getIngredients() {
		return ingredients;
	}
	
	public Bitmap getStepPicture(int nrStep) {
		return bitmaps.get(nrStep - 1);
	}
	
	public String getStepText(int nrStep) {
		return steps.get(nrStep - 1);
	}
	
	@SuppressLint("SdCardPath")
	public Bitmap getPicture () {
		return BitmapFactory.decodeFile("/sdcard/cookbuddy/" + id + "/header.jpg");
	}
}
