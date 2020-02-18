package com.bnk.comapny.bnksys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bnk.comapny.bnksys.model.User;

public class StartActivity extends AppCompatActivity {
    Button button;
    EditText username;
    EditText usersalary;
    EditText usermoney;
    String userField;
    public static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Spinner spinner =findViewById(R.id.like);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userField = parent.getItemAtPosition(position)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button = findViewById(R.id.loginbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=findViewById(R.id.username);
                usersalary=findViewById(R.id.salary);
                usermoney=findViewById(R.id.money);
                if(usermoney.equals(""))
                {

                }
                else if(usersalary.equals(""))
                {

                }
                else if(usermoney.equals(""))
                {

                }
                else if(userField.equals(""))
                {

                }
                else{
                    user = new User(username.getText()+"",Integer.parseInt(usersalary.getText()+""),Integer.parseInt(usermoney.getText()+""),userField);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
