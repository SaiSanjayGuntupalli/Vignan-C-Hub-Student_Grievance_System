package com.example.studentcomplaintmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentcomplaintmanagement.dao.DAO;
import com.example.studentcomplaintmanagement.form.User;
import com.example.studentcomplaintmanagement.util.Constants;

public class AddCoordinator extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5;

    RadioGroup rr1;
    RadioButton r1;

    Spinner spinner;

    String branch;

    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_coordinator);

        e1=(EditText)findViewById(R.id.coordinatorusername);
        e2=(EditText)findViewById(R.id.coordinatoremail);
        e3=(EditText)findViewById(R.id.coordinatormobile);
        e4=(EditText)findViewById(R.id.coordinatorpassword);
        e5=(EditText)findViewById(R.id.coordinatorconformpassword);

        rr1=(RadioGroup)findViewById(R.id.radiogroupgender2);

        spinner=findViewById(R.id.coordinatordepartment);
        String[] departments=new String[]{"sports","academic","exams","hostel","transport","canteen","library","placements","ragging","events","infrastructure","labs"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,departments);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branch=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        b1=(Button)findViewById(R.id.registerButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=e1.getText().toString();
                String usermail=e2.getText().toString();
                String usermobile=e3.getText().toString();
                String userpass=e4.getText().toString();
                String userconpass=e5.getText().toString();

                String gender="";
                int selectedGender=rr1.getCheckedRadioButtonId();
                r1=(RadioButton)findViewById(selectedGender);
                gender=r1.getText().toString();

                if(userpass==null||userconpass==null||usermail==null||usermobile==null||username==null||branch==null||gender==null)
                {
                    Toast.makeText(getApplicationContext(),"Missing Inputs",Toast.LENGTH_SHORT).show();
                }
                else if(usermobile.length()<10||usermobile.length()>12)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Mobile",Toast.LENGTH_SHORT).show();
                }
                else if(!userpass.equals(userconpass))
                {
                    Toast.makeText(getApplicationContext(),"Password Mismatch",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    User user=new User();

                    user.setUsername(username);
                    user.setEmail(usermail);
                    user.setMobile(usermobile);
                    user.setGender(gender);
                    user.setBranch(branch);
                    user.setPassword(userpass);
                    user.setType("coordinator");
                    user.setSection("");
                    user.setYear(0);

                    DAO d=new DAO();

                    try
                    {
                        d.addObject(Constants.USER_DB,user,user.getUsername());
                        Toast.makeText(getApplicationContext(),"Register Success",Toast.LENGTH_SHORT).show();

                        Intent i=new Intent(getApplicationContext(),AdminHome.class);
                        startActivity(i);
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(getApplicationContext(),"Register Error",Toast.LENGTH_SHORT).show();
                        Log.v("Coordinator Reg Ex", ex.toString());
                        ex.printStackTrace();
                    }

                }
            }
        });
    }
}

