package com.example.studentcomplaintmanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.studentcomplaintmanagement.dao.DAO;
import com.example.studentcomplaintmanagement.form.User;
import com.example.studentcomplaintmanagement.util.Constants;
import com.example.studentcomplaintmanagement.util.Session;

public class LoginActivity extends AppCompatActivity {

    private Session session;
    EditText e1,e2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1 = (EditText) findViewById(R.id.loginmail);
        e2 = (EditText) findViewById(R.id.loginpass);
        b1 = (Button) findViewById(R.id.loginButton);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = e1.getText().toString();
                final String password = e2.getText().toString();

                if (username == null || password == null || username.length() <= 0 || password.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Please Enter UserName and Password", Toast.LENGTH_SHORT).show();
                } else {

                    if(username.equals("admin") && password.equals("admin"))
                    {
                        session = new Session(getApplicationContext());

                        session.setusename("admin");
                        session.setRole("admin");

                        Intent i = new Intent(getApplicationContext(), AdminHome.class);
                        startActivity(i);
                    }
                    else {

                        DAO d = new DAO();

                        d.getDBReference(Constants.USER_DB).child(username).addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                User user = dataSnapshot.getValue(User.class);

                                if (user == null) {

                                    Toast.makeText(getApplicationContext(), "Invalid UserName ", Toast.LENGTH_SHORT).show();

                                } else if (user.getPassword().equals(password)) {
                                    //global variable
                                    session = new Session(getApplicationContext());

                                    session.setusename(user.getUsername());

                                    Toast.makeText(getApplicationContext(), user.getType(), Toast.LENGTH_SHORT).show();

                                    session.setRole(user.getType());

                                    Intent i = new Intent(getApplicationContext(), UserHome.class);
                                    startActivity(i);

                                } else {
                                    Toast.makeText(getApplicationContext(), "In valid Password", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                            }
                        });
                    }
                }
            }
        });
    }
}
