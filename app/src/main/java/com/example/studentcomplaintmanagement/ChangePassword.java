package com.example.studentcomplaintmanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentcomplaintmanagement.form.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.studentcomplaintmanagement.dao.DAO;
import com.example.studentcomplaintmanagement.util.Constants;
import com.example.studentcomplaintmanagement.util.Session;

public class ChangePassword extends AppCompatActivity {

    EditText et1,et2,et3;
    Button updatePasswordSubmit;
    Button updatePasswordCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password);

        final Session s = new Session(getApplicationContext());

        et1 = (EditText) findViewById(R.id.updatepasswordold);
        et2 = (EditText) findViewById(R.id.updatepasswordnew);
        et3 = (EditText) findViewById(R.id.updatepasswordconform);

        updatePasswordSubmit = (Button) findViewById(R.id.updatePasswordSubmit);
        updatePasswordCancel = (Button) findViewById(R.id.updatePasswordCancel);


        updatePasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldp=et1.getText().toString();
                String newp=et2.getText().toString();
                String conp=et3.getText().toString();

                if (oldp == null && newp==null && conp==null) {
                    Toast.makeText(getApplicationContext(), "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
                }
                if (! newp.equals(conp)) {
                    Toast.makeText(getApplicationContext(), "Password is not matching", Toast.LENGTH_SHORT).show();
                }
                else {

                    final DAO d=new DAO();
                    d.getDBReference(Constants.USER_DB).child(s.getusename()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            User user =dataSnapshot.getValue(User.class);

                            if(user!=null)
                            {
                                if(user.getPassword().equals(oldp))
                                {
                                    user.setPassword(newp);

                                    d.addObject(Constants.USER_DB,user,user.getUsername());

                                    Intent i = new Intent(getApplicationContext(),UserHome.class);
                                    startActivity(i);
                                }
                                else {

                                    s.loggingOut();
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        updatePasswordCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),UserHome.class);
                startActivity(i);
            }
        });
    }
}
