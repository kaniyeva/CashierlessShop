package com.studios.bihim.casherlessshopndub;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Enter extends AppCompatActivity {
    public static Button camera, enterShop;
    public static TextView result_main, scanQRcode;
    public static ImageView shopImage;
    private int CAMERA_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_exit);
        barcode();
    }


    private void barcode() {
        camera = findViewById(R.id.cameraEnter);
        scanQRcode = findViewById(R.id.scanQRcodeEnter);
        result_main = findViewById(R.id.shop_name);
        shopImage = findViewById(R.id.shop_image);
        enterShop = findViewById(R.id.enterShop);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraClick();
            }
        });
    }

    private void cameraClick()
    {
        if (ContextCompat.checkSelfPermission(Enter.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
        {
            startActivity(new Intent(getApplicationContext(), EnterScanner.class));
        }

        else
        {
            requestPermission();
        }
    }

    private void requestPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Camera Permission Needed")
                    .setMessage("Camera Permission needed for scan QR code")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            ActivityCompat.requestPermissions(Enter.this, new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            Toast.makeText(Enter.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }

        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE)
        {
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), EnterScanner.class));
            }
            else
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
