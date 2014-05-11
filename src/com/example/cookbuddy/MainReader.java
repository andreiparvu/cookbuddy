package com.example.cookbuddy;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

public class MainReader {
	
	private int nrRecipes;
	private ArrayList<Recipe> recipes;
	
	final class Recipe {
		public String title, id, duration;
		public int complexity;
		public String categories;
		public String json;
	}
	
	AssetManager am;
	
	@SuppressLint("SdCardPath")
	public MainReader(Context context) {
//		File mainFile = new File("/sdcard/cookbuddy/main.txt");
		recipes = new ArrayList<Recipe>();
		
		/*try {
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
		}*/
	  am = context.getAssets();
	  try {
	    
  	  String[] s = am.list("all");
  	  Log.d("mydebug", s[0]);

  	  nrRecipes = 1;//s.length;
  	  for (int i = 0; i < nrRecipes; i++) {
  	    Recipe curRecipe = new Recipe();
  	    
  	    curRecipe.title = s[i];
  	    curRecipe.id = s[i];
  	    
  	    InputStream in = am.open("all/" + s[i]);
  	    
  	    curRecipe.json = "";
  	    while (true) {
  	      byte[] x = new byte[100];
  	      
  	      int n = in.read(x, 0, 100);
  	      if (n <= 0) {
            break;
          }
  	      curRecipe.json += new String(x, 0, n);
  	    }
  	    recipes.add(curRecipe);
  	  }
	  } catch (Exception ex) {
	    ex.printStackTrace();
	  }
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
	  try {
	    
    JSONObject jsonObject = new JSONObject(recipes.get(nrRecipe - 1).json);
    JSONArray newJSON = jsonObject.getJSONArray("images");
    JSONObject o1 = newJSON.getJSONObject(0);
    String url = o1.getString("url");
    
    Log.d("mydebug", url);
    
//		return BitmapFactory.decodeFile("/sdcard/cookbuddy/" + getId(nrRecipe) + "/main.jpg");
	  } catch (Exception ex) {
	    ex.printStackTrace();
	  }
	  return null;
	}
}
