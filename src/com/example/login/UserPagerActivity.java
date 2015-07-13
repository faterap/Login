package com.example.login;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sqlcipher.database.SQLiteDatabase;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.db.MyDBHelper;
import com.example.tools.Register;
import com.example.tools.User;

public class UserPagerActivity extends Activity {
	private String path = "";
	
	private ImageView iv;
	private EditText nicknameEt;
	private EditText usernameEt;
	private EditText passwordEt;
	private Button adduserBtn;

	private MyDBHelper dbHelper;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pager);

		nicknameEt = (EditText) findViewById(R.id.new_nickname);
		usernameEt = (EditText) findViewById(R.id.new_username);
		passwordEt = (EditText) findViewById(R.id.new_password);
		iv = (ImageView) findViewById(R.id.icon_iv);
		iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 1);
			}
		});

		adduserBtn = (Button) findViewById(R.id.new_summitBtn);
		adduserBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String nickname = nicknameEt.getText().toString();
				String username = usernameEt.getText().toString();
				String password = passwordEt.getText().toString();
				
				User user = new User(username, password);

				new Register(user,
						UserPagerActivity.this, new Register.SuccessCallBack() {

							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub
								Toast.makeText(UserPagerActivity.this,
										"The user was successfully added!",
										Toast.LENGTH_SHORT).show();
								startActivity(new Intent(
										UserPagerActivity.this,
										UserListActivity.class));
							}
						}, new Register.FailCallBack() {

							@Override
							public void onFail() {
								// TODO Auto-generated method stub
								Toast.makeText(UserPagerActivity.this,
										"Username already exists!",
										Toast.LENGTH_SHORT).show();
							}
						});
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {	
		case 1:
			if(resultCode == RESULT_OK){
				try {
					Uri imageUri = data.getData();
					ContentResolver contentResolver = getContentResolver();
//					InputStream imageStream = getContentResolver().openInputStream(imageUri);
//					Bitmap selectedBitmap = BitmapFactory.decodeStream(imageStream);
//					iv.setImageBitmap(selectedBitmap);
					Bitmap bm = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
					iv.setImageBitmap(bm);
					
					String[] proj = {MediaStore.Images.Media.DATA};
					Cursor cursor = managedQuery(imageUri, null, null, null, null);
					cursor.moveToFirst();
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					path = cursor.getString(column_index);
					System.out.println("image path --------- " + path);
							
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			break;

		default:
			break;
		}
	}
	
	
}
