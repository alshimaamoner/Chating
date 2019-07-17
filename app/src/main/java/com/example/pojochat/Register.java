package com.example.pojochat;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pojochat.Base.BaseActivity;
import com.example.pojochat.FirebaseUnit.MyDataBase;
import com.example.pojochat.FirebaseUnit.UserDao;
import com.example.pojochat.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends BaseActivity {
    private ImageView mBackground;
    String imageUr="";
    User user;
    private TextView mRegister;
    String name,password,email,phonne;
    private FirebaseAuth firebaseAuth;
    private TextInputLayout mUsername;
    private TextInputLayout mTelephone;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mSignUp;
    FirebaseStorage storage;
    boolean temp=true;
    private static int RESULT_LOAD_IMAGE = 1;
    private CircleImageView mUser;
    Uri selectedImage=Uri.parse("E:\\PojoChat\\app\\src\\main\\res\\drawable-nodpi\\users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        mUser.setOnClickListener(uploadClick);
       mSignUp.setOnClickListener(onClickListener);
    }

   View.OnClickListener uploadClick= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);



        }
    };
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            name=mUsername.getEditText().getText().toString();
            password=mPassword.getEditText().getText().toString();
            phonne=mTelephone.getEditText().getText().toString();
            email=mEmail.getEditText().getText().toString();
            if(!name.isEmpty()&& !password.isEmpty()&&!phonne.isEmpty()&&!email.isEmpty()){

                if(phonne.length()!=11) {
                    mTelephone.getEditText().setError("enter valid phone");
                }
                if(!isEmailValid(email)){
                    mEmail.getEditText().setError("enter valid email");

                }
                if(password.length()<8 && !isValidPassword(password)) {
                    mPassword.getEditText().setError("Enter Your password Length at least nine numbers or characters");
                }


            }else {
                mUsername.getEditText().setError("Enter your User");
                mTelephone.getEditText().setError("Enter your phone");
                mEmail.getEditText().setError("Enter your email");
                mPassword.getEditText().setError("Enter your pass");

            }
           if(phonne.length()==11 && isEmailValid(email) &&password.length()>8 ) {
                showProgressBar(R.string.Loading);

                MyDataBase.firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new Register(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        showProgressBar(R.string.loading);
                        // Toast.makeText(Register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            hideProgressBar();
                            Toast.makeText(Register.this, "Email is exist" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            if (selectedImage.toString().isEmpty()) {
                               user = new User(name, phonne, email, password, selectedImage.toString());
                            } else { user = new User(name, phonne, email, password, selectedImage.toString());
                            }

                            UserDao.insertUser(user, onSuccessListener, onFailureListener);

                        }

                    }
                });
            }
        }
    };
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
OnSuccessListener onSuccessListener=new OnSuccessListener() {
    @Override
    public void onSuccess(Object o) {
        showConfirmationMessage(R.string.Success, R.string.User_Added, R.string.ok, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
              // hideProgressBar();
               // startActivity(new Intent(Register.this,Login.class));
                finish();
            }
        });
    }
};
    OnFailureListener onFailureListener=new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
           // hideProgressBar();
            showMessage("fail",e.getLocalizedMessage(),"ok");
        }
    };

    public void init() {
        mBackground = findViewById(R.id.background);
        mUser = findViewById(R.id.user);
        mRegister = findViewById(R.id.register);
        mUsername = findViewById(R.id.username);
        mTelephone = findViewById(R.id.telephone);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mSignUp = findViewById(R.id.SignUp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
           // Picasso.with(this).load(selectedImage).into(mUser);
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            cursor.close();


            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mUser.setImageBitmap(bmp);
           // uploadFile(bmp);
        }
    }
    Bitmap image;
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
public  String getFileExtension(Uri uri){
    ContentResolver cR=getContentResolver();
    MimeTypeMap mime=MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(cR.getType(uri));
}

 /*   private String storeImageToFirebase() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // shrink it down otherwise we will use stupid amounts of memory
       // Bitmap bitmap = BitmapFactory.decodeFile(selectedImage, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
       image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
   return  base64Image;
        // we finally have our base64 string version of the image, save it.
      //  storage.child("pic").setValue(base64Image);
      //  System.out.println("Stored image with length: " + bytes.length);
    }

*/
}

