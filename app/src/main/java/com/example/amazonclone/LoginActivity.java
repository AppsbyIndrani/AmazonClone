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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    LinearLayout signuptext;
    EditText logemail,logpwd;
    Button btnSignIn;
    FirebaseAuth mauth;
   // LinearLayout  otpll;
    ProgressDialog progressDialog;
    String imageUri,emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitializeFields();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                OnSignInBtnClicked();

            }
        });

        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });



    }

    private void OnSignInBtnClicked()
    {

         progressDialog.show();

         String email=logemail.getEditableText().toString();
         String pwd=logpwd.getEditableText().toString();

         if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd))
         {
             progressDialog.dismiss();
             Toast.makeText(this, "Please fill the empty fields", Toast.LENGTH_SHORT).show();


         }else if (!pwd.matches(emailpattern))
         {

             progressDialog.dismiss();
             logemail.setError("Invalid email");
             Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();

         }else if (pwd.length()<=6)
         {
             progressDialog.dismiss();
             logpwd.setError("Invalid password");
             Toast.makeText(this, "Please enter more than 6 characters", Toast.LENGTH_SHORT).show();

         }
         else
         {
             mauth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task)
                 {

                     if (task.isSuccessful())
                     {
                         progressDialog.dismiss();
                         startActivity(new Intent(LoginActivity.this,HomeActivity.class));

                     }
                     else
                     {
                         progressDialog.dismiss();
                         Toast.makeText(LoginActivity.this, "Error in login,Please try again", Toast.LENGTH_SHORT).show();
                     }

                 }
             });
         }

    }

    private void InitializeFields()
    {
        signuptext=findViewById(R.id.signUpText);
        logemail=findViewById(R.id.loginEmail);
        logpwd=findViewById(R.id.loginPassword);
        btnSignIn=findViewById(R.id.signInButton);


        mauth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

    }
}