package com.studios.bihim.casherlessshopndub;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
//
public class History extends AppCompatActivity {
    ListView listView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();
        listViewAdd();
    }

    private void listViewAdd()
    {
        listView = findViewById(R.id.listView_history);
        textView = findViewById(R.id.notAvailable);

        final ArrayList<String> array = new ArrayList<>();

        //table header and table items

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("order").exists())
                {
                    String firebaseGettingHistory = dataSnapshot.child("order").getValue().toString();
                    String[] historySplitByDateSemicolon = firebaseGettingHistory.split(";");
                    for (String colon: historySplitByDateSemicolon)
                    {
                        String[] historySplitByComma = colon.split(",");
                        for(String comma: historySplitByComma)
                        {
                            if (comma.contains("Date"))
                            {
                                array.add("");
                            }
                            array.add(comma);
                        }

                    }

                    array.remove(0);
                    ArrayAdapter adapter = new ArrayAdapter(History.this, android.R.layout.simple_list_item_1, array);
                    listView.setAdapter(adapter);

                }

                else
                {
                    textView.setText("You haven't purchased yet");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
