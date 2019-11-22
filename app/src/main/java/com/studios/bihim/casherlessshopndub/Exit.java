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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Exit extends AppCompatActivity {

    public static TextView header;
    public static Button scan, yes, no;
    public static LinearLayout linearLayout;
    private int CAMERA_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        exit();
    }

    private void exit()
    {
        header = findViewById(R.id.scanQRcodeExit);
        scan = findViewById(R.id.cameraExit);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        linearLayout = findViewById(R.id.YesNo);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraClick();

                //startActivity(new Intent(getApplicationContext(), ExitScanner.class));
            }
        });
    }

    private void cameraClick()
    {
        if (ContextCompat.checkSelfPermission(Exit.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
        {
            startActivity(new Intent(getApplicationContext(), ExitScanner.class));
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
                            ActivityCompat.requestPermissions(Exit.this, new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            Toast.makeText(Exit.this, "Permission Denied", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getApplicationContext(), ExitScanner.class));
            }
            else
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
