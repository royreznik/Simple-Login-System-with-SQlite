package com.rr.royreznik.trysqllite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
///The Admin Page BackEnd
public class AdminLoginActivity extends AppCompatActivity {

    DBHelper db;
    ArrayList<String> dbListViewItems;
    Button delete;
    EditText nameToDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        db = new DBHelper(this);


        delete = (Button) findViewById(R.id.btnDelete);
        nameToDelete = (EditText)findViewById(R.id.EditDelete);

        //Configurate the List view
        final ListView texts = (ListView)findViewById(R.id.ListView1);
        dbListViewItems = new ArrayList<String>();

        //makes the ArrayList contain the DB text
        dbListViewItems = db.getUserList();

        //Adapter shit
        final ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dbListViewItems);
        texts.setAdapter(itemsAdapter);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteByName(nameToDelete.getText().toString());
            }
        });
    }

    protected void onPause() {
        super.onPause();
        db.close();
    }
}
