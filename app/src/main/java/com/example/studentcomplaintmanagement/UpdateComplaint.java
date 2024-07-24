package com.example.studentcomplaintmanagement;

import android.app.PendingIntent;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentcomplaintmanagement.form.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.example.studentcomplaintmanagement.dao.DAO;
import com.example.studentcomplaintmanagement.form.Complaint;
import com.example.studentcomplaintmanagement.util.Constants;
import com.example.studentcomplaintmanagement.util.Session;

import java.util.ArrayList;

public class UpdateComplaint extends AppCompatActivity {

    EditText updateComplaintStatus;
    EditText updateComplaintSolution;
    Button updateComplaintSubmit;
    Button updateComplaintCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_complaint);

        final Session s = new Session(getApplicationContext());

        updateComplaintStatus = (EditText) findViewById(R.id.updateComplaintStatus);
        updateComplaintSolution = (EditText) findViewById(R.id.updateComplaintSolution);

        updateComplaintSubmit = (Button) findViewById(R.id.updateComplaintSubmit);
        updateComplaintCancel = (Button) findViewById(R.id.updateComplaintCancel);

        Intent i = getIntent();
        savedInstanceState = i.getExtras();

        final String complaintid = savedInstanceState.getString("complaintid");

        updateComplaintSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String status = updateComplaintStatus.getText().toString();
                String solution=updateComplaintSolution.getText().toString();

                if (status == null && solution==null) {
                    Toast.makeText(getApplicationContext(), "Please Enter Complaint Status", Toast.LENGTH_SHORT).show();
                } else {

                    updateComplaint(complaintid,status,solution);

                    Intent i = new Intent(getApplicationContext(),UserHome.class);
                    startActivity(i);
                }
            }
        });

        updateComplaintCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),UserHome.class);
                startActivity(i);
            }
        });
    }

    private void updateComplaint(final String complaintId, final String status,final String solution)
    {
        final DAO d=new DAO();
        d.getDBReference(Constants.COMPLAINTS_DB).child(complaintId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Complaint complaint=dataSnapshot.getValue(Complaint.class);

                if(complaint!=null)
                {
                    complaint.setComplaintStatus(status);
                    complaint.setComplaintSolution(solution);
                }

                d.addObject(Constants.COMPLAINTS_DB,complaint,complaintId);

                try {

                    final DAO d=new DAO();
                    d.getDBReference(Constants.USER_DB).child(complaint.getComplentedBy()).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            User user =dataSnapshot.getValue(User.class);

                            if(user!=null)
                            {
                                Intent intent=new Intent(getApplicationContext(),UserHome.class);
                                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,PendingIntent.FLAG_MUTABLE);

                                ArrayList<PendingIntent> pendingIntents=new ArrayList<>();
                                pendingIntents.add(pi);

                                //Get the SmsManager instance and call the sendTextMessage method to send message
                                SmsManager sms=SmsManager.getDefault();

                                ArrayList<String> parts = sms.divideMessage("Your Complaint Status Updated");
                                //smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                sms.sendMultipartTextMessage(user.getPassword(), null, parts,
                                        pendingIntents, null);

                                Toast.makeText(getApplicationContext(), "Complaint Updated Successfully", Toast.LENGTH_SHORT).show();
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
