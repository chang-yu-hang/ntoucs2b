package com.example.butterflyidentify;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class lv_BtnAdapter extends BaseAdapter {
 
 private ArrayList<HashMap<String, Object>> mAppList;
private LayoutInflater mInflater;
private Context mContext;
private String[] keyString;
private int[] valueViewID;
//private Bitmap Pic;

private ItemView itemView;
 
private class ItemView {
   ImageView ItemImage;
   TextView ItemName;
   TextView ItemInfo;
   Button ItemButton;
}

 public lv_BtnAdapter(Context c, ArrayList<HashMap<String, Object>> appList, int resource, String[] from, int[] to) {
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
       convertView = mInflater.inflate(R.layout.adapter_button, null);
       itemView = new ItemView();
       itemView.ItemImage = (ImageView)convertView.findViewById(valueViewID[0]);
       itemView.ItemName = (TextView)convertView.findViewById(valueViewID[1]);
       itemView.ItemInfo = (TextView)convertView.findViewById(valueViewID[2]);
       itemView.ItemButton = (Button)convertView.findViewById(valueViewID[3]);
       convertView.setTag(itemView);
   }

   HashMap<String, Object> appInfo = mAppList.get(position);
   if (appInfo != null) {
  
       int mid = (Integer)appInfo.get(keyString[0]);
       String name = (String) appInfo.get(keyString[1]);
       String info = (String) appInfo.get(keyString[2]);
       int bid = (Integer)appInfo.get(keyString[3]);
       itemView.ItemName.setText(name);
       itemView.ItemInfo.setText(info);
       itemView.ItemImage.setImageDrawable(itemView.ItemImage.getResources().getDrawable(mid));//setImageBitmap(bitmap);
       itemView.ItemButton.setBackgroundDrawable(itemView.ItemButton.getResources().getDrawable(bid));
       itemView.ItemButton.setOnClickListener(new ItemButton_Click(position));
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
       if (vid == itemView.ItemButton.getId())
        Log.v("ola_log",String.valueOf(position) );
   }
}
}
