package com.example.pojochat.FirebaseUnit;

import com.example.pojochat.Model.ChatRoom;
import com.example.pojochat.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

public class UserDao {
    public static void insertUser(User user, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener){
        DatabaseReference UserNode= MyDataBase.getUserBranch().push();
        user.setId(UserNode.getKey());
        UserNode.setValue(user).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);


    }
}
