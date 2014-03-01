package com.example.cookbuddy;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

class DrawerItem {
	int pic;
	
	public DrawerItem(int pic) {
		this.pic = pic;
	}
}

public class DrawerAdapter extends BaseAdapter {

	private Activity context; 
	private ArrayList<DrawerItem> drawerItems;
	
	public DrawerAdapter(Activity context) {
		this.context = context;
		drawerItems = new ArrayList<DrawerItem>();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return drawerItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return drawerItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.drawer_list_item, null);
		}
		
		//Typeface externalFont = Typeface.createFromAsset(context.getAssets(), "fonts/century.ttf");
		
		//TextView text = (TextView)view.findViewById(R.id.drawer_item_text);
		//text.setTypeface(externalFont);
		//text.setTextSize(TypedValue.COMPLEX_UNIT_PT, 15);
		//text.setTextColor(Color.WHITE);
		//text.setText(drawerItems.get(position));
		
		ImageView img = (ImageView)view.findViewById(R.id.drawer_item_pic);
		img.setImageResource(drawerItems.get(position).pic);
		
		return view;
	}
	
	public void addItem(DrawerItem item) {
		drawerItems.add(item);
		notifyDataSetChanged();
	}

}
