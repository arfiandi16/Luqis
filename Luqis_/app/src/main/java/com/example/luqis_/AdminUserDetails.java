package com.example.luqis_;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luqis_.Model.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserDetails extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName,edtPass,edtPhone;
    private Button btnUpdate,btnDelete;

    public static final String EXTRA_MEMBER = "Extra Member";
    public static final int ALERT_DIALOG_CLOSE = 10;
    public static final int ALERT_DIALOG_DELETE = 20;

    private Users users;
    private String userPhone;

    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_details);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtName = findViewById(R.id.edt_edit_Name);
        edtPass = findViewById(R.id.edt_edit_Pass);
        edtPhone = findViewById(R.id.edt_edit_Phone);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(ALERT_DIALOG_DELETE);
            }
        });

        btnUpdate.setOnClickListener(this);

        users = getIntent().getParcelableExtra(EXTRA_MEMBER);

        if(users!=null){
            userPhone = users.getPhone();
        }
        else{
            users = new Users();
        }

        if(users!=null){
            edtName.setText(users.getName());
            edtPass.setText(users.getPassword());
            edtPhone.setText(users.getPhone());
        }

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Edit data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_update){
            updateMember();
        }
    }

    private void updateMember(){
        String name = edtName.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();

        boolean isEmpty = false;

        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pass) || TextUtils.isEmpty(phone)){
            isEmpty=true;
            edtName.setError("Maaf data tidak boleh kosong");
            edtPass.setError("Maaf data tidak boleh kosong");
            edtPhone.setError("Maaf data tidak boleh kosong");
        }
        if(!isEmpty){
            Toast.makeText(AdminUserDetails.this,"Updating data..", Toast.LENGTH_LONG).show();

            users.setName(name);
            users.setPassword(pass);
            users.setPhone(phone);

            DatabaseReference dbMember = mDatabase.child("Users");

            dbMember.child(userPhone).setValue(users);
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //pilih menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form";
        } else {
            dialogTitle = "Hapus Data";
            dialogMessage = "Apakah anda yakin ingin menghapus item ini";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (isDialogClose) {
                            finish();
                        } else {
                            DatabaseReference dbMember =
                                    mDatabase.child("Users").child(userPhone);

                            dbMember.removeValue();

                            Toast.makeText(AdminUserDetails.this, "Deleting data...",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}