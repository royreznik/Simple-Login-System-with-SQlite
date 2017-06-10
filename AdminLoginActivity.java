package com.rr.royreznik.trysqllite;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//Admin Page Activity

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

        texts.setClickable(true);

        texts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //getting the Item Text
                String str = texts.getItemAtPosition(position).toString();

                //Getting the name
                String[] name = str.split(" ",0);
                User a = new User(name[0],"");
                //making the dialog
                dialogMaker(db.getIDbyUser(a));


                Toast.makeText(AdminLoginActivity.this, "The item " + str +" was clicked " , Toast.LENGTH_LONG).show();
            }
        });


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



    //Edit the Score Dialog
    public void dialogMaker(final int id)
    {

        AlertDialog.Builder Dialoger = new AlertDialog.Builder(AdminLoginActivity.this);
        Dialoger.setMessage("What score you want to set to him?");
        final EditText input = new EditText(AdminLoginActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        Dialoger.setView(input);

        Dialoger.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                db.setScoreByID(id,Integer.parseInt(input.getText().toString()));
                Toast.makeText(AdminLoginActivity.this, "Worked ", Toast.LENGTH_SHORT).show();
            }
        });

        Dialoger.setNegativeButton("NVM",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = Dialoger.create();
        alertDialog.show();
    }


}
