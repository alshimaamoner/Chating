package com.example.pojochat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pojochat.Base.BaseActivity;
import com.example.pojochat.FirebaseUnit.ChatDao;
import com.example.pojochat.FirebaseUnit.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class Login extends BaseActivity {
    private ImageView mBackground;
    private TextView mLogo;
    private TextView mLogin;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mSignIn;
    private TextView mForgetPass;
    private Button mNewAccount;
    String email,pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        mNewAccount.setOnClickListener(onNewAccountListener);
        mForgetPass.setOnClickListener(onForgetListener);
        mSignIn.setOnClickListener(onSignListener);

    }
View.OnClickListener onNewAccountListener=new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(activity,Register.class));
    }
};
    View.OnClickListener onForgetListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(activity,FindYourAccount.class));
        }
    };
    View.OnClickListener onSignListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

           email=mEmail.getEditText().getText().toString();
           pass=mPassword.getEditText().getText().toString();
            if(TextUtils.isEmpty(email)){
                mEmail.setError("Enter Your Email");
            }
            if(TextUtils.isEmpty(pass)){
                mPassword.setError("Enter Your Password");
            }
           showProgressBar(R.string.Loading);
            MyDataBase.firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    hideProgressBar();
                    if(task.isSuccessful()){
                        startActivity(new Intent(activity,ChatList.class));
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(),"EMAIL OR PASS Is INVALID",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    };

    private void init() {
        mBackground = findViewById(R.id.background);
        mLogo = findViewById(R.id.logo);
        mLogin = findViewById(R.id.login);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mSignIn = findViewById(R.id.SignIn);
        mForgetPass = findViewById(R.id.forgetPass);
        mNewAccount = findViewById(R.id.newAccount);
    }
}