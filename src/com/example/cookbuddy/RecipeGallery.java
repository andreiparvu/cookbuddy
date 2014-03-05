package com.example.cookbuddy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
	
	private HashMap <Integer, Pair> drawerMap;

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
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		
		
		drawerMap = new HashMap <Integer, Pair>();
		
		//Map <Integer, HashMap<String, DrawerItem>> drawerMap = new HashMap<Integer,String, DrawerItem>();
		drawerMap.put(2, new Pair("cocktail", new DrawerItem(R.drawable.section_cocktail)));
		drawerMap.put(1, new Pair("cakes", new DrawerItem(R.drawable.section_cakes)));
		drawerMap.put(3, new Pair("pasta", new DrawerItem(R.drawable.section_pasta)));
		drawerMap.put(4, new Pair("salads", new DrawerItem(R.drawable.section_salads)));
		drawerMap.put(5, new Pair("smoothies", new DrawerItem(R.drawable.section_smoothies)));
		drawerMap.put(6, new Pair("stakes", new DrawerItem(R.drawable.section_stakes)));
		drawerMap.put(0, new Pair("main", new DrawerItem(R.drawable.section_all)));
		drawerMap.put(7, new Pair("delicacies", new DrawerItem(R.drawable.section_delicacies)));
		
		drawerAdapter = new DrawerAdapter(this);

		for (int i = 0; i < drawerMap.size(); i++) {
			drawerAdapter.addItem(drawerMap.get(i).di);
		}
		
//		drawerAdapter.addItem(new DrawerItem(R.drawable.amfood));
//		drawerAdapter.addItem(new DrawerItem(R.drawable.sweets));
//		drawerAdapter.addItem(new DrawerItem(R.drawable.smoothies));
//		drawerAdapter.addItem(new DrawerItem(R.drawable.interesting));

		mDrawerList.setAdapter(drawerAdapter);

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				arg1.setSelected(true);
				Log.v("DRAWER", "position: " + arg2);
				
				
				//arg1.setAlpha(0.3f);
				
				if (arg2 == 0) {
					populateFields("main");
				} else {
					Pair p = drawerMap.get(arg2);
					populateFields(p.category);
				}

				/*
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
				*/
				
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
				Log.v("TAG", "position: " + position + " => " + mr.getId(position + 1));

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
	
	public class Pair {
		String category;
		DrawerItem di;
		
		public Pair (String category, DrawerItem di) {
			this.category = category;
			this.di = di;
		}
	}

}
