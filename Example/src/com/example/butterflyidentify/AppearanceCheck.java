package com.example.butterflyidentify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AppearanceCheck extends Activity {
    //test svn text
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private int x,y;//popwindow抓位置
	private int status=0;//目前事件,顏色=1,紋路=2,外型=3
	private ImageButton colorpop;  
    Button buttoncheck;
    private RelativeLayout ColorRL,StripeRL;
	private GridView menuview;  
	private RelativeLayout layout;  
	private PopupWindow pop;
	private int page=1;//page 設定回上一頁的代碼 1為外型查詢 2為文字與科別查詢 3為蝴蝶圖鑑
	//=========================顏色宣告==========================================
	private String color[]={"黑","橘紅","橘黃","黃","綠","藍","紫","咖啡","灰","白"};
	private int colorImage[]={R.drawable.black,R.drawable.orange_red,R.drawable.orange_yellow,R.drawable.yellow
			,R.drawable.green,R.drawable.blue,R.drawable.purple,R.drawable.light_wood,R.drawable.gray,R.drawable.white};
	private String colorsql[]={"black","orange_red","orange_yellow","yellow","green","blue","purple","light_wood"
			,"gray","white"};
	private int colordraw[]={R.drawable.bfly_black,R.drawable.bfly_orange_red,R.drawable.bfly_orange_red
			,R.drawable.bfly_yellow,R.drawable.bfly_green,R.drawable.bfly_blue,R.drawable.bfly_purple
			,R.drawable.bfly_light_wood,R.drawable.bfly_gray,R.drawable.bfly_white};
	//=========================斑紋宣告======================================
	private String stripename[]={"有色塊","有色塊","邊緣花紋","有眼點","前端異色","格子狀","水平色帶","多條細線","沒花紋","一條細線",
			                      "一些斑點","少數斑點","滿佈斑點","零星斑點","多條色帶","垂直色帶","翅脈明顯"};
	private int stripeImage[]={R.drawable.block_u_m,R.drawable.block_m,R.drawable.edge_m,R.drawable.eyes_m,R.drawable.fore_half_m,
			R.drawable.grid_m,R.drawable.h_band_m,R.drawable.lines_m,R.drawable.mono_m,R.drawable.one_line_m,R.drawable.some_spots_m,
			R.drawable.spot_m,R.drawable.spots_m,R.drawable.stars_m,R.drawable.three_bands_m,R.drawable.v_band_m,R.drawable.vein_m};
	private String stripesql[]={"block","block","edge","eyes","fore_half",
			"grid","h_band","lines","mono","one_line","some_spots",
			"spot","spots","stars","three_bands","v_band","vein"};
	private int stripedraw[]={R.drawable.block_u,R.drawable.block,R.drawable.edge,R.drawable.eyes,R.drawable.fore_half,
			R.drawable.grid,R.drawable.h_band,R.drawable.lines,R.drawable.fly,R.drawable.oneline,R.drawable.some_spots,
			R.drawable.spot,R.drawable.spots,R.drawable.stars,R.drawable.threebands,R.drawable.v_band,R.drawable.vein};
	////////////////////////////////////////////////////////////////////////
	private TextView title; //popupwindows 標題
	private Button exit; //popupwindows 離開案件
	private Button stripepop;
	private ImageView colorview,stripeview;//圖像中間畫面
	private String sql_order;
	private String sirp_w=null,color_w=null;
	 int width,high;//螢幕大小
	static int scalewidth,scaleheigh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.appearance_check);
		
        buttoncheck = (Button)findViewById( R.id.check );
        ColorRL=(RelativeLayout)findViewById( R.id.ColorRL );
        StripeRL=(RelativeLayout)findViewById( R.id.StripeRL );
        
        DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        width=dm.widthPixels;
        high=dm.heightPixels;
       
        colorview=(ImageView)findViewById(R.id.colordraw);
              		
		stripeview=(ImageView)findViewById(R.id.stripedraw);
		colorpop = (ImageButton) findViewById(R.id.color); 
		colorpop.getTop();
    	//POPWindow 顯示位置X Y
///============================顏色=====================================
		colorpop.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        status=1;	
	   		 y = colorpop.getBottom() * 3/2;
			 x = getWindowManager().getDefaultDisplay().getWidth() /10;
			 //Drawable putbtn=getResources().getDrawable(R.drawable.colorusing);
			 //colorpop.setBackgroundDrawable(putbtn);
			 ColorRL.setBackgroundColor(Color.parseColor("#5C5C5C"));
	        	popshow(x,y,color,colorImage,10); 
	        }                   
	    });  
///============================紋路=====================================	    
	    stripepop=(Button)findViewById(R.id.stripe);
		stripepop.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
	        	status=2;
		   		 y = colorpop.getBottom() * 3/2;
				 x = getWindowManager().getDefaultDisplay().getWidth() /10;
				 //Drawable putbtn=getResources().getDrawable(R.drawable.stripeusing);
				 //stripepop.setBackgroundDrawable(putbtn);
				StripeRL.setBackgroundColor(Color.parseColor("#5C5C5C"));
	        	popshow(x,y,stripename,stripeImage,17); 
	        }                   
	    });  
///====================================================================   
        buttoncheck.setOnClickListener(  new OnClickListener(){
            public void onClick(View v) {
            	
				 //Drawable putbtn=getResources().getDrawable(R.drawable.checkusing);
				 //buttoncheck.setBackgroundDrawable(putbtn);
				 if(sirp_w!=null && color_w==null)
			    	{sql_order="SELECT  C_Name,Often_Name,Search.ID FROM ButterflyID,Search WHERE Search.Stripe='"+sirp_w+"' and ButterflyID.ID=Search.ID ORDER BY Search.ID ASC"; }
			    	else if(sirp_w==null && color_w!=null)
			    	{sql_order="SELECT  C_Name,Often_Name,Search.ID FROM ButterflyID,Search WHERE Search.Color='"+color_w+"' and ButterflyID.ID=Search.ID ORDER BY Search.ID ASC";}
			    	else if(sirp_w!=null && color_w!=null)
			    	{sql_order="SELECT  C_Name,Often_Name,Search.ID FROM ButterflyID,Search WHERE Search.Color='"+color_w+"' and Search.Stripe='"+sirp_w+"' and ButterflyID.ID=Search.ID ORDER BY Search.ID ASC";}
			    	else{sql_order="SELECT  C_Name,Often_Name,Search.ID FROM ButterflyID,Search WHERE ButterflyID.ID=Search.ID ORDER BY Search.ID ASC";}
			       Bundle password = new Bundle();
			       password.putString("SQL_order",sql_order );
			       password.putInt("page", page);//1為外型查詢
			       Intent newAct = new Intent();
			       newAct.setClass( AppearanceCheck.this, Result.class );
			       newAct.putExtras(password);
			       startActivity( newAct );
           }
         });
		
	}
	
    public void onclasscheck(View V)
    {
        Intent newAct = new Intent();
        newAct.setClass( AppearanceCheck.this, ClassCheck.class );
        startActivity( newAct );
        AppearanceCheck.this.finish();	       
    	
    }
    public void onAppearancecheck(View V)
    {  	
    }

	//===按下紋路與顏色案件，彈出PopupWindow ====
	private void popshow(int xi, int yi,String[] Name,int[] Image,int kind) {  
		 
		layout = (RelativeLayout) LayoutInflater.from(AppearanceCheck.this).inflate(
				R.layout.popwindow, null);
	    menuview=(GridView)layout.findViewById(R.id.selectcolor);
		title=(TextView) layout.findViewById(R.id.title);
		exit=(Button) layout.findViewById(R.id.exit);
		//新增Adapter項目與欄位，放入gridview裡顯示
	    ArrayList<HashMap<String, Object>> Item = new ArrayList<HashMap<String, Object>>();
	    for(int j=0;j<kind;j++)
	    { HashMap<String, Object> map = new HashMap<String, Object>();
	     map.put("ItemImage",Image[j]);
	     map.put("ItemColor",Name[j]);
	     Item.add(map);
	    }
	    //新建一個class gd_Adapter 來複寫BaseAdapter方法 來顯示自己想要的layout
	    gd_Adapter grdviewAdapter=new gd_Adapter(this,Item,R.layout.pop_list_item
	    		,new String[]{"ItemImage","ItemColor"},new int[]{R.id.ItemImage,R.id.menuitem} );
	    
	    menuview.setAdapter(grdviewAdapter);
	    
	    switch(status)//title 標題轉換
	    {
	    case 1:
	    title.setText("請選擇顏色" );
	    break;
	    case 2:
	    title.setText("請選擇斑紋");
	    break;
	    }
	    //popupwindows 設定
	    Log.v("width-high",width+"-"+high);
	    pop = new PopupWindow(layout,width*4/5,high*3/4); //所要呈現的layout與大小
	    pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(false);
		pop.setFocusable(true);
		pop.setContentView(layout);
		pop.showAtLocation(findViewById(R.id.linearlayout1), Gravity.LEFT
				| Gravity.TOP, xi, yi);

       pop.setOnDismissListener(new OnDismissListener() {
            
            @Override
            //popupwindow 消失時，顏色或紋路之案件還原原圖
            public void onDismiss() {
                    // TODO Auto-generated method stub
			    
			    switch(status)
			    {
			    case 1:
			    	ColorRL.setBackgroundColor(Color.parseColor("#CDCDC1"));
			    	//colorpop.setBackgroundDrawable(upbtn);
			    	break;
			    case 2:
			    	StripeRL.setBackgroundColor(Color.parseColor("#CDCDC1"));
			    	//stripepop.setBackgroundDrawable(upbtn);
			    	break;
			    }
				pop.dismiss();
				pop = null; 
            }
        });
		
		//紅色小X 跳出Popupwindows
	    exit.setOnClickListener(new View.OnClickListener() {  
	        public void onClick(View v) {  
				pop.dismiss();
				pop = null; 
	        }                   
	    }); 
		//gridview 觸發事件
		menuview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) { 
			    switch(status)
			    {
			    case 1:
			    	color_w=colorsql[arg2];
			        colorview.setImageDrawable(getResources().getDrawable(colordraw[arg2]));
			    break;	
			    case 2:
			    	sirp_w=stripesql[arg2];
			    	stripeview.setImageDrawable(getResources().getDrawable(stripedraw[arg2]));    
			    break;	
			    }
				pop.dismiss();
				pop = null;
			}
		});
	}   
	
	protected void onResume()
	{super.onResume();
     Drawable upbtn=getResources().getDrawable(R.drawable.check);
	 buttoncheck.setBackgroundDrawable(upbtn);
	}
	
    protected void onRestart()
    {super.onRestart();
     super.finish();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.appearance_check, menu);
		return true;
	}

}
