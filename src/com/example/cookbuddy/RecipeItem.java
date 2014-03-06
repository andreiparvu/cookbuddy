package com.example.cookbuddy;

import android.graphics.Bitmap;

public class RecipeItem {
	public String title;
	public String id;
	
	public String prepTime;
	public int complexity;
	
	public String categories;
	
	public Bitmap picture;
	
	public RecipeItem() {
		
	}
	
	public RecipeItem (String title, String id, String prepTime, int complexity, String categories, Bitmap picture) {
		this.title = title;
		this.id = id;
		this.complexity = complexity;
		this.prepTime = prepTime;
		this.categories = categories;
		this.picture = picture;
	}
}
