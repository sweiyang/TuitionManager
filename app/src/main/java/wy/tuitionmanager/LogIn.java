package wy.tuitionmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText user;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        auth = FirebaseAuth.getInstance();
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        password.setTransformationMethod(new PasswordTransformationMethod());
    }

    public void gotoSignup(View view){
        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
    }

    public void login(View view){
        auth.signInWithEmailAndPassword(user.getText().toString(),password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LogIn.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogIn.this,Home.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LogIn.this, "Username or Password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
