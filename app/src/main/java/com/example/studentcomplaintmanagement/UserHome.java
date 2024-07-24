package com.example.studentcomplaintmanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.studentcomplaintmanagement.util.Session;

public class UserHome extends AppCompatActivity {

    Button viewComplaintStatus;
    Button userlogout;
    Button viewUsers;
    Button changepassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        viewComplaintStatus=(Button) findViewById(R.id.viewComplaintStatus);
        userlogout=(Button) findViewById(R.id.userlogout);
        viewUsers=(Button) findViewById(R.id.viewUsers);
        changepassword=(Button) findViewById(R.id.userchangepassword);

        final Session s = new Session(getApplicationContext());

        String role=s.getRole();

        viewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("in list view action ","");
                Intent i = new Intent(getApplicationContext(),ListUsers.class);
                startActivity(i);
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),ChangePassword.class);
                startActivity(i);
            }
        });

        viewComplaintStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("in list view action ","");
                Intent i = new Intent(getApplicationContext(),ListComplaints.class);
                startActivity(i);
            }
        });

        userlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s.loggingOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
