package com.example.amazonclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.amazonclone.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    Button btnReg;
    EditText edUserName,edPwd,edEmail,edConfirmPwd;
    FirebaseAuth mauth;
    FirebaseStorage mstorage;
    FirebaseDatabase mdatabase;
    LinearLayout signInText;
    ProgressDialog progressDialog;
    String imageUri,emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitializeFields();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                onBtnSignUpClicked();

            }
        });

        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });
    }

    private void InitializeFields()
    {
        edUserName=findViewById(R.id.regUsername);
        edEmail=findViewById(R.id.regEmail);
        edPwd=findViewById(R.id.regPwd);
        edConfirmPwd=findViewById(R.id.regConfirmPwd);
        btnReg=findViewById(R.id.btnSignUp);
        signInText=findViewById(R.id.signInText);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);


        mauth=FirebaseAuth.getInstance();
        mdatabase=FirebaseDatabase.getInstance();
        mstorage=FirebaseStorage.getInstance();

    }

    private void onBtnSignUpClicked()
    {

           progressDialog.show();
           String name=edUserName.getEditableText().toString();
           String email=edEmail.getEditableText().toString();
           String pwd=edPwd.getEditableText().toString();
           String confirmpwd=edConfirmPwd.getEditableText().toString();

           if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)
            || TextUtils.isEmpty(confirmpwd))
           {

               progressDialog.dismiss();
               Toast.makeText(RegisterActivity.this, "Enter valid data",
                       Toast.LENGTH_SHORT).show();

           }else if (!email.matches(emailpattern))
           {
               progressDialog.dismiss();
               edEmail.setError("Invalid email");
               Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
               
           }else if (pwd.length()<=6)
           {
               progressDialog.dismiss();
               edPwd.setError("Invalid passwors");
               Toast.makeText(this, "Please enter more than 6 characters", Toast.LENGTH_SHORT).show();

           }else if (!pwd.equals(confirmpwd))
           {
               progressDialog.dismiss();
               Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();

           }else
           {
               mauth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task)
                   {
                       if (task.isSuccessful())
                       {
                           DatabaseReference dbref=mdatabase.getReference().child("users").
                                   child(Objects.requireNonNull(mauth.getCurrentUser()).getUid());
                           StorageReference sref=mstorage.getReference().child("upload").child(mauth.getCurrentUser().getUid());

                           imageUri="https://firebasestorage.googleapis.com/v0/b/cloneapp-f23d9.appspot.com/o/profile.png?alt=media&token=2374bfbd-b215-4b04-8803-3e597912a714";

                         Users users=new Users(mauth.getCurrentUser().getUid(),name,email,imageUri);

                         dbref.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task)
                             {
                                 if (task.isSuccessful())
                                 {

                                     progressDialog.dismiss();
                                     startActivity(new Intent(RegisterActivity.this,HomeActivity.class));


                                 }
                                 else
                                 {
                                     progressDialog.dismiss();
                                     Toast.makeText(RegisterActivity.this, "error in creating a user", Toast.LENGTH_SHORT).show();
                                 }

                             }
                         });

                       }
                       else
                       {
                           progressDialog.dismiss();
                           Toast.makeText(RegisterActivity.this, "Something get wrong", Toast.LENGTH_SHORT).show();
                       }

                   }

               });
           }

    }
}