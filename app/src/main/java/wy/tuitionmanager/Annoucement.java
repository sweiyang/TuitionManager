package wy.tuitionmanager;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Annoucement extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference root;
    private EditText getAnnoucement;
    private ArrayList<String> listofAnnouncement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annoucement);
        auth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference();
        getAnnoucement = (EditText) findViewById(R.id.annoucement);
        listofAnnouncement = new ArrayList<String>();
        root.child("Announcement").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    listofAnnouncement.add(d.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void postAnnoucement(View view){
        String annoucemenet = getAnnoucement.getText().toString();
        root.child("Announcement").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    Toast.makeText(Annoucement.this, "i am here", Toast.LENGTH_SHORT).show();
                    listofAnnouncement.add(d.getValue(String.class));
                    Toast.makeText(Annoucement.this, listofAnnouncement.get(0), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listofAnnouncement.add(annoucemenet);
        root.child("Announcement").setValue(listofAnnouncement);
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }
}
