package com.example.butterflyidentify;




import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//鎖螢幕直式
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除標題
        setContentView(R.layout.activity_main);
        
        ImageView myImageView = (ImageView)findViewById(R.id.cover);
        myImageView.setImageResource(R.drawable.cover);

          
        
    }

    
    public void onAppearancecheck(View V)
    {
        Intent newAct = new Intent();
        newAct.setClass( MainActivity.this, AppearanceCheck.class );
        startActivity( newAct );       
    	
    }

    public void onclasscheck(View V)
    {
        Intent newAct = new Intent();
        newAct.setClass( MainActivity.this, ClassCheck.class );
        startActivity( newAct );
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bi_main, menu);
        return true;
    }
    
}
