package com.example.butterflyidentify;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

 private static String DB_NAME = "Newbtnfly01.db";
 private SQLiteDatabase db;
 private final Context context;
 private String DB_PATH;
 private DBHelper dbHelper;

 ///建構子
 public DBHelper(Context context) {
  super(context, DB_NAME, null, 1);//context,name,null,version
  this.context = context;
  DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";

 }

 ///建立DB,判斷是否成功複製DB
 public void createDataBase() throws IOException {


	boolean dbExist = checkDataBase();
	if (dbExist) {
		Log.v("recreate", "OK,"+dbExist);
	 }
	
	 else {
		   this.getReadableDatabase();
		   try {
		    copyDataBase();
		   } catch (IOException e) {
			   Log.e("error","Error copying database");
		   }
		  }
  
 }

 
 private boolean checkDataBase() {
	  File dbFile = new File(DB_PATH + DB_NAME);
	  return dbFile.exists();
	 }

 
 private void copyDataBase() throws IOException {

  InputStream myInput = context.getAssets().open(DB_NAME);
  String outFileName = DB_PATH + DB_NAME;
  Log.v("copy", "OK=");
  OutputStream mOutput = new FileOutputStream(outFileName);
  byte[] mBuffer = new byte[1024];
  int mLength;
  while ((mLength = myInput.read(mBuffer))>0)
  {
      mOutput.write(mBuffer, 0, mLength);
  }
  Log.v("copy", "OK");
  mOutput.flush();
  mOutput.close();
  myInput.close();
 }

 
 public Cursor getData(String sql_order) { 
	 
  dbHelper=new DBHelper(context);
  String myPath =DB_PATH + DB_NAME;
  Log.v("data","OKwait"+myPath);
  //db = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.OPEN_READONLY);
  db=dbHelper.getWritableDatabase();
  
  Log.v("data", "OK");
  return  db.rawQuery(sql_order, null);
 //return	  db.query("ButterflyID", null, null, null, null,  
	                //null, null);  
 }


 public  void onDestroy(){
	 if(db!=null ||dbHelper!=null)
	 {db.close();
	  dbHelper.close();}
	 super.close();
 }
 
 @Override
 public void onCreate(SQLiteDatabase arg0) {
  // TODO Auto-generated method stub
 }

 @Override
 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  // TODO Auto-generated method stub
 }
}
