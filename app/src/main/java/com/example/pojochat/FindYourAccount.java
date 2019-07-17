package com.example.pojochat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.pojochat.Base.BaseActivity;
import com.example.pojochat.FirebaseUnit.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class FindYourAccount extends BaseActivity {

    private ImageButton mBack;
    private Toolbar mToolbar;
    private EditText mEmail;
    private TextView mHint;
    private Button mFindAccount;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_your_account);
        init();
       mBack.setOnClickListener(backListener);
       mFindAccount.setOnClickListener(FindAccountListener);
    }
View.OnClickListener backListener=new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        onBackPressed();
    }

    };
    View.OnClickListener FindAccountListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Email=mEmail.getText().toString();
            if(TextUtils.isEmpty(Email)){
                mEmail.setError("Enter Your Email");
                return;
            }
           showProgressBar(R.string.Searching);
            MyDataBase.firebaseAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        hideProgressBar();
                        startActivity(new Intent(activity,Login.class));
                        finish();
                    }else{
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(),"Enter Valid Email",Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    };

    private void init() {
        mBack = findViewById(R.id.back);
        mToolbar = findViewById(R.id.toolbar);
        mEmail = findViewById(R.id.email);
        mHint = findViewById(R.id.hint);
        mFindAccount = findViewById(R.id.findAccount);
    }
}
