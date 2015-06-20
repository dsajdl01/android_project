package org.example.preferences;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

	TextView viewText;
	Button press;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewText = (TextView) findViewById(R.id.txt_view);
		press = (Button) findViewById(R.id.btn_press);
		reload();
	
	}
	
	public void reload(){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this); 
		 String str = sp.getString("downloadType","");
		 setValue(str);
	}
	public void setValue(String s){
		viewText.setText(s + "  <--this.");
		press.setText(s + "\t\tChanege Preferencees");
	}
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 reload();
	 }
	 @Override
     protected void onPause() {
         super.onPause();
         setResult(RESULT_OK, new Intent(this, MainActivity.class));
     }

	public boolean setPreferencies(View v){
		try{
		 Intent intent = new Intent();
	        intent.setClass(MainActivity.this, SetPreferenceActivity.class);
	        startActivityForResult(intent, 0); 
	  
	       return true;
		}catch (Exception e){
			viewText.setText(e.toString());
			return false;
		}
	}
}
