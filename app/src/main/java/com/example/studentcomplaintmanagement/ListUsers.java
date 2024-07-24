package com.example.studentcomplaintmanagement;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.studentcomplaintmanagement.dao.DAO;
import com.example.studentcomplaintmanagement.form.User;
import com.example.studentcomplaintmanagement.util.Constants;
import com.example.studentcomplaintmanagement.util.Session;

public class ListUsers extends AppCompatActivity {

    ListView listView;
    Button back;

    RadioGroup radioGroup;
    RadioButton radioButton;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        listView=(ListView) findViewById(R.id.UsersList);
        button = (Button) findViewById(R.id.viewUsersList);

        final Session s=new Session(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
                int selectedType=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedType);
                final String type=radioButton.getText().toString();

                DAO dao=new DAO();
                dao.setDataToAdapterList(listView,User.class, Constants.USER_DB,type);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = listView.getItemAtPosition(i).toString();
                Intent intent=new Intent(getApplicationContext(),ViewUser.class);
                intent.putExtra("userid", item);
                startActivity(intent);
            }
        });
    }
}
