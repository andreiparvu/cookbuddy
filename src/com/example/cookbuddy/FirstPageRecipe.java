package com.example.cookbuddy;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstPageRecipe extends Activity {

	private Activity context;
	private String recipeId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_page_recipe);
		context = this;
		
		Intent activator = getIntent();
		recipeId = activator.getStringExtra("recipeId");
		
		ImageView img = (ImageView)findViewById(R.id.image_recipe);
		
		RecipeReader rr = new RecipeReader(recipeId, getApplicationContext());
		img.setImageBitmap(rr.getPicture());
		
		ArrayList<String> ingredientsArray = rr.getIngredients();
		String ingredients = "Ingredients: \n";
		for (int i = 0; i < ingredientsArray.size(); i++) {
			ingredients += "\n \t- " + ingredientsArray.get(i);
		}
		
		TextView ingredientsTextView = (TextView)findViewById(R.id.ingredients);
		Typeface externalFont = Typeface.createFromAsset(context.getAssets(), "fonts/lane_narrow.ttf");
		ingredientsTextView.setTypeface(externalFont);
		ingredientsTextView.setTextColor(Color.parseColor("#12418C"));
//		ingredientsTextView.setTypeface(null, Typeface.BOLD);
		ingredientsTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 18);
		ingredientsTextView.setText(ingredients);
		
//		Button backBtn = (Button)findViewById(R.id.back_button);
//		backBtn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				finish();
//			}
//		});
		
		Button startBtn = (Button)findViewById(R.id.start_cooking_button);
		startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent startCooking = new Intent();
				startCooking.putExtra("recipeId", recipeId);
				startCooking.setClass(context, StepActivity.class);
				startActivity(startCooking);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_page_recipe, menu);
		return true;
	}

}
