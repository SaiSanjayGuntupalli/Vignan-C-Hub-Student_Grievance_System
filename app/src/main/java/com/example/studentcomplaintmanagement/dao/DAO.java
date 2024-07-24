package com.example.studentcomplaintmanagement.dao;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.studentcomplaintmanagement.form.Complaint;
import com.example.studentcomplaintmanagement.form.User;
import com.example.studentcomplaintmanagement.util.Constants;
import com.example.studentcomplaintmanagement.util.MapUtil;
import com.example.studentcomplaintmanagement.util.Session;

public class DAO
{
        public static DatabaseReference getDBReference(String dbName)
        {
            return GetFireBaseConnection.getConnection(dbName);
        }

        public String getUnicKey(String dbName)
        {
            return getDBReference(dbName).push().getKey();
        }

        public int addObject(String dbName,Object obj,String key) {

            int result=0;

            try {

                getDBReference(dbName).child(key).setValue(obj);

                result=1;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return result;
        }


        public void setDataToAdapterList(final View view, final Class c, final String dbname,final String username) {

            final ArrayList<String> arrayList=new ArrayList<String>();

            final Map<String,Object> map=new HashMap<String,Object>();
            final Map<String,String> viewMap=new HashMap<String,String>();

            getDBReference(dbname).addValueEventListener(new ValueEventListener() {
                int i=1;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshotNode: dataSnapshot.getChildren()) {

                         String id=snapshotNode.getKey();

                        Object object=snapshotNode.getValue(c);

                        if(dbname.equals(Constants.USER_DB)) {

                           User user=(User)object;

                           if(user.getType().equals(username)) {
                               arrayList.add(user.getUsername());
                           }
                        }

                        if(dbname.equals(Constants.COMPLAINTS_DB)) {

                            Complaint complaint=(Complaint) object;

                            if(complaint.getComplentedBy().equals(username))
                            {
                                viewMap.put(complaint.getComplaintName(),complaint.getComplaintId());
                            }
                            else if(username.equals("admin"))
                            {
                                viewMap.put(complaint.getComplaintName(),complaint.getComplaintId());
                            }
                            else if(username.equals(complaint.getComplaintTo()))
                            {
                                viewMap.put(complaint.getComplaintName(),complaint.getComplaintId());
                            }
                        }
                    }

                    if(view instanceof ListView) {

                        if(arrayList.size()!=0)
                        {
                            final ListView myView=(ListView)view;

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(myView.getContext(),
                                    android.R.layout.simple_list_item_1, (arrayList.toArray(new String[arrayList.size()])));

                            myView.setAdapter(adapter);
                        }
                        else
                        {
                            ArrayList<String> al=new ArrayList<String>(viewMap.keySet());

                            final ListView myView=(ListView)view;

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(myView.getContext(),
                                    android.R.layout.simple_list_item_1, (al.toArray(new String[al.size()])));

                            myView.setAdapter(adapter);
                        }
                    }

                    Session s=new Session(view.getContext());
                    s.setViewMap(MapUtil.mapToString(viewMap));
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

        }

        public int deleteObject(String dbName, String key) {

            int result=0;

            try {

                getDBReference(dbName).child(key).removeValue();

                result=1;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return 0;
        }
    }

