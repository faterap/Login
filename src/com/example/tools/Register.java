package com.example.tools;

import net.sqlcipher.database.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.db.DBConstants;
import com.example.db.MyDBHelper;

public class Register {
	public Register(User user, Context context,
			SuccessCallBack successCallBack, FailCallBack failCallBack) {
		MyDBHelper dbHelper = new MyDBHelper(context);
		SQLiteDatabase db = dbHelper
				.getReadableDatabase(DBConstants.SECRET_KEY);

		Cursor cursor = db.rawQuery("SELECT * FROM " + DBConstants.TABLE_NAME,
				null);
		boolean usernameExisted = false;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					int userColumn = cursor
							.getColumnIndex(DBConstants.USERNAME_COLUMN);
					String username = cursor.getString(userColumn);
					if (user.getUsername().equals(username)) {
						usernameExisted = true;
						failCallBack.onFail();
						break;
					}
				} while (cursor.moveToNext());

				if (!usernameExisted) {
					ContentValues cv = new ContentValues();
					cv.put(DBConstants.USERNAME_COLUMN, user.getUsername());
					cv.put(DBConstants.PASSWORD_COLUMN, user.getPassword());
					db.insert(DBConstants.TABLE_NAME, null, cv);
					cursor.close();
					db.close();
					dbHelper.close();
					successCallBack.onSuccess();
				}
				
			}
		}

		// if(usernameExisted){
		// failCallBack.onFail();
		// }else{
		// // cv.put(DBConstants.USERNAME_COLUMN,
		// etUsername.getText().toString());
		// // cv.put(DBConstants.PASSWORD_COLUMN,
		// etPassword.getText().toString());
		// // db.insert(DBConstants.TABLE_NAME, null, cv);
		// // Toast.makeText(getApplicationContext(),"Sign up successfully!",
		// Toast.LENGTH_SHORT).show();
		// // cursor.close();
		// successCallBack.onSuccess();
		// }
	}

	public interface SuccessCallBack {
		void onSuccess();
	}

	public interface FailCallBack {
		void onFail();
	}
}
