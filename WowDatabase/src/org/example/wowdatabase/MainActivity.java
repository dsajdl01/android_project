package org.example.wowdatabase;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.ArrayAdapter;
import android.widget.Toast;


public class MainActivity extends Activity {

	private TextView txtTop;
	private TextView txtView;
	private EditText name;
	private EditText email;
	private EditText emplId;
	private EditText deleteEmp;
	private EditText update;
	private EmployeeDataSource dataSource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtTop = (TextView) findViewById(R.id.txt_top_view);
		txtView = (TextView) findViewById(R.id.txt_view);
		name = (EditText) findViewById(R.id.edit_txt_name);
		email = (EditText) findViewById(R.id.edit_txt_em);
		emplId = (EditText) findViewById(R.id.edit_txt_id);
		deleteEmp = (EditText) findViewById(R.id.edit_txt_deleteEmp);
		update = (EditText) findViewById(R.id.edit_txt_id_for_update);
		dataSource = new EmployeeDataSource(this);
		dataSource.open();
		File dtb = getApplicationContext().getDatabasePath("commments.db");
		if(!dtb.exists()){
			txtTop.setText("Database do not exist NOT FOUND.");
		}
		else{
			try{
				txtTop.setText( dataSource.getStoredDate());  }
			catch (Exception e){
				txtTop.setText(e.toString());
			}
		}
	}
	
	public void getEmployeeId(View v){
		try{
			String empId = update.getText().toString().trim();
			String eMailText = email.getText().toString().trim();
			if(empId.matches("")){
				Toast.makeText(this, "You did not enter employee's id.", Toast.LENGTH_SHORT).show();
				return;
			} else{
			
				if(eMailText.matches("")){
				Toast.makeText(this, "You did not enter employee's mail.", Toast.LENGTH_SHORT).show();
			}
			else{
					dataSource.updateEmployeeEmail(empId, eMailText);
					Toast.makeText(this,"Employee of the id: " + empId + " was updated from the database", Toast.LENGTH_SHORT).show();
					update.setText("");
					email.setText("");
				}
		}
		} catch (Exception e){
			txtView.setText(e.toString());
		}
	}
	
	public void countRows(View v){
		int num = dataSource.getProfilesCount();
		txtView.setText("In the database is " + num);
		displayToast(num);
		try{
		}catch (Exception e){
			txtView.setText(e.toString());
		}
	}
	
	
	public void displayToast(int num){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
		                               (ViewGroup) findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("It was updated... In the database is " + num );

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
	
	public void getEmployee(View v){
		String idEmp = emplId.getText().toString().trim();
		try{
		if(idEmp.matches("")){
			Toast.makeText(this, "You did not enter employee's id.", Toast.LENGTH_SHORT).show();
		}
		else if(isIdMatch(idEmp)){
			Toast.makeText(this, idEmp + " does not match with any emploee's id", Toast.LENGTH_SHORT).show();
		}
		else{
			String employee = dataSource.getEmployeeName(idEmp);
			emplId.setTag("");
			txtView.setText(employee);
		}
		}
		catch (Exception e){
			txtView.setText(e.toString());
		}
	}
	
	public void deleteEmployee(View v){
		String empId = deleteEmp.getText().toString().trim();
		if(empId.matches("")){
			Toast.makeText(this, "You did not enter employee's id.", Toast.LENGTH_SHORT).show();
		}
		else if(isIdMatch(empId)){
			Toast.makeText(this, empId + " deas not match with any employee's id.", Toast.LENGTH_SHORT).show();
		}
		else{
			//Employee emp = getEmpl(empId);
			dataSource.deleteEmployeeFromDatabase(empId);
			Toast.makeText(this, "Employee of the id: " + empId + " was deleted from the database", Toast.LENGTH_SHORT).show();
			deleteEmp.setText("");
		}
		
		
	}
	
	public void deleteText(View v){
		dataSource.deleteAll();
		name.setText("");
		email.setText("");
		List<Employee> values = dataSource.getAllComments();
		String message = getData(values);
		txtView.setText(message);
		Toast.makeText(this, "Employee table was deleted from database", Toast.LENGTH_SHORT).show();
	}

	public void addText(View v){
		String empName = name.getText().toString();
		String empEmail = email.getText().toString();
		
		if (empName.matches("")){
			Toast.makeText(this, "You did not enter a employee name", Toast.LENGTH_SHORT).show();
			return;
		}
		else{
			if(empEmail.matches("")){
				Toast.makeText(this, "You did not enter a employee mail", Toast.LENGTH_SHORT).show();
				return;
			}
			else{
			dataSource.createComment(empName, empEmail);
			name.setText("");
			email.setText("");
			Toast.makeText(this, "Employee data was added into database", Toast.LENGTH_SHORT).show();
			}
		}
	}
	@SuppressLint("SimpleDateFormat")
	public void getCurrentDate(View v){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDate = sdf.format(new Date());
		txtTop.setText("FOUND! " + formatDate);
		dataSource.addDate(formatDate);
		
	}
	
	public void getNameColumnText(View v){
		Intent i = new Intent(this,DisplayColumn.class);
		startActivity(i);
	}
	
	
	public void displayText(View v){

		Intent i = new Intent(this,ActivityDisplayList.class);
		startActivity(i);
	}
	
	public String getEmployee(String id){
		List<Employee> values = dataSource.getAllComments();
		String employee = "";
		for(Employee line : values){
			String l = line.toString();
			String[] arr = l.split(" ");
			String idStored = arr[2];
			String clearId = idStored.replace(",","");
			if(id.equals(clearId.trim())){
				employee = l;
				break;
			}
		}
		return employee;
	}
	private boolean isIdMatch(String id){
		List<Employee> values = dataSource.getAllComments();
		for(Employee line: values){
			 	String l = line.toString();
	            String[] w = l.split(" ");
	            String storedId = w[2];
	            String clearId = storedId.replace(",", "");
	            if(id.equals(clearId.trim())){
	            	return false;
	            }
	        }
	        return true;
	}
	
	public void getStringText(View v){
		String data = dataSource.getAllValues();
		txtView.setText(data);
	}
	private String getData(List<Employee> values){
		String message = "\tALL EMPLOYEES\n\n"; 
		for(Employee l : values){
			message = message + l + "\n";
		}
		return message;
	}
	
	@Override
	protected void onResume(){
		dataSource.open();
		super.onResume();
	}
	@Override
	protected void onPause(){
		dataSource.close();
		super.onPause();
	}
}
