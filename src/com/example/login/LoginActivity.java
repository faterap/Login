package com.example.login;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.db.MyDBHelper;
import com.example.tools.Login;
import com.example.tools.User;

public class LoginActivity extends Activity implements OnClickListener{
	private static final int RESULT_STATUS_SUCCESS = 1;
	private static final int RESULT_STATUS_FAIL = 0;

	private Context context;
	
	private EditText etUsername;
	private EditText etPassword;
	private Button btnLogin;
	private Button btnRegister;
	private ProgressDialog mDialog;
	private MyDBHelper dbHelper;
	
//	private TestHandler mHandler;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		SQLiteDatabase.loadLibs(this);
		
		mDialog = new ProgressDialog(LoginActivity.this);
		mDialog.setCancelable(false);
		
		etUsername = (EditText) findViewById(R.id.username);
		etPassword = (EditText) findViewById(R.id.password);
		btnLogin = (Button) findViewById(R.id.btnlogin);
		btnLogin.setOnClickListener(LoginActivity.this);
		btnRegister = (Button) findViewById(R.id.btnregister);
		btnRegister.setOnClickListener(LoginActivity.this);
		
		dbHelper = new MyDBHelper(getApplicationContext());
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btnlogin:
			final String username = etUsername.getText().toString();
			final String password = etPassword.getText().toString();
			User user = new User(username, password);
			
			if(TextUtils.isEmpty(etUsername.getText())){
				Toast.makeText(LoginActivity.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(TextUtils.isEmpty(etPassword.getText())){
				Toast.makeText(LoginActivity.this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
				return;
			}
			
			
			new Login(user,LoginActivity.this, new Login.SuccessCallBack() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					if(username.equals("admin") && password.equals("admin")){
						mDialog.setMessage("Logging in...");
						mDialog.show();
						new Handler().postDelayed(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								mDialog.dismiss();
								startActivity(new Intent(LoginActivity.this, UserListActivity.class));
								clear();
							}
						}, 1000);
			}
			else{
				mDialog.setMessage("Logging in...");
				mDialog.show();
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mDialog.dismiss();
						startActivity(new Intent(LoginActivity.this, DemoActivity.class));
						clear();
					}
				},1000);
			}
					
				}
			}, new Login.FailCallBack() {
				
				@Override
				public void onFail() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Username and password do not match! Please try again.", 
							Toast.LENGTH_SHORT).show();
				}
			});
			
			break;
		case R.id.btnregister:
			Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
			startActivity(i);
			
			break;

		default:
			break;
		}
	}
	
	private void clear(){
		etUsername.setText("");
		etPassword.setText("");
	}
	
}
