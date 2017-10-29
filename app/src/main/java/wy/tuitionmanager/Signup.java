package wy.tuitionmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText name;
    private EditText IC;
    private EditText phonenumber;
    private EditText password;
    private DatabaseReference root;
    private ArrayList<User> userList;
    private EditText email;

    private String nameString;
    private String ICString;
    private String phonenumberString;
    private String passwordString;
    private String emailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.name);
        IC = (EditText) findViewById(R.id.identitynumber);
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        password = (EditText) findViewById(R.id.password);
        password.setTransformationMethod(new PasswordTransformationMethod());
        email = (EditText) findViewById(R.id.email);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");
        root = FirebaseDatabase.getInstance().getReference("User");

    }

    public void createUser(View view){
        nameString = name.getText().toString();
        ICString = IC.getText().toString();
        phonenumberString = phonenumber.getText().toString();
        passwordString = password.getText().toString();
        emailString = email.getText().toString();

        (mAuth.createUserWithEmailAndPassword(emailString, passwordString )).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Signup.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                    login();
                }
                else{
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    Toast.makeText(Signup.this, "Registration is unsuccessful" + "Error = " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void login(){
        mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Signup.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                    String user = mAuth.getCurrentUser().getUid();
                    root.child(user).setValue(new User(nameString,phonenumberString,emailString));
                    Intent intent = new Intent(Signup.this,Home.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Signup.this, "Username or Password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
