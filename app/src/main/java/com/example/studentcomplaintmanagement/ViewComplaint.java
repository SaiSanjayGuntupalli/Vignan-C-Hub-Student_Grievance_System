package com.example.studentcomplaintmanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.studentcomplaintmanagement.dao.DAO;
import com.example.studentcomplaintmanagement.form.Complaint;
import com.example.studentcomplaintmanagement.util.Constants;
import com.example.studentcomplaintmanagement.util.Session;

public class ViewComplaint extends AppCompatActivity {

    Button menuDeleteComplaint;
    Button viewComplaintBack;
    Button viewComplaintUpdate;

    TextView t1,t2,t3,t4,t5,t6,t7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);

        menuDeleteComplaint=(Button) findViewById(R.id.menuDeleteComplaint);
        viewComplaintBack=(Button) findViewById(R.id.viewComplaintBack);
        viewComplaintUpdate=(Button) findViewById(R.id.viewComplaintUpdate);

        t1=(TextView) findViewById(R.id.complaintviewname);
        t2=(TextView)findViewById(R.id.complaintviewdescription);
        t3=(TextView)findViewById(R.id.complaintviewdate);
        t4=(TextView)findViewById(R.id.complaintviewstatus);
        t5=(TextView)findViewById(R.id.complaintviewsolution);
        t6=(TextView)findViewById(R.id.complaintviewstudent);
        t7=(TextView)findViewById(R.id.complaintviewfaculty);

        final Session s = new Session(getApplicationContext());

        Intent i = getIntent();
        savedInstanceState = i.getExtras();
        final String complaintid = savedInstanceState.getString("complaintid");

        String role=s.getRole();

        if(role.equals("admin") || role.equals("student") )
        {
            viewComplaintUpdate.setEnabled(false);
        }

        if(role.equals("faculty") || role.equals("coordinator") )
        {
            menuDeleteComplaint.setEnabled(false);
        }

        DAO d=new DAO();
        d.getDBReference(Constants.COMPLAINTS_DB).child(complaintid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Complaint complaint=dataSnapshot.getValue(Complaint.class);

                if(complaint!=null)
                {
                    t1.setText("Complaint Name :"+complaint.getComplaintName());
                    t2.setText("Complaint Description :"+complaint.getComplaintDescription());
                    t3.setText("Complaint Date :"+complaint.getCompaintDate());
                    t4.setText("Complaint Status :"+complaint.getComplaintStatus());
                    t5.setText("Complaint Solution :"+complaint.getComplaintSolution());

                    if(s.getRole().equals("admin"))
                    {
                        t6.setText("Complaint Status :"+complaint.getComplentedBy());
                    }
                    else
                    {
                        t6.setText("");
                    }

                    t7.setText("Complaint Solution :"+complaint.getComplaintTo());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        menuDeleteComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DAO dao=new DAO();
                dao.deleteObject(Constants.COMPLAINTS_DB,complaintid);

                Intent i=null;

                String role=s.getRole();

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

        viewComplaintBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=null;

                String role=s.getRole();

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

        viewComplaintUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),UpdateComplaint.class);
                intent.putExtra("complaintid",complaintid);
                startActivity(intent);
            }
        });
    }
}
