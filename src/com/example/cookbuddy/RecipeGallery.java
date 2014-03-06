package com.example.cookbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecipeGallery extends Activity {

	private RecipeItemAdapter adapter;

	private Context context;

	private DrawerAdapter drawerAdapter;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private int nrRecipes;
	private MainReader mr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d("mydebug", "pornit-am");
		
		setContentView(R.layout.activity_recipe_gallery);


		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		drawerAdapter = new DrawerAdapter(this);

		drawerAdapter.addItem(new DrawerItem(R.drawable.amfood));
		drawerAdapter.addItem(new DrawerItem(R.drawable.sweets));
		drawerAdapter.addItem(new DrawerItem(R.drawable.smoothies));
		drawerAdapter.addItem(new DrawerItem(R.drawable.interesting));

		mDrawerList.setAdapter(drawerAdapter);

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.v("DRAWER", "position: " + arg2);
				if (arg2 == 0) {
					populateFields("amfood");
				}
				if (arg2 == 1) {
					populateFields("sweets");
				}
				if (arg2 == 2) {
					populateFields("smoothies");
				}
				if (arg2 == 3) {
					populateFields("main");
				}
				
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		});

		ListView lv = (ListView) findViewById(R.id.recipes_list);
		adapter = new RecipeItemAdapter(this);

		mr = new MainReader();
		nrRecipes = mr.getNrRecipes();

		Log.d("mydebug", "uite " + nrRecipes);
		
		populateFields("main");

		lv.setAdapter(adapter);

		context = this;

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Log.v("TAG", "position: " + position);

				Intent intent = new Intent();
				intent.setClass(context, FirstPageRecipe.class);
				intent.putExtra("recipeId", mr.getId(position));
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipe_gallery, menu);
		return true;
	}

	public void populateFields(String categories) {
		adapter.clear();
		if (categories.equals("main")) {
			for (int i = 0; i < nrRecipes; i++) {
				adapter.addItem(mr.getItem(i));
			}
		}
		if (categories.equals("smoothies")) {
			for (int i = 0; i < nrRecipes; i++) {
				if (mr.getCategory(i).equals("smoothies")) {
					adapter.addItem(mr.getItem(i));
				}
			}
		}
		if (categories.equals("amfood")) {
			for (int i = 0; i < nrRecipes; i++) {
				if (mr.getCategory(i).equals("amfood")) {
					adapter.addItem(mr.getItem(i));
				}
			}
		}
		if (categories.equals("sweets")) {
			for (int i = 0; i < nrRecipes; i++) {
				if (mr.getCategory(i).equals("sweets")) {
					adapter.addItem(mr.getItem(i));
				}
			}
		}
	}

}
