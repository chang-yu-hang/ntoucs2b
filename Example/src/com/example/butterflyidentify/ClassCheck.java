package com.example.butterflyidentify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ClassCheck extends Activity {


	 private String sql_t="SELECT C_Name,Often_Name FROM ButterflyID";
	 private String keyword;
	 private GridView gridView;
	 private int[] image = {
	            R.drawable.skipper, R.drawable.pieridae, R.drawable.papilionidae,
	            R.drawable.lycaenidae,R.drawable.nymphalidae
	    };
	 private String[] imgText = {
	            "弄蝶科", "粉蝶科","鳳蝶科","灰蝶科","蛺蝶科"
	    };
	 private int page=2;//page 設定回上一頁的代碼 1為外型查詢 2為文字與科別查詢 3為蝴蝶圖鑑
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.class_check);

		//查詢欄的keyword
		final EditText wordselect=(EditText)findViewById(R.id.editText1);
		  
		List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < image.length; i++) {
             Map<String, Object> item = new HashMap<String, Object>();
             item.put("image", image[i]);
             item.put("text", imgText[i]);
             items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, 
            items, R.layout.bi_grid_item, new String[]{"image", "text"},
            new int[]{R.id.image, R.id.text});
        
        gridView = (GridView)findViewById(R.id.main_page_gridview);
        gridView.setNumColumns(2);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new GridView.OnItemClickListener(){
        	         @Override
        	         public void onItemClick(AdapterView adapterView,View view,int position,long id) {
        	        	        	        		   
        	      sql_t="SELECT  C_Name,Often_Name,ButterflyID.ID FROM Detail,ButterflyID WHERE  Detail.Class='"+imgText[position]+"' and ButterflyID.ID=Detail.ID";   
        	        	   
                           Bundle password = new Bundle();
                           password.putString("SQL_order",sql_t );
                           password.putInt("page", page);//2為文字與科別查詢
        	        	   Intent newAct = new Intent();
        	               newAct.setClass( ClassCheck.this, Result.class );
        	               newAct.putExtras(password);
        	               startActivity( newAct );
        	        }
        	      });
		
        
        Button buttoncheck = (Button)findViewById( R.id.check );				
        buttoncheck.setOnClickListener(  new OnClickListener(){
            public void onClick(View v) {
            	
                keyword=wordselect.getText().toString();
        	    Log.v("keyword",keyword);
            	sql_t="SELECT C_Name,Often_Name,ID FROM ButterflyID WHERE ( C_Name LIKE  '%"+ keyword  +"%' or Often_Name LIKE '%"+ keyword  +"%')"   ;
               Bundle password = new Bundle();
               password.putString("SQL_order",sql_t );
               password.putInt("page", page);//2為文字與科別查詢
               Intent newAct = new Intent();
               newAct.setClass(ClassCheck.this, Result.class );
               newAct.putExtras(password);
               startActivity( newAct );
               
           }
         });
	}
	
	public void onAppearancecheck(View V)
	{
        Intent newAct = new Intent();
        newAct.setClass( ClassCheck.this, AppearanceCheck.class );
        startActivity( newAct ); 
        ClassCheck.this.finish();
		
	}
	
	public void onclasscheck(View V)
	{
		
	}

    protected void onRestart()
    {super.onRestart();
     super.finish();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.class_check, menu);
		return true;
	}

}
