package com.example.luqis_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.luqis_.Model.Users;
import com.example.luqis_.Model.UsersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCategoryActivity extends AppCompatActivity{
    private ImageView softFile, hardCoppy;
    private Button LogoutBtn, CheckOrdersBtn;

    private ListView listView;
    private Button btnAdd;

    private UsersAdapter adapter;
    private ArrayList<Users> userList;
    DatabaseReference dbMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,AdminUserDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        dbMember = FirebaseDatabase.getInstance().getReference("Users");
        listView = findViewById(R.id.listMember);

        userList = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminUserDetails.class);
                intent.putExtra(AdminUserDetails.EXTRA_MEMBER,userList.get(i));
                startActivity(intent);
            }
        });
    }


    @Override
    public void onStart(){
        super.onStart();
        dbMember.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                userList.clear();

                for(DataSnapshot memberSnap : ds.getChildren()){
                    Users member = memberSnap.getValue(Users.class);
                    userList.add(member);
                }

                UsersAdapter adapter = new UsersAdapter(AdminCategoryActivity.this);
                adapter.setUsersList(userList);
                listView.setAdapter(adapter);
                System.out.println(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminCategoryActivity.this,"Terjadi kesalahan..",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
