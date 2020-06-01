package com.studios.bihim.casherlessshopndub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.util.Timer;
import java.util.TimerTask;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ExitScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
//
    ZXingScannerView ScannerView;
    TimerTask timerTask;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }


    @Override
    public void handleResult(Result result)
    {
        timer = new Timer();

        String exitResult = result.getText();

        if (exitResult.equals("leave"))
        {
           Exit.scan.setVisibility(View.GONE);
           Exit.linearLayout.setVisibility(View.VISIBLE);
           Exit.header.setText("Do you want to exit?");

           Exit.yes.setOnClickListener(new View.OnClickListener() {   //Do you want to exit yes here firebase code implies
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference("Door").child("Status").setValue("Open").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ExitScanner.this, "Door is open", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ExitScanner.this, MainActivity.class));
                        }
                    });

                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            FirebaseDatabase.getInstance().getReference("Door").child("Status").setValue("Close").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ExitScanner.this, "Door is closed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    };

                    timer.schedule(timerTask, 10*1000);
                }
            });

           Exit.no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Exit.header.setText("Scan QR Code to Exit");
                    Exit.linearLayout.setVisibility(View.GONE);
                    Exit.scan.setVisibility(View.VISIBLE);
                }
            });
        }

        else
        {
            Exit.header.setText("QR Not Matched, Scan Again");
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
