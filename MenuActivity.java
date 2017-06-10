package com.rr.royreznik.trysqllite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//Starting page

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button Login,Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Login = (Button)findViewById(R.id.btnLog);
        Login.setOnClickListener(MenuActivity.this);

        Register = (Button)findViewById(R.id.btnReg);
        Register.setOnClickListener(MenuActivity.this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent main = new Intent();
        switch(id)
        {
            case R.id.btnLog:
                main.setClass(MenuActivity.this, LoginActivity.class);
                break;
            case R.id.btnReg:
                main.setClass(MenuActivity.this, MainActivity.class);
                break;

        }
        startActivity(main);
    }
}
