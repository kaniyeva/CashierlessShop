package com.studios.bihim.casherlessshopndub;


import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    //private TextView textView, userName, headerUsername, headerEmail;
    //private Button button, camera;

    private CardView enter_shop, exit_shop, history, purchase;

    private String userID;

    private FirebaseAuth mAuth;
    /*private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private ListView mListView;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logIn();
       /* userInfo();*/
        /*barcode();*/
        cardViewClick();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.profile_toolbar:
                startActivity(new Intent(MainActivity.this, Profile.class));
                break;

            case R.id.logout_toolbar:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this, LoginNDUB.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void cardViewClick()
    {
        enter_shop = findViewById(R.id.enter_shop_cardView);
        exit_shop  = findViewById(R.id.exit_shop_cardview);
        history    = findViewById(R.id.history_cardview);
        purchase   = findViewById(R.id.purchase_cardview);

        enter_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Enter.class));
            }
        });

        exit_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Exit.class));
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, History.class));
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Purchase.class));
            }
        });
    }


  /*  private void userInfo()
    {
        mListView = findViewById(R.id.listView);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Authentication", "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("Authentication", "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

    /*private void showData(DataSnapshot dataSnapshot)
    {
        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            User user = new User();
            user.setUsername(ds.child(userID).getValue(User.class).getUsername());
            user.setEmail(ds.child(userID).getValue(User.class).getEmail());
            user.setFullname(ds.child(userID).getValue(User.class).getFullname());

            ArrayList<String> array = new ArrayList<>();
            array.add(user.getUsername());
            array.add(user.getFullname());
            array.add(user.getEmail());







            //userName = findViewById(R.id.userNameTest);


           *//* userName.setText(user.getUsername());
            headerUsername.setText(user.getUsername());*//*

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
            mListView.setAdapter(adapter);
        }
    }*/


    /*private void barcode()
    {
        camera = findViewById(R.id.camera);
        result_main = findViewById(R.id.barcode_text);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EnterScanner.class));
            }
        });
    }*/

    private void logIn()
    {
        //textView = findViewById(R.id.textView);
        //button = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginNDUB.class));
        }

     /*   FirebaseUser firebaseUser = mAuth.getCurrentUser();
        userID = firebaseUser.getUid();*/

        //textView.setText("Welcome "+firebaseUser.getEmail());

/*

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this, LoginNDUB.class));
            }
        });
*/



    }


   /* @Override
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
    }*/

   /* private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }*/


}
