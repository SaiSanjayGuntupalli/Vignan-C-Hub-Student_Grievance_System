package com.example.studentcomplaintmanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.studentcomplaintmanagement.dao.DAO;
import com.example.studentcomplaintmanagement.form.User;
import com.example.studentcomplaintmanagement.util.Constants;
import com.example.studentcomplaintmanagement.util.Session;

public class ViewUser extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6,t7;
    Button cancel;
    Button postComplaint;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        cancel=(Button) findViewById(R.id.viewUserCanel);
        postComplaint=(Button) findViewById(R.id.postComplaint);
        delete=(Button) findViewById(R.id.viewUserDelete);

        final Session session=new Session(getApplicationContext());

        String role=session.getRole();

        Log.v("login user role :",role);

        if(role.equals("faculty") || role.equals("coordinator") || role.equals("student"))
        {
            delete.setEnabled(false);
        }

        if(role.equals("faculty") || role.equals("coordinator") || role.equals("admin"))
        {
            postComplaint.setEnabled(false);
        }

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String userId=savedInstanceState.getString("userid");

        Log.v("in view user userid ",userId);

        t1=(TextView) findViewById(R.id.textviewname);
        t2=(TextView)findViewById(R.id.textviewemail);
        t3=(TextView)findViewById(R.id.textviewmobile);
        t4=(TextView)findViewById(R.id.textviewgender);
        t5=(TextView)findViewById(R.id.textviewbranch);
        t6=(TextView)findViewById(R.id.textviewyear);
        t7=(TextView)findViewById(R.id.textviewsection);

        DAO d=new DAO();
        d.getDBReference(Constants.USER_DB).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user=dataSnapshot.getValue(User.class);

                if(user!=null)
                {
                    String type=user.getType();

                    if(type.equals("student"))
                    {
                        t1.setText("Roll Number:"+ user.getUsername());
                    }else if(type.equals("faculty"))
                    {
                        t1.setText("Faculty Name :"+ user.getUsername());
                    }else if(type.equals("coordinator"))
                    {
                        t1.setText("Coordinator Name :"+ user.getUsername());
                    }

                    t2.setText("Email :"+user.getEmail());
                    t3.setText("Mobile :"+user.getMobile());
                    t4.setText("Gender :"+user.getGender());
                    t5.setText("Branch :"+user.getBranch());
                    t6.setText("Year :"+user.getYear());
                    t7.setText("Section :"+user.getSection());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=null;

                String role=session.getRole();

                if(role.equals("admin"))
                {
                    i= new Intent(getApplicationContext(),AdminHome.class);
                }
                else
                {
                    i= new Intent(getApplicationContext(),UserHome.class);
                }

                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DAO d=new DAO();
                d.deleteObject(Constants.USER_DB,userId);

                Intent i=null;

                String role=session.getRole();

                if(role.equals("admin"))
                {
                    i= new Intent(getApplicationContext(),AdminHome.class);
                }
                else
                {
                    i= new Intent(getApplicationContext(),UserHome.class);
                }

                startActivity(i);
            }
        });

        postComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(getApplicationContext(),AddComplaint.class);
                i.putExtra("complaintto",userId);
                startActivity(i);
            }
        });
    }
}
