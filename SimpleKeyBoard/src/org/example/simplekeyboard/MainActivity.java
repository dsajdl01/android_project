package org.example.simplekeyboard;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	private EditText editText2;
	private KeyListener originalKeyListener;
	private Button buttonShowIme;
//	private EditText et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	//	et = (EditText) findViewById(R.id.edit_txt);
		
		 // Find out our editable field.
	    editText2 = (EditText)findViewById(R.id.edit_txt);
	    // Save its key listener which makes it editable.
	    originalKeyListener = editText2.getKeyListener();
	    // Set it to null - this will make the field non-editable
	    editText2.setKeyListener(null);

	    // Find the button which will start editing process.
	    buttonShowIme = (Button)findViewById(R.id.button_show_ime);
	    // Attach an on-click listener.
	    buttonShowIme.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	 // Focus the field.
	            editText2.requestFocus();
	            // Restore key listener - this will make the field editable again.
	            editText2.setKeyListener(originalKeyListener);
	            editText2.setFocusableInTouchMode(true);
	            // Show soft keyboard for the user to enter the value.
	            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	            imm.showSoftInput(editText2, InputMethodManager.SHOW_IMPLICIT);
	        }
	    });

	    // We also want to disable editing when the user exits the field.
	    // This will make the button the only non-programmatic way of editing it.
	    editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	        @Override
	        public void onFocusChange(View v, boolean hasFocus) {
	            // If it loses focus...
	            if (!hasFocus) {
	                // Hide soft keyboard.
	                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	                imm.hideSoftInputFromWindow(editText2.getWindowToken(), 0);
	                // Make it non-editable again.
	                editText2.setKeyListener(null);
	            }
	        }
	    });
	}
}
