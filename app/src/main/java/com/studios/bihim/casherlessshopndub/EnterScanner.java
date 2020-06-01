package com.studios.bihim.casherlessshopndub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.util.Timer;
import java.util.TimerTask;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class EnterScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
//-
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

        if(result.getText().equals("100"))
        {
            Enter.result_main.setVisibility(View.VISIBLE);
            Enter.shopImage.setVisibility(View.VISIBLE);
            Enter.result_main.setText("Agora");
            imageViewMethod("@drawable/agora");

        }

        else if(result.getText().equals("101"))
        {
            Enter.result_main.setVisibility(View.VISIBLE);
            Enter.shopImage.setVisibility(View.VISIBLE);
            Enter.result_main.setText("Meena");
            imageViewMethod("@drawable/meena");
        }

        else if(result.getText().equals("102"))
        {
            Enter.result_main.setVisibility(View.VISIBLE);
            Enter.shopImage.setVisibility(View.VISIBLE);
            Enter.result_main.setText("Mustafa");
            imageViewMethod("@drawable/mustafa");
        }

        else if(result.getText().equals("103"))
        {
            Enter.result_main.setVisibility(View.VISIBLE);
            Enter.shopImage.setVisibility(View.VISIBLE);
            Enter.result_main.setText("Plus Point");
            imageViewMethod("@drawable/pluspoint");
        }

        else if(result.getText().equals("104"))
        {
            Enter.result_main.setVisibility(View.VISIBLE);
            Enter.shopImage.setVisibility(View.VISIBLE);
            Enter.result_main.setText("Shawpno");
            imageViewMethod("@drawable/shopno");
        }

        else
        {
            Enter.result_main.setVisibility(View.VISIBLE);
            Enter.result_main.setText("This "+result+" is not registered as a shop");
            Enter.shopImage.setVisibility(View.GONE);
        }

        onBackPressed();

        Enter.enterShop.setVisibility(View.VISIBLE);
        Enter.scanQRcode.setVisibility(View.GONE);
        Enter.camera.setVisibility(View.GONE);

        Enter.enterShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Door").child("Status").setValue("Open").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EnterScanner.this, "Door is open", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EnterScanner.this, MainActivity.class));
                    }
                });
                //after certain amount of time, door will close
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        FirebaseDatabase.getInstance().getReference("Door").child("Status").setValue("Close").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EnterScanner.this, "Door is closed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                };

                timer.schedule(timerTask, 10*1000);
            }
        });

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

    private void imageViewMethod(String drawable)
    {
        int imageResource = getResources().getIdentifier(drawable,null,this.getPackageName());
        Enter.shopImage.setImageResource(imageResource);
    }
}
