package com.example.login;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tools.User;

public class UserAdapter extends BaseAdapter{
	private Context mContext = null;
	private List<User> mUser = new ArrayList<User>();
	
	public UserAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}
	
	public UserAdapter(Context context, List<User> user){
		mContext = context;
		mUser = user;
	}
	
	public Context getContext() {
		return mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mUser.size();
	}

	@Override
	public User getItem(int arg0) {
		// TODO Auto-generated method stub
		return mUser.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Viewholder holder;
		if(convertView  == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.useritem, null);
			holder = new Viewholder();
			holder.usernameTv = (TextView) convertView.findViewById(R.id.username_textview);
			holder.passwordTv = (TextView) convertView.findViewById(R.id.password_textview);
			convertView.setTag(holder);
		}else {
			holder = (Viewholder) convertView.getTag();
		}
		User item = (User) getItem(position);
		
		holder.usernameTv.setText(item.getUsername());
		holder.passwordTv.setText(item.getPassword());
		
		return convertView;
	}
	private class Viewholder{
		TextView usernameTv;
		TextView passwordTv;
	}
	
}
