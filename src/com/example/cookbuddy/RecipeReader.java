package com.example.cookbuddy;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.graphics.*;
import android.util.Log;


public class RecipeReader {
	private CompleteRecipe recipe;
	
	private class ConnectThread extends Thread {
		private String id;
		
		public ConnectThread(String id) {
			this.id = id;
		}
		
		public void run() {
			try {
				Log.println(Log.DEBUG, "mydebug", "facem conexiunea");
				
				Socket s = new Socket(Connection.IP, Connection.PORT);
		
				Log.println(Log.DEBUG, "mydebug", "s-a facut conexiunea 2 ");
			    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			    ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			    
			    DataRequest request = new DataRequest(DataRequest.RECIPE);
			    request.id = id;
			    
			    out.writeObject(request);
			    out.flush();
			    
			    recipe = (CompleteRecipe)in.readObject();
			    
			    Log.d("mydebug", recipe.id + " " + recipe.nrSteps);
			    s.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
	}
	
	public RecipeReader(String id) {
		try {
			ConnectThread t = new ConnectThread(id);
			t.start();
			
			t.join();
		} catch (Exception ex) {}
	}
	
	public int getNrSteps() {
		return recipe.nrSteps;
	}
	
	public ArrayList<String> getIngredients() {
		return recipe.ingredients;
	}
	
	public Bitmap getStepPicture(int nrStep) {
		ByteArrayInputStream imageStream = new ByteArrayInputStream(recipe.pictures.get(nrStep - 1));
    	return BitmapFactory.decodeStream(imageStream);
	}
	
	public String getStepText(int nrStep) {
		return recipe.steps.get(nrStep - 1);
	}
	
	public Bitmap getPicture () {
		ByteArrayInputStream imageStream = new ByteArrayInputStream(recipe.mainPicture);
    	return BitmapFactory.decodeStream(imageStream);
	}
}
