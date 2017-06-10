package com.rr.royreznik.trysqllite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//Register Activity page

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    Button tr, login;
    //ArrayList<String> dbListViewItems; Used for Debug
    String Name,Pass;

    static String[] nooK = {",","/","."," ","-","+","#","@"};


    EditText user, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Basic start stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText)findViewById(R.id.editUser);
        password = (EditText)findViewById(R.id.editPass);

        //Creating DB
        db = new DBHelper(this);


        //adding to DB
        tr = (Button) findViewById(R.id.btn1);
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = user.getText().toString();
                Pass = password.getText().toString();
                if(valid(Name) && valid(Pass))
                {
                    User ra = new User(Name, Pass);
                    db.insert(ra);
                    Toast.makeText(MainActivity.this, "Register is done", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Debug Delete
        tr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //db.delete(0);
                //Texts = db.getUserList();
                return true;
            }
        });

        //Move to Login
        login = (Button) findViewById(R.id.LoginMove);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(a);

            }
        });

    }

    //To dont get StackOverFlow
    protected void onPause() {
        super.onPause();
        db.close();
    }


    public boolean valid(String inputStr)
    {
        for(int i =0; i < nooK.length; i++)
        {
            if(inputStr.contains(nooK[i]))
            {
                Toast.makeText(MainActivity.this, "Make sure the your user and pass contain only characters and numbers", Toast.LENGTH_SHORT).show();
                db.close();
                return false;
            }
        }
        if(db.isNameTaken(inputStr))
        {
            Toast.makeText(MainActivity.this, "This UserName already Used", Toast.LENGTH_SHORT).show();
            db.close();
            return false;
        }
        db.close();
        return true;
    }

}
