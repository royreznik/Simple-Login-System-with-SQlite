package com.rr.royreznik.trysqllite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    Button tr, login;
    ArrayList<String> dbListViewItems;
    String Name,Pass;


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


        //Used to check if db contain things
        /*
        //Configurate the List view
        final ListView texts = (ListView)findViewById(R.id.ListView1);
        dbListViewItems = new ArrayList<String>();

        //makes the ArrayList contain the DB text
        dbListViewItems = db.getUserList();

        //Adapter shit
        final ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dbListViewItems);
        texts.setAdapter(itemsAdapter);
        */

        //adding to DB
        tr = (Button) findViewById(R.id.btn1);
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = user.getText().toString();
                Pass = password.getText().toString();
                User ra = new User(Name, Pass);
                db.insert(ra);
            }
        });
        //Debug Delet
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
}
