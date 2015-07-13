package com.example.db;

import android.content.ClipData.Item;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBConstants {
	public static final String TABLE_NAME = "user";
	public static final String KEY_ID = "_id";
	public static final String DB_NAME = "mydata.db";
	public static final int VERSION = 1;
	
	public static final String USERNAME_COLUMN = "username";
	public static final String PASSWORD_COLUMN = "password";
	public static final String SECRET_KEY = "080816";
	
}
