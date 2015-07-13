package com.example.tools;

import android.content.Context;
import android.database.Cursor;

import com.example.db.DBConstants;
import com.example.db.MyDBHelper;

public class Login {
	private MyDBHelper dbHelper;
	public Login(User user,Context context, SuccessCallBack successCallBack, FailCallBack failCallBack){
		dbHelper = new MyDBHelper(context);
		net.sqlcipher.database.SQLiteDatabase db = dbHelper.getReadableDatabase(DBConstants.SECRET_KEY);
//		String username = etUsername.getText().toString();
//		String password = etPassword.getText().toString();
		
		String sql = "SELECT * FROM "
				+ DBConstants.TABLE_NAME + " WHERE "
				+ DBConstants.USERNAME_COLUMN +  " = ? and "
				+ DBConstants.PASSWORD_COLUMN +  " = ?";	
		Cursor cursor = db.rawQuery(sql, new String[]{user.getUsername(), user.getPassword()});
		if(cursor!=null){
			if(cursor.moveToFirst()){
				cursor.close();
				db.close();
				dbHelper.close();
				successCallBack.onSuccess();
			}
			else{
				failCallBack.onFail();
			}
		}
//		mDialog.setMessage("Logging in...");
//		mDialog.show();
	}
	
	public interface SuccessCallBack{
		void onSuccess();
	}
	
	public interface FailCallBack{
		void onFail();
	}

}
