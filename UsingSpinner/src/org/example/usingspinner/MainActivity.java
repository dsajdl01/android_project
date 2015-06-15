package org.example.usingspinner;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
//import android.view.View;
import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.TextView;

public class MainActivity extends Activity  {

	Spinner spinner_c;
	Spinner spinner_2;
	TextView txtV;
	TextView txtV2;
	
	private ArrayAdapter<String> aspnCountries;
	private List<String> allcountries;
	private static final String[] mCountries = { "China" ,"Russia", "Germany", "Ukraine", "Belarus", "USA", 
		"Czech Republic", "Colombia", "Ecudar", "Spain", "UK", "Australia", "Slovakia" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("SpinnerActivity");
		setContentView(R.layout.activity_main);
		txtV = (TextView) findViewById(R.id.txtView_sel);
		txtV2 = (TextView) findViewById(R.id.txtView_sel2);
	    find_and_modify_view();
	}

	 private void find_and_modify_view() {
		    spinner_c = (Spinner) findViewById(R.id.spinner_1);
		    allcountries = new ArrayList<String>();
		    for (int i = 0; i < mCountries.length; i++) {
		      allcountries.add(mCountries[i]);
		    }
		    aspnCountries = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item, allcountries);
		    aspnCountries
		        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spinner_c.setAdapter(aspnCountries);
		    
		    
		    spinner_2 = (Spinner) findViewById(R.id.spinner_2);
		 //   String text = spinner_2.getSelectedItem().toString();
		        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
		                this, R.array.countries, android.R.layout.simple_spinner_item);
		        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        spinner_2.setAdapter(adapter);

	 spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        txtV.setText("\n\n\n"+String.valueOf(item) + " <<<<<<<<<<<<<<<------------------->>>>>>>>>>>>>>>>>>");
		        
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
	 
	 spinner_c.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        Object item = parent.getItemAtPosition(pos);
		        txtV2.setText("\n\n\n"+String.valueOf(item) + "   <<<<<<-------------->>>>>>");
		        
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }
		});
	 
	 }

}

