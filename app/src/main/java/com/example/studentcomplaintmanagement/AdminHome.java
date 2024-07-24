package com.example.studentcomplaintmanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.studentcomplaintmanagement.util.Session;

public class AdminHome extends AppCompatActivity {

    private Session session;

    Button addfaculty;
    Button addcoordinator;
    Button adminLogout;
    Button viewUsers;
    Button adminComplaintsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addfaculty=(Button) findViewById(R.id.addfaculty);
        addcoordinator=(Button) findViewById(R.id.addcoordinator);
        viewUsers=(Button) findViewById(R.id.adminviewusers);
        adminLogout=(Button) findViewById(R.id.adminlogout);
        adminComplaintsList=(Button) findViewById(R.id.adminComplaintsList);

        final Session s = new Session(getApplicationContext());

        addfaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AddFaculty.class);
                startActivity(i);
            }
        });

        addcoordinator.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), AddCoordinator.class);
                startActivity(i);
            }
        });

        viewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("in list view action ","");
                Intent i = new Intent(getApplicationContext(),ListUsers.class);
                startActivity(i);
            }
        });

        adminComplaintsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("in list view action ","");
                Intent i = new Intent(getApplicationContext(),ListComplaints.class);
                startActivity(i);
            }
        });

        adminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s.loggingOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}