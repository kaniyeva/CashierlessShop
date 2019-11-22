package com.studios.bihim.casherlessshopndub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class PurchaseScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannerView;
    String balance_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result)
    {
        Purchase.camera.setVisibility(View.GONE);
        Purchase.scanQRcode.setVisibility(View.GONE);

        Purchase.cardView.setVisibility(View.VISIBLE);
        Purchase.linearLayout.setVisibility(View.VISIBLE);

        String scanResult = result.getText();
        String[] splet = scanResult.split(";");
        String dummy;


        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

        String date = localDateTime.format(dateTimeFormatter);

        final ArrayList<String> array = new ArrayList<>();

        array.add("Date "+date);
        for(String item: splet)
        {
            final int total;
            dummy = item.replaceAll(","," ");
            array.add(dummy+" tk");
            String[] splet2 = item.split(",");
            for (String all: splet)
            {
                splet2 = all.split(",");
            }

            total = Integer.parseInt(splet2[1]);
            Log.d("total", "handleResult: "+total);


            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);

            Purchase.listView.setAdapter(adapter);

            Purchase.yes.setOnClickListener(new View.OnClickListener() {  //if purchased
                @Override
                public void onClick(View v)
                {
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            balance_string = dataSnapshot.child("balance").getValue().toString();
                            int balance = Integer.parseInt(balance_string);
                            Purchase.something = balance_string;
                            Log.d("bg", "onDataChange: " + balance_string);


                            if (total < balance)
                            {
                                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("balance").setValue(balance - total);
                                Toast.makeText(PurchaseScanner.this, "Purchased Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PurchaseScanner.this, MainActivity.class));

                                FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                    {
                                        if (dataSnapshot.child("order").exists())
                                        {
                                            String previousHistory = dataSnapshot.child("order").getValue().toString();
                                            String historyToFirebase = Arrays.toString(array.toArray()).replaceAll("\\[","").replaceAll("]","")+";";

                                            String togetherHistory = previousHistory+historyToFirebase;

                                            FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("order").setValue(togetherHistory); //history order section and if history exists
                                        }

                                        else
                                        {
                                            String historyToFirebase = Arrays.toString(array.toArray()).replaceAll("\\[","").replaceAll("]","")+";";
                                            FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .child("order").setValue(historyToFirebase); //history order section and if history doesn't exists
                                        }



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                            else
                                {
                                Toast.makeText(PurchaseScanner.this, "You have insufficient balance", Toast.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });


                        }
                    });

            Purchase.no.setOnClickListener(new View.OnClickListener() {  //if not purchased
                @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(PurchaseScanner.this, MainActivity.class));

                }
            });

        }

        onBackPressed();


    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }





}
