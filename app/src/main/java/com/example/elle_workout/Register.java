package com.example.elle_workout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {
    Button CreateAcc;
    EditText Name;
    EditText password;
    EditText email;
    EditText UniReg;
    EditText Mobile;

    ProgressDialog loadingbar;
    FirebaseAuth auth;

   // DatabaseReference RootRef;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAcc = (Button) findViewById(R.id.create);
        Name = (EditText) findViewById(R.id.PlayerName);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        UniReg = (EditText) findViewById(R.id.uniReg);
        Mobile = (EditText) findViewById(R.id.PhoneNo);
        loadingbar = new ProgressDialog(this);
        fstore=FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();



        CreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //CreateAccount();
                String inNAME = Name.getText().toString();
                String inPASSWORD = password.getText().toString();
                String inEMAIL= email.getText().toString();
                String inUNIREG = UniReg.getText().toString();
                String inPHONE = Mobile.getText().toString();

                if(TextUtils.isEmpty(inNAME))
                {
                    Toast.makeText(Register.this, "Name is empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(inPASSWORD))
                {
                    Toast.makeText(Register.this, "Password is empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(inEMAIL))
                {
                    Toast.makeText(Register.this, "Email is empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(inUNIREG))
                {
                    Toast.makeText(Register.this, "University Registration  is empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(inPHONE))
                {
                    Toast.makeText(Register.this, "Phone number is empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingbar.setTitle("Create Account");
                    loadingbar.setMessage("Please wailt...");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();

                    //ValidateEMAIL(inNAME,inPASSWORD,inEMAIL,inUNIREG,inPHONE);
                    auth.createUserWithEmailAndPassword(inEMAIL,inPASSWORD).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            DocumentReference Doc = fstore.collection("Users").document(auth.getCurrentUser().getUid());
                            Map<String,Object> Userinfo= new HashMap<>();
                            Userinfo.put("name",inNAME);
                            Userinfo.put("Password",inPASSWORD);
                            Userinfo.put("email",inEMAIL);
                            Userinfo.put("Uni.Reg",inUNIREG);
                            Userinfo.put("phone",inPHONE);

                            Doc.set(Userinfo);
                            startActivity(new Intent(getApplicationContext(),Login.class));
                            finish();
                        }
                    });

                }

            }

        });


    }
    //public void CreateAccount()
    //{


   /* private void ValidateEMAIL(String inNAME, String inPASSWORD, String inEMAIL, String inUNIREG, String inPHONE)
    {
       // final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(!(snapshot.child("Users").child(inEMAIL).exists()))
                {
                    HashMap<String,Object> userdataMap=new HashMap<>();
                    userdataMap.put("Player name",inNAME);
                    userdataMap.put("Password",inPASSWORD);
                    userdataMap.put("Email",inEMAIL);
                    userdataMap.put("Reg.No",inUNIREG);
                    userdataMap.put("Phone",inPHONE);

                    RootRef.child("Users").child(inEMAIL).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();

                                        Intent intent= new Intent(Register.this,Login.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingbar.dismiss();
                                        Toast.makeText(Register.this, "There is some error. Please try again later...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                else
                {
                    Toast.makeText(Register.this, "This email already exist", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(Register.this, "Please try gain using another email", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}