package com.example.pojochat.FirebaseUnit;

import com.example.pojochat.Model.ChatRoom;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

public class ChatDao {
    public static void AddRoom(ChatRoom chatRoom, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener){
      DatabaseReference RoomNode= MyDataBase.getRoomBranch().push();
      chatRoom.setId(RoomNode.getKey());
      RoomNode.setValue(chatRoom).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);


    }
}
