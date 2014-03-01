package com.example.cookbuddy;
import java.io.*;
import java.util.*;
import android.annotation.SuppressLint;
import android.graphics.*;

import android.graphics.BitmapFactory;

public class MainReader {
	
	private int nrRecipes;
	private ArrayList<Recipe> recipes;
	
	final class Recipe {
		public String title, id, duration;
		public int complexity;
		public String categories;
	}
	
	@SuppressLint("SdCardPath")
	public MainReader() {
		File mainFile = new File("/sdcard/cookbuddy/main.txt");
		recipes = new ArrayList<Recipe>();
		
		try {
			BufferedReader input = new BufferedReader(
		            new InputStreamReader(new FileInputStream(mainFile)));
			
			nrRecipes = Integer.parseInt(input.readLine());
			System.out.println(nrRecipes);
			for (int i = 0; i < nrRecipes; i++) {
				Recipe curRecipe = new Recipe();
				
				curRecipe.title = input.readLine();
				curRecipe.id = input.readLine();
				curRecipe.complexity = Integer.parseInt(input.readLine());
				curRecipe.duration = input.readLine();
				curRecipe.categories = input.readLine();
				
				recipes.add(curRecipe);
			}
			
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(recipes.size());
	}
	
	public int getNrRecipes() {
		return nrRecipes;
	}
	
	public String getTitle(int nrRecipe) {
		return recipes.get(nrRecipe - 1).title;
	}
	
	public String getId(int nrRecipe) {
		return recipes.get(nrRecipe - 1).id;
	}
	
	public int getComplexity(int nrRecipe) {
		return recipes.get(nrRecipe - 1).complexity;
	}
	
	public String getDuration(int nrRecipe) {
		return recipes.get(nrRecipe - 1).duration;
	}
	
	public String getCategory(int nrRecipe) {
		return recipes.get(nrRecipe - 1).categories;
	}
	
	@SuppressLint("SdCardPath")
	public Bitmap getPicture(int nrRecipe) {
		return BitmapFactory.decodeFile("/sdcard/cookbuddy/" + getId(nrRecipe) + "/main.jpg");
	}
}
