package com.example.cookbuddy;

import android.graphics.Bitmap;

public class RecipeItem {

	public String title;
	public String prepTime;
	public int complexity;
	
	public Bitmap picture;
	
	public RecipeItem() {
		
	}
	
	public RecipeItem (String title, String prepTime, int complexity, Bitmap picture) {
		this.title = title;
		this.complexity = complexity;
		this.prepTime = prepTime;
		this.picture = picture;
	}
}
