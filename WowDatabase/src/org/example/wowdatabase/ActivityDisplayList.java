package org.example.wowdatabase;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityDisplayList extends Activity {
	
	private EmployeeDataSource dataSource;
	private TextView txtView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_of_employees);
		txtView = (TextView) findViewById(R.id.txt_Employee_List);
		txtView.setText("dsfg");
		
		
		
		dataSource = new EmployeeDataSource(this);
		dataSource.open();
		
		List<Employee> values = dataSource.getAllComments();
		String message = getData(values);
		
		txtView.setText(message);
		
	}
	public void getBack(View v){
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);
	}
	protected String getData(List<Employee> values){
		String message = "\t\t\t\t\t\tALL EMPLOYEES\n\n"; 
		for(Employee l : values){
			message = message + l + "\n";
		}
		return message;
	}
	
	
}
