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
		setContentView(R.layout.activity_recipe_gallery);

		// ActionBar actionBar = getActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(true);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		/*
		 * mDrawerToggle = new ActionBarDrawerToggle(this, // host Activity
		 * mDrawerLayout, // DrawerLayout object R.drawable.ic_drawer, //nav
		 * drawer image to replace 'Up' caret R.string.drawer_open, //
		 * "open drawer" description for accessibility R.string.drawer_close //
		 * "close drawer" description for accessibility ) { public void
		 * onDrawerClosed(View view) { getActionBar().setTitle("Cook Buddy");
		 * invalidateOptionsMenu(); // creates call to // onPrepareOptionsMenu()
		 * }
		 * 
		 * public void onDrawerOpened(View drawerView) {
		 * getActionBar().setTitle("Cook Buddy"); invalidateOptionsMenu(); //
		 * creates call to // onPrepareOptionsMenu() } };
		 */
		//mDrawerLayout.setDrawerListener(mDrawerToggle);

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

		// System.out.println("NR recipes: " + nrRecipes);

		populateFields("main");
		/*
		for (int i = 0; i < nrRecipes; i++) {
			RecipeItem item = new RecipeItem();
			item.title = mr.getTitle(i + 1);
			item.complexity = mr.getComplexity(i + 1);
			item.prepTime = mr.getDuration(i + 1);
			item.picture = mr.getPicture(i + 1);
			adapter.addItem(item);
		}
		*/

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
				intent.putExtra("recipeId", mr.getId(position + 1));
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
				RecipeItem item = new RecipeItem();
				item.title = mr.getTitle(i + 1);
				item.complexity = mr.getComplexity(i + 1);
				item.prepTime = mr.getDuration(i + 1);
				item.picture = mr.getPicture(i + 1);
				adapter.addItem(item);
			}
		}
		if (categories.equals("smoothies")) {
			for (int i = 0; i < nrRecipes; i++) {
				if (mr.getCategory(i+1).equals("smoothies")) {
					RecipeItem item = new RecipeItem();
					item.title = mr.getTitle(i + 1);
					item.complexity = mr.getComplexity(i + 1);
					item.prepTime = mr.getDuration(i + 1);
					item.picture = mr.getPicture(i + 1);
					adapter.addItem(item);
				}
			}
		}
		if (categories.equals("amfood")) {
			for (int i = 0; i < nrRecipes; i++) {
				if (mr.getCategory(i+1).equals("amfood")) {
					RecipeItem item = new RecipeItem();
					item.title = mr.getTitle(i + 1);
					item.complexity = mr.getComplexity(i + 1);
					item.prepTime = mr.getDuration(i + 1);
					item.picture = mr.getPicture(i + 1);
					adapter.addItem(item);
				}
			}
		}
		if (categories.equals("sweets")) {
			for (int i = 0; i < nrRecipes; i++) {
				if (mr.getCategory(i+1).equals("sweets")) {
					RecipeItem item = new RecipeItem();
					item.title = mr.getTitle(i + 1);
					item.complexity = mr.getComplexity(i + 1);
					item.prepTime = mr.getDuration(i + 1);
					item.picture = mr.getPicture(i + 1);
					adapter.addItem(item);
				}
			}
		}
	}

}
