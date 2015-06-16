package org.example.wowdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MYSQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "employee";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_EMAIL	= "email";
	public static final String COLUMN_TIME = "saved_date";
	
	public static final String COLUMN_TIME_ID = "date_id";
	public static final String TABLE_TIME_NAME = "date"; 
	
	private static final String DATABASE_NAME = "commments.db";
	private static final int DATABASE_VERSION = 2;
	
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME + " text not null, " + COLUMN_EMAIL + " text)";
	private static final String DATABASE_CREATE_TIME = "create table " 
			+ TABLE_TIME_NAME + "(" + COLUMN_TIME + " text)";
	
	public MYSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_TIME);	
		database.execSQL(DATABASE_CREATE);
			
	}

  
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MYSQLiteHelper.class.getName(),"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIME_NAME);
		Log.w(MYSQLiteHelper.class.getName(),"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
} 
