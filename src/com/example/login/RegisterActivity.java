package com.example.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.db.MyDBHelper;
import com.example.tools.Register;
import com.example.tools.User;

public class RegisterActivity extends Activity {
	private EditText etUsername;
	private EditText etPassword;
	private EditText etPassword1;
	private Button btnRegister;
	
	private MyDBHelper dbHelper;
	private net.sqlcipher.database.SQLiteDatabase db;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
//		context = getApplicationContext();
//		dbHelper = new MyDBHelper(getApplicationContext());
		etUsername = (EditText) findViewById(R.id.username);
		etPassword = (EditText) findViewById(R.id.password);
		etPassword1 = (EditText) findViewById(R.id.password1);
		btnRegister = (Button) findViewById(R.id.btnregister);
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String username = etUsername.getText().toString();
				String pwd = etPassword.getText().toString();
				String pwd1 = etPassword1.getText().toString();
				
				
				if (TextUtils.isEmpty(etUsername.getText())) {
					Toast.makeText(RegisterActivity.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(TextUtils.isEmpty(etPassword.getText()) || TextUtils.isEmpty(etPassword1.getText())){
					Toast.makeText(RegisterActivity.this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(!pwd.equals(pwd1)){
					Toast.makeText(RegisterActivity.this, "Password should be the same!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				User user = new User(username, pwd);
				
				new Register(user, RegisterActivity.this, new Register.SuccessCallBack() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(RegisterActivity.this,"Sign up successfully!", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
					}
				}, new Register.FailCallBack() {
					
					@Override
					public void onFail() {
						// TODO Auto-generated method stub
						Toast.makeText(RegisterActivity.this, "Username already exists!", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
}
