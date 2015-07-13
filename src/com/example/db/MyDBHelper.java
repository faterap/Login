package com.example.db;

import android.content.Context;

import android.database.sqlite.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteDatabase;

public class MyDBHelper extends net.sqlcipher.database.SQLiteOpenHelper{
	
	public MyDBHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DBConstants.TABLE_NAME, null, DBConstants.VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_TABLE = 
				"CREATE TABLE " + DBConstants.TABLE_NAME + " (" + 
	            DBConstants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	            DBConstants.USERNAME_COLUMN + " TEXT NOT NULL, " +
	            DBConstants.PASSWORD_COLUMN + " TEXT NOT NULL)";
		String CREATE_ADMIN = 
				"INSERT INTO "
				+ DBConstants.TABLE_NAME + "("
						+ DBConstants.USERNAME_COLUMN + " , " + DBConstants.PASSWORD_COLUMN + 
						" ) " + " VALUES ('admin', 'admin')";
		db.execSQL(CREATE_TABLE);
		db.execSQL(CREATE_ADMIN);
		
		System.out.println("Database created~");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String DROP_TABLE = "DROP TABLE IF EXISTS" + DBConstants.DB_NAME;
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}
	
	
}
