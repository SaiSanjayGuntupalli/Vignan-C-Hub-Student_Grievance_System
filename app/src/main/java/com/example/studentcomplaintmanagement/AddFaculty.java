package com.example.studentcomplaintmanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.studentcomplaintmanagement.dao.DAO;
import com.example.studentcomplaintmanagement.form.User;
import com.example.studentcomplaintmanagement.util.Constants;

public class AddFaculty extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6;
    RadioGroup rr1,rg2,rg3;
    RadioButton r1,r2,r3,r4,r5,r6,r7,r8,r9,r10;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_faculty);

        e1=(EditText)findViewById(R.id.registerUserName1);
        e2=(EditText)findViewById(R.id.registerUserEmail1);
        e3=(EditText)findViewById(R.id.registerUserMobile1);
        e4=(EditText)findViewById(R.id.registerUserPass1);
        e5=(EditText)findViewById(R.id.registerUserConPass1);
        e6=(EditText)findViewById(R.id.registerUserSection1);

        rr1=(RadioGroup)findViewById(R.id.radiogroupgender1);
        rg2=(RadioGroup) findViewById(R.id.radiogroupbranch1);
        rg3=(RadioGroup) findViewById(R.id.radiogroupyear1);


        b1=(Button)findViewById(R.id.registerButton1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=e1.getText().toString();
                String usermail=e2.getText().toString();
                String usermobile=e3.getText().toString();
                String userpass=e4.getText().toString();
                String userconpass=e5.getText().toString();
                String usersection=e6.getText().toString();

                String gender="";
                int selectedGender=rr1.getCheckedRadioButtonId();
                r1=(RadioButton)findViewById(selectedGender);
                gender=r1.getText().toString();

                String branch="";
                int selectedBranch=rg2.getCheckedRadioButtonId();
                r1=(RadioButton)findViewById(selectedBranch);
                branch=r1.getText().toString();

                int year=1;
                int selectedYear=rg3.getCheckedRadioButtonId();
                r1=(RadioButton)findViewById(selectedYear);
                year=Integer.parseInt(r1.getText().toString());

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
                    user.setYear(year);
                    user.setSection(usersection);
                    user.setPassword(userpass);
                    user.setType("faculty");

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
                        Log.v("Faculty Registration Ex", ex.toString());
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}

