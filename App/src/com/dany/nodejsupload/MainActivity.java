package com.dany.nodejsupload;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
			Uri imgUri =  data.getData();
			String imgPath = getPath(imgUri);
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
    
    //Get the path from Uri
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		startManagingCursor(cursor);
		int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

    
}
