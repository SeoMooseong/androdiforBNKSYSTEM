package com.bnk.comapny.bnksys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bnk.comapny.bnksys.SQL.DataAdapter;
import com.bnk.comapny.bnksys.model.Apartment;
import com.bnk.comapny.bnksys.model.User;
import com.bnk.comapny.bnksys.util.NumberFormat;

import java.util.List;

public class StartActivity extends AppCompatActivity {
    Button button;
    EditText username;
    EditText usersalary;
    EditText usermoney;
    String userField;
    public static User user;
    public static List<Apartment> recommandList;
    private void initLoadDB(User user){
        DataAdapter mDbHelper = new DataAdapter(getApplicationContext());
        mDbHelper.open();
        double salaryYY = (user.getSalaryM()*12)/10000;
        double priM = salaryYY*LoadingActivity.pirList.get(LoadingActivity.pirList.size()-1).getpLocal();

        System.out.println("지역구 들어옴 : "+user.getField());
        recommandList = mDbHelper.getTableDateW(user.getField(),0.5*priM,priM);
        mDbHelper.close();
    }
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

        username=findViewById(R.id.username);
        usersalary=findViewById(R.id.salary);
        usermoney=findViewById(R.id.money);

//        username.setText("홍길동");
//        usersalary.setText("3,600,000");
//        usermoney.setText("56,000,000");

        usersalary.addTextChangedListener(new NumberFormat(usersalary));
        usermoney.addTextChangedListener(new NumberFormat(usermoney));

        button = findViewById(R.id.loginbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    user = new User(username.getText()+"",Integer.parseInt(usersalary.getText().toString().replace(",","")),Integer.parseInt(usermoney.getText().toString().replace(",","")),userField);
                    initLoadDB(user);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
