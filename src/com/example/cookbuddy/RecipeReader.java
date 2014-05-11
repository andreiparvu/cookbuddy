package com.example.cookbuddy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class RecipeReader {
	private int nrSteps;
	private ArrayList<String> ingredients, steps;
	private ArrayList<Bitmap> bitmaps;
	private String id;
	
	@SuppressLint("SdCardPath")
	public RecipeReader(String id, Context context) {
		File recipeFile = new File("/sdcard/cookbuddy/" + id + "/recipe.txt");
		
		AssetManager am = context.getAssets();
		
		this.id = id;
		try {
		  InputStream in = am.open("all/" + id);
	    
	    String json = "";
	    while (true) {
	      byte[] x = new byte[100];
	      
	      int n = in.read(x, 0, 100);
	      if (n <= 0) {
	        break;
	      }
	      json += new String(x, 0, n);
	    }
	    
	    JSONObject jsonObject = new JSONObject(json);
      JSONArray instructions = jsonObject.getJSONArray("recipe_instructions");
      JSONArray ingreds = jsonObject.getJSONArray("recipe_ingredients");
      int nrSteps = instructions.length();
			System.out.println(nrSteps);
			
			ingredients = new ArrayList<String>();
			
			for (int i = 0; i < ingreds.length(); i++) {
				ingredients.add(ingreds.getString(i));
			}
			
			//bitmaps = new ArrayList<Bitmap>();
			steps = new ArrayList<String>();
			
			for (int i = 0; i < nrSteps; i++) {
				steps.add(instructions.getString(i));
//				bitmaps.add(BitmapFactory.decodeFile("/sdcard/cookbuddy/" + id + "/step" + i + ".jpg"));
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
