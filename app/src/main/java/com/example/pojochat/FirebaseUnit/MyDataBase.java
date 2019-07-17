package com.example.pojochat.FirebaseUnit;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class MyDataBase {
    private static FirebaseDatabase firebaseDatabase;
    private static FirebaseStorage firebaseStorage;
    public static FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    public static FirebaseDatabase getInstance(){
        if(firebaseDatabase==null )
            firebaseDatabase = FirebaseDatabase.getInstance();


        return firebaseDatabase;
    }
    public  static DatabaseReference getUserBranch(){
        return getInstance().getReference("user");
    }
    public  static DatabaseReference getRoomBranch(){
        return getInstance().getReference("room");
    }
    public static FirebaseStorage getStorageInstance(){
        if(firebaseStorage==null )
            firebaseStorage = FirebaseStorage.getInstance();
        return firebaseStorage;
    }
    public  static StorageReference getStorageBranch(){
        return getStorageInstance().getReference("user");
    }

}
