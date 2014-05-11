package com.example.cookbuddy;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeItemAdapter extends BaseAdapter {

	private Activity context; 
	private ArrayList<RecipeItem> recipeItems;
	
	public RecipeItemAdapter(Activity context) {
		this.context = context;
		recipeItems = new ArrayList<RecipeItem>();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return recipeItems.size();

	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub

		return recipeItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}


	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.item_recipe_main, null);
		}
		
//		TextView title = (TextView)view.findViewById(R.id.item_title);
//		TextView complexity = (TextView)view.findViewById(R.id.complexity);
//		
//		Typeface externalFont = Typeface.createFromAsset(context.getAssets(), "fonts/century.ttf");
//		title.setTypeface(externalFont);
//		
//		title.setTextSize(TypedValue.COMPLEX_UNIT_PT, 10);
//		
//		title.setText(recipeItems.get(position).title);
//		
//		if (recipeItems.get(position).complexity < 4) {
//			complexity.setTextColor(Color.rgb(34, 139, 34));
//			complexity.setText("LOW");
//		}
//		else if (recipeItems.get(position).complexity < 8) {
//			complexity.setTextColor(Color.rgb(255, 165, 0));
//			complexity.setText("AVERAGE");
//		}
//		else {
//			complexity.setTextColor(Color.RED);
//			complexity.setText("HIGH");
//		}
//		
//		TextView prepTime = (TextView)view.findViewById(R.id.prep_time);
//		prepTime.setText(recipeItems.get(position).prepTime);
//		
		ImageView img = (ImageView)view.findViewById(R.id.item_img);
		img.setImageBitmap(recipeItems.get(position).picture);
		
		return view;
	}
	
	public void addItem(RecipeItem newItem) {
		recipeItems.add(newItem);
		notifyDataSetChanged();
	}
	
	public void clear() {
		recipeItems.clear();
		notifyDataSetChanged();
	}

}
