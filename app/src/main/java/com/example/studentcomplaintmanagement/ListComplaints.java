package com.example.studentcomplaintmanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.studentcomplaintmanagement.dao.DAO;
import com.example.studentcomplaintmanagement.form.Complaint;
import com.example.studentcomplaintmanagement.util.Constants;
import com.example.studentcomplaintmanagement.util.Session;
import com.example.studentcomplaintmanagement.util.MapUtil;

public class ListComplaints extends AppCompatActivity {

    ListView listView;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_complaints);

        listView=(ListView) findViewById(R.id.ComplaintsList);

        final Session s=new Session(getApplicationContext());

        final DAO dao=new DAO();

        if(s.getRole().equals("admin"))
        {
            dao.setDataToAdapterList(listView, Complaint.class, Constants.COMPLAINTS_DB,s.getusename());
        }else
        {
            dao.setDataToAdapterList(listView, Complaint.class, Constants.COMPLAINTS_DB,s.getusename());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = listView.getItemAtPosition(i).toString();
                item= MapUtil.stringToMap(s.getViewMap()).get(item);

                Intent intent=new Intent(getApplicationContext(),ViewComplaint.class);
                intent.putExtra("complaintid",item);
                startActivity(intent);
            }
        });
    }
}
