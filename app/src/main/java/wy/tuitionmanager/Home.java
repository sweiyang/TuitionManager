package wy.tuitionmanager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    private DatabaseReference root;
    private String user;
    private ListView listofannoucement;
    private ListView listofpromotions;
    private ArrayList<String> listofAnnoucement;
    private dataListAdapter listAdapter;
    private ArrayList<String> listofAdmin;
    private boolean isAdmin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        auth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference();
        user = auth.getCurrentUser().getUid();
        listofannoucement = (ListView) findViewById(R.id.listofannoucement);
        listofpromotions = (ListView) findViewById(R.id.listofpromotion);
        listofAnnoucement = new ArrayList<String>();
        listofAdmin = new ArrayList<String>();

        listAdapter = new dataListAdapter(listofAnnoucement);
        listofannoucement.setAdapter(listAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ifAdmin();
        root.child("User/" + user + "/admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isAdmin = dataSnapshot.getValue(Boolean.class);
                if(!isAdmin){
                    View b = findViewById(R.id.addAnnoucement);
                    b.setVisibility(View.GONE);
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        root.child("Announcement").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listofAnnoucement.clear();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    listofAnnoucement.add(d.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ifAdmin(){
        root.child("User/" + user + "/admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isAdmin = dataSnapshot.getValue(Boolean.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(this,user, Toast.LENGTH_SHORT).show();
    }

    public void postAnnouncement(View view){
        Intent intent = new Intent(this,Annoucement.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Home) {
            // Handle the camera action
        }
        else if(id == R.id.Profile){
            Intent intent = new Intent(this,Profile.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class dataListAdapter extends BaseAdapter {
        ArrayList<String> Title = new ArrayList<String>();

        dataListAdapter() {
            Title = null;

        }

        public dataListAdapter(ArrayList<String> text) {
            Title = text;


        }

        public int getCount() {
            // TODO Auto-generated method stub
            return Title.size();
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.custom, parent, false);
            TextView title;
            title = (TextView) row.findViewById(R.id.title);
            title.setText(Title.get(position));


            return (row);
        }
    }
}
