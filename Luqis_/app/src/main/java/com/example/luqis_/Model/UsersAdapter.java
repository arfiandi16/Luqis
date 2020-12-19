package com.example.luqis_.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.luqis_.R;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Users> UsersList = new ArrayList<>();

    public void setUsersList(ArrayList<Users> UsersList) {
        this.UsersList = UsersList;
    }

    public UsersAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return UsersList.size();
    }

    @Override
    public Object getItem(int i) {
        return UsersList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;

        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_member, viewGroup, false);
        }

        ViewHolder viewHolder = new ViewHolder(itemView);

        Users Users = (Users) getItem(i);
        viewHolder.bind(Users);
        return itemView;
    }

    private class ViewHolder {
        private TextView txtPhone, txtName;

        ViewHolder(View view) {
            txtName = view.findViewById(R.id.txt_name);
            txtPhone = view.findViewById(R.id.txt_phone);
        }

        void bind(Users Users) {
            txtName.setText(Users.getName());
            txtPhone.setText(Users.getPhone());
        }
    }
}