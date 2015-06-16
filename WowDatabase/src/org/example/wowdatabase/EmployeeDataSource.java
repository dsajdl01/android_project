package org.example.wowdatabase;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EmployeeDataSource {

	
	 // Database fields
	  private SQLiteDatabase database;
	  private MYSQLiteHelper dbHelper;
	  private String[] allColumns = { MYSQLiteHelper.COLUMN_ID,MYSQLiteHelper.COLUMN_NAME, MYSQLiteHelper.COLUMN_EMAIL};
	  private String[] timeColumn = {MYSQLiteHelper.COLUMN_TIME};
	
	  public EmployeeDataSource(Context context) {
	    dbHelper = new MYSQLiteHelper(context);
	  }
	
	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }
	
	  public void close() {
	    dbHelper.close();
	  }
	  
	  public int getProfilesCount() {
		    String countQuery = "SELECT  * FROM " + MYSQLiteHelper.TABLE_NAME;
		    Cursor cursor = database.rawQuery(countQuery, null);
		    int cnt = cursor.getCount();
		    cursor.close();
		    return cnt;
		}
	  
	  public String getEmployeeName(String id){
		 // Cursor cr = database.query(MYSQLiteHelper.TABLE_NAME, MYSQLiteHelper.COLUMN_NAME, MYSQLiteHelper.COLUMN_ID + "<?", id, null, null, null, null);
	//	  db.query(TABLE1_NAME, columns_T1, COLUMN_T1_TIMECEST_RAW + ">? AND "+COLUMN_T1_TIMECEST_RAW +"<?", args, null, null, null, null);}
		  Cursor cr = database.rawQuery("select * from " + MYSQLiteHelper.TABLE_NAME + " where " + MYSQLiteHelper.COLUMN_ID + "=" + "'" + id + "'", null);
		  cr.moveToFirst();
		  return cr.getString(1);
	  }
	  
	  
	 // public Employee createComment(String name, String email) {
	  public void createComment(String name, String email) {
	    ContentValues values = new ContentValues();
	    values.put(MYSQLiteHelper.COLUMN_NAME, name);
	    values.put(MYSQLiteHelper.COLUMN_EMAIL, email);
	    //long insertId = 
	    database.insert(MYSQLiteHelper.TABLE_NAME, null, values);
	    /**
	    Cursor cursor = database.query(MYSQLiteHelper.TABLE_NAME,
	        allColumns, MYSQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Employee newEmpl = cursorToComment(cursor);
	    cursor.close();
	    return newEmpl; */
	  } 
	  
	  public long addDate(String date){
		  database.delete(MYSQLiteHelper.TABLE_TIME_NAME, null, null);
		  ContentValues values = new ContentValues();
		  values.put(MYSQLiteHelper.COLUMN_TIME, date);
		  return database.insert(MYSQLiteHelper.TABLE_TIME_NAME, null, values);
	  }  
	/**	  Cursor crs = database.query(MYSQLiteHelper.TABLE_TIME_NAME, timeColumn, MYSQLiteHelper.COLUMN_TIME_ID + " = " + insertIt, 
				  null, null, null, null);
	 
		  crs.moveToFirst();
		  Date newDate = getDataCursor(crs);
		  crs.close();
		  return newDate;
  }
	private Date getDataCursor(Cursor crs){
		Date d = new Date();
		d.setId(crs.getLong(0));
	    d.setDate(crs.getString(1));
	    return d;
	} */
	  
	  
	  public void deleteAll(){
		  database.delete(MYSQLiteHelper.TABLE_NAME, null, null);
	  }
	  
		/**
	  public void deleteComment(Comment comment) {
	    long id = comment.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public Cursor getEmployeeByID(Employee emp){
		  long id = emp.getId();
		  Cursor crs = database.query(MYSQLiteHelper.TABLE_NAME,null, MYSQLiteHelper.COLUMN_ID+"=?", new String[] {id+""}, null, null, null);
		  if(crs != null){
			  crs.moveToFirst();
		  }
		  return crs;
	  }
	  */
	  
	  public String getStoredDate(){
		  Cursor crs = database.query(MYSQLiteHelper.TABLE_TIME_NAME, timeColumn, null, null, null, null, null);
		 crs.moveToFirst();
		 String strData = (crs.getString(0));
		  return strData;
	  }
	  
	  public List<String> EmployeesName(){
		//  List<Employee> emplName = new ArrayList<Employee>();
		  ArrayList<String> array = new ArrayList<String>();
		  Cursor cursor = database.query(MYSQLiteHelper.TABLE_NAME,
			        allColumns, null, null, null, null, null);
			
			    cursor.moveToFirst();
			    while (!cursor.isAfterLast()) {
			    	array.add(cursorEmpName(cursor));
			      cursor.moveToNext();
			    }
			    // make sure to close the cursor
			    cursor.close();
			    return array;	  
	  }
	  public String getAllValues(){
		  String message = "";
		  Cursor cr = database.query(MYSQLiteHelper.TABLE_NAME, allColumns, null , null, null, null, null);
		  cr.moveToFirst();
		  while(!cr.isAfterLast()){
			  message = message + cursorString(cr);
			  cr.moveToNext();
		  }
		  cr.close();
		  return message;
	  }
	  
	  private String cursorString(Cursor cr){
		  String name = cr.getString(1);
		  String mail = cr.getString(2);
		  return name + ", " + mail + ", ";
	  }
	  
	  private String cursorEmpName(Cursor cursor) {
		    String name = cursor.getString(1);
		    return name;
		  }
	  
	  public List<Employee> getAllComments() {
	    List<Employee> allEmpl = new ArrayList<Employee>();
	
	    Cursor cursor = database.query(MYSQLiteHelper.TABLE_NAME,
	        allColumns, null, null, null, null, null);
	
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Employee empl = cursorToComment(cursor);
	      allEmpl.add(empl);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return allEmpl;
	  }

	  //String strSQL = "UPDATE myTable SET Column1 = someValue WHERE columnId = "+ someValue;
	  public void updateEmployeeEmail(String name, String mail){
		  name = "'"+name +"'";
		//  String strSql = "UPDATE " + MYSQLiteHelper.TABLE_NAME + " SET " + MYSQLiteHelper.COLUMN_EMAIL +"=" + mail + " WHERE " + MYSQLiteHelper.COLUMN_NAME + "=" + name;
		 // database.execSQL(strSql);
		  ContentValues cv = new ContentValues();
		  cv.put(MYSQLiteHelper.COLUMN_EMAIL, mail);
		  database.update(MYSQLiteHelper.TABLE_NAME, cv, MYSQLiteHelper.COLUMN_NAME + "=" + name, null);
		  
	  }
	  
	  public void deleteEmployeeFromDatabase(String id){
		  database.delete(MYSQLiteHelper.TABLE_NAME, MYSQLiteHelper.COLUMN_ID + "=" + id, null);
		  
	  }
	  
	  private Employee cursorToComment(Cursor cursor) {
	    Employee emp = new Employee();
	    emp.setId(cursor.getLong(0));
	    emp.setName(cursor.getString(1));
	    emp.setEmail(cursor.getString(2));
	    return emp;
	  }
	
	
}
