package com.studios.bihim.casherlessshopndub;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ListView mListView;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        userInfo();

    }

    private void userInfo()
    {
        mListView = findViewById(R.id.listView);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        DatabaseReference profileUserReference= FirebaseDatabase.getInstance().getReference().child("Users").child(userID);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    /*Log.d("Authentication", "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());*/
                } else {
                    // User is signed out
                    /*Log.d("Authentication", "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");*/
                }
            }
        };

        profileUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //showData(dataSnapshot);
                if (dataSnapshot.exists())
                {
                    String userName = dataSnapshot.child("username").getValue().toString();
                    String fullName = dataSnapshot.child("fullname").getValue().toString();
                    String email    = dataSnapshot.child("email").getValue().toString();
                    String balance  = dataSnapshot.child("balance").getValue().toString();

                    ArrayList<String> array = new ArrayList<>();
                    array.add("Fullname: "+userName);
                    array.add("Username: "+fullName);
                    array.add("Email: "+email);
                    array.add("Balance: "+balance+" Tk");

                    ArrayAdapter adapter = new ArrayAdapter(Profile.this, android.R.layout.simple_list_item_1, array);
                    mListView.setAdapter(adapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
