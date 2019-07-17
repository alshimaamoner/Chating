package com.example.pojochat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pojochat.Base.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatList extends BaseActivity {


    private Toolbar mToolbar;
    private RecyclerView mGroupChat;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        init();
       mFab.setOnClickListener(onClickListener);


    }
View.OnClickListener onClickListener=new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(ChatList.this,AddRoom.class));

    }
};
    private void init() {
        mToolbar = findViewById(R.id.toolbar);
        mGroupChat = findViewById(R.id.groupChat);
        mFab = findViewById(R.id.fab);
    }

}