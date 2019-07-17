package com.example.pojochat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pojochat.Base.BaseActivity;
import com.example.pojochat.FirebaseUnit.ChatDao;
import com.example.pojochat.Model.ChatRoom;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AddRoom extends BaseActivity {

    private ImageView mBackground;
    private TextView mTitle;
    private TextInputLayout mGroupname;
    private TextInputLayout mDetails;
    private Button mAdd;
    String group_name,details,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        init();
        mAdd.setOnClickListener(onAddListener);

    }

    View.OnClickListener onAddListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showProgressBar(R.string.Loading);
            group_name=mGroupname.getEditText().getText().toString();
            details=mDetails.getEditText().getText().toString();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
            date=simpleDateFormat.format(new Date());
            ChatRoom chatRoom=new ChatRoom(group_name,details,date);
            ChatDao.AddRoom(chatRoom,onSuccessListener,onFailureListener);


        }
    };
    OnSuccessListener onSuccessListener= new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
            hideProgressBar();
            showConfirmationMessage(R.string.success, R.string.Group_Created, R.string.ok, new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    //startActivity(new Intent(AddRoom.this,ChatList.class));
                    finish();
                }
            });
        }
    };
    OnFailureListener onFailureListener=new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            hideProgressBar();
            showMessage("Fail",e.getLocalizedMessage(),"Ok");

        }
    };
    private void init() {

        mBackground = findViewById(R.id.background);
        mTitle = findViewById(R.id.title);
        mGroupname = findViewById(R.id.groupname);
        mDetails = findViewById(R.id.details);
        mAdd = findViewById(R.id.Add);
    }

}
