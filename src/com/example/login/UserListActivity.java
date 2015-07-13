package com.example.login;

import java.util.ArrayList;
import java.util.List;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import com.example.db.DBConstants;
import com.example.db.MyDBHelper;
import com.example.tools.User;

public class UserListActivity extends ListActivity{
	private List<User> rowitem = new ArrayList<User>();
	private UserAdapter adapter;
	
	private MyDBHelper dbHelper;
	private SQLiteDatabase db;
	
	private TextView usernameTv;
	private TextView passwordTv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getData();
		adapter = new UserAdapter(this,rowitem );
		setListAdapter(adapter);
		
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
//				rowitem.remove(arg2);
				
				removeUserFromList(arg2);
//				setListAdapter(adapter);
				return true;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.userlist, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.new_user:
			startActivity(new Intent(UserListActivity.this, UserPagerActivity.class));
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		// TODO Auto-generated method stub
//		super.onListItemClick(l, v, position, id);
//	}
	
	
	private void getData(){
		MyDBHelper dbHelper = new MyDBHelper(this);
		SQLiteDatabase db = dbHelper.getReadableDatabase(DBConstants.SECRET_KEY);
		String sql = "SELECT * FROM " + DBConstants.TABLE_NAME;
		Cursor cursor = db.rawQuery(sql, null);
		rowitem.clear();
		while (cursor.moveToNext()) {
			String username = cursor.getString(1);
			String password = cursor.getString(2);
			rowitem.add(new User(username, password));
		}
		cursor.close();
		db.close();
		dbHelper.close();
		
	}
	
	private void removeUserFromList(final int position){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		String username = adapter.getItem(position).getUsername();
		System.out.println(position);
		System.out.println(username);
		
		//Create the alert dialog
		alert.setTitle("Delete").
		setMessage("Do you want to delete this user?").
		setPositiveButton("YES", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				String username = adapter.getItem(position).getUsername();
				dbHelper = new MyDBHelper(UserListActivity.this);
				db = dbHelper.getWritableDatabase(DBConstants.SECRET_KEY);
				db.delete(DBConstants.TABLE_NAME, DBConstants.USERNAME_COLUMN +
						" = " + "'" + username + "'", null);
				
				rowitem.remove(position);
				dbHelper.close();
				db.close();
				
				setListAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		})
		.setNegativeButton("NO", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.dismiss();
			}
		});
		
		alert.show();
		
		
	}
}
