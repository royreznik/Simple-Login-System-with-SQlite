package com.rr.royreznik.trysqllite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//Login Page Activity

public class LoginActivity extends AppCompatActivity {


    EditText userLog, passLog;
    Button loginButton;
    DBHelper db;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DBHelper(this);

        userLog = (EditText)findViewById(R.id.UserLogin);
        passLog = (EditText)findViewById(R.id.PassLogin);
        loginButton = (Button)findViewById(R.id.btnLogin);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User a = new User(0, userLog.getText().toString(),passLog.getText().toString());
                SharedPreferences.Editor editor = sharedPref.edit();
                if(db.loginByUserName(a))
                {
                    //Checking if Admin Login
                    if(a.name.equals("Admin"))
                    {
                        Intent admin = new Intent(LoginActivity.this,AdminLoginActivity.class);
                        db.close();
                        startActivity(admin);
                    }
                    //Normal User
                    else
                    {
                        editor.putInt("ID",db.getIDbyUser(a));
                        editor.commit();
                        Intent normal = new Intent(LoginActivity.this,NormalLoginActivity.class);
                        db.close();
                        startActivity(normal);
                    }

                    //debug Login
                    Toast.makeText(LoginActivity.this, "Nailed it", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //debug Login Fail
                    Toast.makeText(LoginActivity.this, "Fuck", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
    protected void onPause() {
        super.onPause();
        db.close();
    }
}
