package com.example.studentcomplaintmanagement;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

import com.example.studentcomplaintmanagement.dao.DAO;
import com.example.studentcomplaintmanagement.form.Complaint;
import com.example.studentcomplaintmanagement.form.User;
import com.example.studentcomplaintmanagement.util.Constants;
import com.example.studentcomplaintmanagement.util.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AddComplaint extends AppCompatActivity {

    EditText addComplaintName;
    EditText addComplaintDescription;

    Button addComplaintSubmit;
    Button addComplaintCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_complaint);

        final Session s = new Session(getApplicationContext());

        addComplaintName = (EditText) findViewById(R.id.addComplaintName);
        addComplaintDescription = (EditText) findViewById(R.id.addComplaintDescription);

        addComplaintSubmit = (Button) findViewById(R.id.addComplaintSubmit);
        addComplaintCancel = (Button) findViewById(R.id.addComplaintCancel);

        Intent i=getIntent();
        savedInstanceState=i.getExtras();

        final String complaintto=savedInstanceState.getString("complaintto");

        addComplaintSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = addComplaintName.getText().toString();
                String description = addComplaintDescription.getText().toString();

                if (name == null || description == null) {
                    Toast.makeText(getApplicationContext(), "Please Enter Valid Data", Toast.LENGTH_SHORT).show();
                } else {

                    String imageName = UUID.randomUUID().toString();

                    DAO dao = new DAO();

                    Complaint complaint = new Complaint();
                    complaint.setComplaintId(dao.getUnicKey(Constants.COMPLAINTS_DB));
                    complaint.setComplaintName(name);
                    complaint.setComplaintDescription(description);
                    complaint.setComplentedBy(s.getusename());
                    complaint.setComplaintTo(complaintto);
                    complaint.setCompaintDate(new java.util.Date().toString());
                    complaint.setComplaintStatus("open");
                    complaint.setComplaintSolution("");

                    try {

                        final DAO d=new DAO();
                        d.getDBReference(Constants.USER_DB).child(complaintto).addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                User user =dataSnapshot.getValue(User.class);

                                if(user!=null)
                                {
                                    dao.addObject(Constants.COMPLAINTS_DB, complaint, complaint.getComplaintId());

                                    Intent intent=new Intent(getApplicationContext(),UserHome.class);
                                    PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,PendingIntent.FLAG_MUTABLE);

                                    ArrayList<PendingIntent> pendingIntents=new ArrayList<>();
                                    pendingIntents.add(pi);

                                    //Get the SmsManager instance and call the sendTextMessage method to send message
                                    SmsManager sms=SmsManager.getDefault();

                                    ArrayList<String> parts = sms.divideMessage("You Had Received New Complaint");
                                    //smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    sms.sendMultipartTextMessage(user.getMobile(), null, parts,
                                            pendingIntents, null);

                                    Toast.makeText(getApplicationContext(), "Complaint Posted Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Complaint Failed", Toast.LENGTH_SHORT).show();
                        Log.v("complaint failed", ex.toString());
                        ex.printStackTrace();
                    }
                }
            }
        });

        addComplaintCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), UserHome.class);
                startActivity(i);
            }
        });
    }
}
