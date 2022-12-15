package com.example.amazonclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amazonclone.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hbb20.CountryCodePicker;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OtpLoginActivity extends AppCompatActivity {

    CountryCodePicker countyp;
    EditText edphno,edcode,edname,edemail;
    Button btnSend,btnLogin;
    ConstraintLayout cllogin,clsendotp;
    FirebaseAuth mauth;
    FirebaseStorage mstorage;
    FirebaseDatabase mdatabase;
    ProgressDialog progressdialog;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    String mVerificationId,imageUri,emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    PhoneAuthProvider.ForceResendingToken forceresendingtoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);

        mauth=FirebaseAuth.getInstance();
        mdatabase= FirebaseDatabase.getInstance();
        mstorage=FirebaseStorage.getInstance();


        countyp=findViewById(R.id.countrycode);
        edphno=findViewById(R.id.edPhoneNo);
        btnSend=findViewById(R.id.btnSendOtp);

        cllogin=findViewById(R.id.clLogin);
        edcode=findViewById(R.id.edVCode);
        btnLogin=findViewById(R.id.btnSubmit);
        clsendotp=findViewById(R.id.clSendOtp);
        edname=findViewById(R.id.edOtpUsername);
        edemail=findViewById(R.id.edOtpEmail);


        countyp.registerCarrierNumberEditText(edphno);

        progressdialog=new ProgressDialog(this);
        progressdialog.setTitle("Please wait");
        progressdialog.setCanceledOnTouchOutside(false);


       btnSend.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {

               String cc=countyp.getSelectedCountryCodeWithPlus();
               String phno=edphno.getText().toString();
               String phone=cc+phno;

               if (TextUtils.isEmpty(phone))
               {
                   Toast.makeText(OtpLoginActivity.this,"phone number is required",Toast.LENGTH_SHORT).show();
               }
               else
               {


                   progressdialog.setMessage("Verifying phone number");
                   progressdialog.show();

                   PhoneAuthProvider.getInstance().verifyPhoneNumber(
                           phone,        // Phone number to verify
                           60,                 // Timeout duration
                           TimeUnit.SECONDS,   // Unit of timeout
                           OtpLoginActivity.this,               // Activity (for callback binding)
                           mCallBacks);
               }

           }
       });


        mCallBacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential,edname.getText().toString(),edemail.getText().toString());
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressdialog.dismiss();
                Toast.makeText(OtpLoginActivity.this, "Invalid phone number,please enter correct phone number with your country code", Toast.LENGTH_SHORT).show();


            }

            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                forceresendingtoken=token;

                progressdialog.dismiss();

                clsendotp.setVisibility(View.GONE);
                cllogin.setVisibility(View.VISIBLE);

                Toast.makeText(OtpLoginActivity.this,"Verification code sent successfully",
                        Toast.LENGTH_SHORT).show();


            }



        };



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

               String code=edcode.getText().toString();
               String name=edname.getText().toString();
               String email=edemail.getText().toString();


                if (!TextUtils.isEmpty(code) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && email.matches(emailpattern) )
                {
                    verifyphonenowithcode(mVerificationId,code,name,email);
                }
                else
                {
                    Toast.makeText(OtpLoginActivity.this,"Please write the code sent to the number and enter valid data",
                        Toast.LENGTH_SHORT).show();
                }

            }
        });



}

    private void verifyphonenowithcode(String mVerificationId, String code,String name,String email)
    {

        progressdialog.setMessage("Verifying code....");
        progressdialog.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
        signInWithPhoneAuthCredential(credential, name,email);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential, String s, String e)
    {

        progressdialog.setMessage("Logging In");


        mauth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            String currentUserID=mauth.getCurrentUser().getUid();

                            progressdialog.dismiss();
                            sendUserToMainActivity(currentUserID,s,e);
                            Toast.makeText(OtpLoginActivity.this,"You are loggedIn successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String message=task.getException().toString();
                            Toast.makeText(OtpLoginActivity.this,"Error:" + message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void sendUserToMainActivity(String currentUserID, String s, String e)
    {

        DatabaseReference dbref=mdatabase.getReference().child("users").
                child(Objects.requireNonNull(mauth.getCurrentUser()).getUid());
        StorageReference sref=mstorage.getReference().child("upload").child(mauth.getCurrentUser().getUid());

        imageUri="https://firebasestorage.googleapis.com/v0/b/cloneapp-f23d9.appspot.com/o/profile.png?alt=media&token=2374bfbd-b215-4b04-8803-3e597912a714";

        Users users=new Users(mauth.getCurrentUser().getUid(),s,e,imageUri);

        dbref.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {

                    startActivity(new Intent(OtpLoginActivity.this,HomeActivity.class));


                }
                else
                {
                    Toast.makeText(OtpLoginActivity.this, "error in creating a user", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }




}


