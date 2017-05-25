package com.rr.royreznik.trysqllite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    EditText userLog, passLog;
    Button loginButton;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DBHelper(this);

        userLog = (EditText)findViewById(R.id.UserLogin);
        passLog = (EditText)findViewById(R.id.PassLogin);
        loginButton = (Button)findViewById(R.id.btnLogin);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User a = new User(0, userLog.getText().toString(),passLog.getText().toString());
                if(db.loginByUserName(a))
                {
                    if(a.name.equals("Admin"))
                    {
                        Intent admin = new Intent(LoginActivity.this,AdminLoginActivity.class);
                        startActivity(admin);
                    }
                    else
                    {
                        Intent normal = new Intent(LoginActivity.this,NormalLoginActivity.class);
                        startActivity(normal);
                    }

                    Toast.makeText(LoginActivity.this, "Nailed it", Toast.LENGTH_SHORT).show();
                }
                else
                {
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
