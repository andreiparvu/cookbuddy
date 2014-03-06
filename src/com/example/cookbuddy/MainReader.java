package com.example.cookbuddy;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MainReader {
	
	private ArrayList<RecipeItem> items = new ArrayList<RecipeItem>();
	
	private class ConnectThread extends Thread {
		public void run() {
			try {
				Log.println(Log.DEBUG, "mydebug", "facem conexiunea");
				
				Socket s = new Socket(Connection.IP, Connection.PORT);
		
				Log.println(Log.DEBUG, "mydebug", "s-a facut conexiunea");
			    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			    ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			    
			    out.writeObject(new DataRequest(DataRequest.MAIN_RECIPES));
			    out.flush();
			    
			    for (ShortRecipe recipe : (ArrayList<ShortRecipe>)in.readObject()) {
			    	ByteArrayInputStream imageStream = new ByteArrayInputStream(recipe.picture);
			    	
			    	items.add(new RecipeItem(
			    			recipe.title,
			    			recipe.id,
			    			recipe.duration,
			    			recipe.complexity,
			    			recipe.categories,
			    			BitmapFactory.decodeStream(imageStream))
			    	);
			    }
			    Log.d("mydebug", "ok");
			    s.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
	}
	
	public MainReader() {
		try {
			ConnectThread t = new ConnectThread();
			
			t.start();
			
			t.join();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public int getNrRecipes() {
		return items.size();
	}
	
	public RecipeItem getItem(int nrRecipe) {
		return items.get(nrRecipe);
	}
	
	public String getId(int nrRecipe) {
		return items.get(nrRecipe).id;
	}

	public String getCategory(int nrRecipe) {
		return items.get(nrRecipe).categories;
	}
}
