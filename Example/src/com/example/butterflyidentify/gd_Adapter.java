package com.example.butterflyidentify;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class gd_Adapter extends BaseAdapter{

	 private ArrayList<HashMap<String, Object>> mAppList;
	 private LayoutInflater mInflater;
	 private Context mContext;
	 private String[] keyString;
	 private int[] valueViewID;

	 private ItemView itemView;
	  
	 private class ItemView {
	    ImageView ItemImage;
	    TextView ItemColor;
	 }

	  public gd_Adapter(Context c, ArrayList<HashMap<String, Object>> appList, int resource, String[] from, int[] to) {
	    mAppList = appList;
	    mContext = c;
	    mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    keyString = new String[from.length];
	    valueViewID = new int[to.length];
	    System.arraycopy(from, 0, keyString, 0, from.length);
	    System.arraycopy(to, 0, valueViewID, 0, to.length);
	 }
	  
	  @Override
	  public int getCount() {
	   // TODO Auto-generated method stub
	   //return 0;
	   return mAppList.size();
	  }

	  @Override
	  public Object getItem(int position) {
	   // TODO Auto-generated method stub
	   //return null;
	   return mAppList.get(position);
	  }

	  @Override
	  public long getItemId(int position) {
	   // TODO Auto-generated method stub
	   //return 0;
	   return position;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	   // TODO Auto-generated method stub
	   //return null;
	   
	   if (convertView != null) {
	    itemView = (ItemView) convertView.getTag();
	    } else {
	        convertView = mInflater.inflate(R.layout.pop_list_item, null);
	        itemView = new ItemView();
	        itemView.ItemImage = (ImageView)convertView.findViewById(valueViewID[0]);
	        itemView.ItemColor = (TextView)convertView.findViewById(valueViewID[1]);
	        convertView.setTag(itemView);
	    }

	    HashMap<String, Object> appInfo = mAppList.get(position);
	    if (appInfo != null) {
	   
	        int mid = (Integer)appInfo.get(keyString[0]);
	        String name = (String) appInfo.get(keyString[1]);
	        itemView.ItemColor.setText(name);
	        itemView.ItemImage.setImageDrawable(itemView.ItemImage.getResources().getDrawable(mid));
	    }

	    return convertView;
	  }
	  
	  class ItemButton_Click implements OnClickListener {
	    private int position;

	    ItemButton_Click(int pos) {
	        position = pos;
	    }

	    @Override
	    public void onClick(View v) {
	        int vid=v.getId();

	    }
	 }
	
}
