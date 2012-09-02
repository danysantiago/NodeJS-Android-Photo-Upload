package com.dany.nodejsupload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    private static final int GET_PIC_CODE = 1;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == GET_PIC_CODE && resultCode == Activity.RESULT_OK){
			String imgPath =  data.getData().getEncodedPath();
			Log.d("DEBUG", "Choose: " + imgPath);
			new HttpUpload(this, imgPath).execute();
		}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void uploadOnClick(View v){
    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
    	intent.setType("image/*");
    	startActivityForResult(intent, GET_PIC_CODE);
    }
    
}
