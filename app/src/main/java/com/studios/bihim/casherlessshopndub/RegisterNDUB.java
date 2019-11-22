package com.studios.bihim.casherlessshopndub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterNDUB extends AppCompatActivity {
    private EditText editTextusername, editTextfullname, editTextemail, editTextpassword, editTextconfirm_password;
    private Button register,loginClick;

    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private Intent intent,loginActivity;

    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ndub);

        editTextusername    = findViewById(R.id.username);
        editTextfullname    = findViewById(R.id.fullname);
        editTextemail       = findViewById(R.id.email);
        editTextpassword    = findViewById(R.id.password);
        editTextconfirm_password = findViewById(R.id.confirm_password);
        register    = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        loginClick = findViewById(R.id.loginClick);
        checkBox   = findViewById(R.id.ch);

        mAuth = FirebaseAuth.getInstance();
        intent = new Intent(this,MainActivity.class);
        loginActivity = new Intent(this,LoginNDUB.class);

        //str.matches(".*\\d+.*");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

     checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(isChecked)
             {
                editTextpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                editTextconfirm_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
             }
             else
             {
                 editTextpassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                 editTextconfirm_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
             }
         }
     });

      loginClick.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
              startActivity(loginActivity);
          }
      });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }

    private void registerUser()
    {
        final String username = editTextusername.getText().toString().trim();
        final String fullname = editTextfullname.getText().toString().trim();
        final String email = editTextemail.getText().toString().trim();
        final String balance="1000";
        final String order = "order";
        String password = editTextpassword.getText().toString().trim();
        String confirm_password = editTextconfirm_password.getText().toString().trim();

        if (username.isEmpty()) {
            editTextusername.setError("Empty Username");
            editTextusername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextemail.setError("Empty Email");
            editTextemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextemail.setError("Invalid Email Address");
            editTextemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextpassword.setError("Empty Password");
            editTextpassword.requestFocus();
            return;
        }

        if (confirm_password.isEmpty()) {
            editTextconfirm_password.setError("Empty Confirm Password");
            editTextconfirm_password.requestFocus();
            return;
        }

        if(fullname.matches(".*\\d+.*"))
        {
            editTextfullname.setError("Invalid Fullname");
            editTextfullname.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextpassword.setError("Password must not less than 6 letters");
            editTextpassword.requestFocus();
            return;
        }

        if(!password.equals(confirm_password))
        {
            editTextconfirm_password.setError("Password doesn't match");
            editTextconfirm_password.requestFocus();
            return;
        }



        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            User user = new User(username,email,fullname,balance,order);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterNDUB.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        editTextusername.setText("");
                                        editTextpassword.setText("");
                                        editTextfullname.setText("");
                                        editTextemail.setText("");
                                        editTextconfirm_password.setText("");
                                        startActivity(intent);

                                    }

                                    else
                                    {
                                        Toast.makeText(RegisterNDUB.this, "Failure", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        else
                        {
                            Toast.makeText(RegisterNDUB.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
