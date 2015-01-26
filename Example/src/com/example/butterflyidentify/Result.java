package com.example.butterflyidentify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends Activity {

	 DBHelper dbhelper;
	 String Sql_order;
	 private TextView undata;
	 private Button goback;
     private Intent intent;
     List<String> ImageStirng = new ArrayList<String>();
     private static int uppage=0;//page 設定回上一頁的代碼 1為外型查詢 2為文字與科別查詢
 	 private ProgressDialog pd;//等待dialog
 	 private int rows_num;//資料筆數
 	 private Cursor data;
 	 private String sql_text;
     Intent newAct = new Intent();
     private ListView lv;
     ArrayList<HashMap<String, Object>> Item;
     List<String> ResultOrder = new ArrayList<String>();
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.result);
		//創資料庫
		  dbhelper = new DBHelper(this);
		  try {
		   dbhelper.createDataBase();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
	      undata= (TextView)findViewById(R.id.undata);//沒有資調顯示  
	      goback=(Button)findViewById(R.id.goback);
	      goback.setVisibility(View.GONE);
	      
		  Bundle bundle = getIntent().getExtras();
		  Sql_order=bundle.getString("SQL_order");
		  uppage=bundle.getInt("page");//page 設定回上一頁的代碼 1為外型查詢 2為文字與科別查詢 
		  
		  putDataToListView(Sql_order);
		  dbhelper.onDestroy();

	}

    public void onAppearancecheck(View V)
    {
        Intent newAct = new Intent();
        newAct.setClass( Result.this, AppearanceCheck.class );
        startActivity( newAct ); 
        Result.this.finish();     
    	
    }

    public void onclasscheck(View V)
    {
        Intent newAct = new Intent();
        newAct.setClass( Result.this, ClassCheck.class );
        startActivity( newAct );
        Result.this.finish();
    }
	
	protected void putDataToListView(String sql_test)
	{
		sql_text=sql_test;
		pd = ProgressDialog.show(Result.this, "等待", "資料處理中，請稍後……");
		dataspandTime();

	}
	
	//============換頁面之資料處理等待====================================
	private void dataspandTime() {//資料所花的時間
		new Thread(new Runnable() {
			@Override
			public void run() {
		    data = dbhelper.getData(sql_text);
			 //String cmq="test";
			 ArrayList<String> cmq=new ArrayList<String>();
			 cmq.add("test");
			 Boolean same=false; 
			 Item = new ArrayList<HashMap<String, Object>>(); 
			 rows_num =data.getCount(); //拿取資料筆數
			 if(rows_num!=0) //判斷資料是否為空
			 {data.moveToFirst();   //指向DB第一筆資料
				 for(int i=0;i<rows_num;i++)//將DB資料匯入adapter
			    { same=false;
				    for(int j=0;j<cmq.size();j++)
			      { if((cmq.get(j).compareTo(data.getString(0)))==0){same=true; break;}}
			        if(same){data.moveToNext(); continue;}
					 String Image=new String();
					 Image= "@drawable/s"+data.getString(2);
					 ImageStirng.add(data.getString(2));
					 int imageResource = getResources().getIdentifier(Image, null, getPackageName());
					 if(imageResource==0)
					{imageResource=R.drawable.nullimage;}
			         Log.v("Test",Image+"="+imageResource);
			          HashMap<String, Object> map = new HashMap<String, Object>();
			        map.put("ItemImage",/*Image_Processing(data.getString(2))*/  imageResource);
			        map.put("ItemName", data.getString(0)); 
			        map.put("ItemInfo", data.getString(1));
			        map.put("ItemButton", R.drawable.btngn);
			        Item.add(map);
			        cmq.add(data.getString(0));
			        data.moveToNext();
			    }
				 
			 }
			handler.sendEmptyMessage(0);// 執行耗時的方法之後發送给handler
		}

	}).start();
	
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
		     lv = (ListView) findViewById(R.id.showlist);
			 //若資料庫沒有資料時，顯示尚無資料，並顯示返回鍵
			 if(rows_num==0){lv.setVisibility(View.GONE); undata.setText("尚無資料");
		          goback.setVisibility(View.VISIBLE);
		          goback.setOnClickListener(new Button.OnClickListener()
		          {
		           @Override
		           public void onClick(View v) {
		                 // TODO Auto-generated method stub
		                  /* 返回result回上一個activity */
		        	   Result.this.setResult(RESULT_OK, intent);
		              /* 结束這個activity */
		        	   Result.this.finish();     
		           }
		          });
			 } 
			 //建立adapter
			  lv_BtnAdapter Btnadapter = new lv_BtnAdapter(
			  Result.this,
			  Item,
			         R.layout.adapter_button,
			         new String[] {"ItemImage","ItemName", "ItemInfo","ItemButton"},
			         new int[] {R.id.ItemImage,R.id.ItemName,R.id.ItemInfo,R.id.ItemButton}
			 );
			  lv.setAdapter(Btnadapter);//產生listview
			    data.close();
			    dbhelper.onDestroy();
				
			  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		            @Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		            	Intent it;
		            	Uri uri;
		            	final TextView B_Name=(TextView) arg1.findViewById(R.id.ItemName);
		            	uri = Uri.parse("https://www.google.com.tw/#q=+"+B_Name.getText().toString());
						Toast.makeText(Result.this, B_Name.getText().toString() ,Toast.LENGTH_SHORT).show();
						it = new Intent(Intent.ACTION_VIEW, uri);
    					startActivity(it);
		            }
		            
		        });   
			    
			pd.dismiss();// 关闭ProgressDialog
		}
	};
	
    
    //============Back鍵返回上一頁=======================================
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){

    switch(uppage){
    case 1:    
        newAct.setClass( Result.this, AppearanceCheck.class );
        startActivity( newAct );
        break;
    case 2:
        newAct.setClass( Result.this, ClassCheck.class );
        startActivity( newAct );
        break;
    default:
        newAct.setClass( Result.this, MainActivity.class );
        startActivity( newAct );
    	break;
        }
    return true;
    }
    return super.onKeyDown(keyCode, event);
    }
	///====	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

}
