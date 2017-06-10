package com.rr.royreznik.trysqllite;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

//Question Activity

public class QuestActivity extends AppCompatActivity implements View.OnClickListener {


    //Used to Debug
    String TAG = MainActivity.class.getSimpleName();

    //UI objects
    TextView textQuest, catQuest, diffQuest, typeQuest;

        //Multi
    EditText answerText;
    Button btnAnswer;

        //bool
    Button btnFalse, btnTrue;


    //DB
    DBHelper db;


    //Dialog for the AsyncTask
    ProgressDialog pDialog;

    //List view
    ListView lv;

    //random 1 Question, API
    static String url = "https://opentdb.com/api.php?amount=1";

    //Save stuff
    String currectAnswer, typer="";
    int ID=0;


    SharedPreferences sharedPref;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        db = new DBHelper(this);

        //Configurate UI
        textQuest = (TextView)findViewById(R.id.textQuestion);
        catQuest = (TextView)findViewById(R.id.categoryQuestion);
        diffQuest = (TextView)findViewById(R.id.diffQuestion);
        typeQuest = (TextView)findViewById(R.id.typeQuestion);

            //multi
        answerText = (EditText)findViewById(R.id.answerText);
        btnAnswer = (Button)findViewById(R.id.btnAnswer);

            //bool
        btnFalse = (Button)findViewById(R.id.btnFalse);
        btnTrue = (Button)findViewById(R.id.btnTrue);


        //The asyncTask question
        new GetItem().execute();


        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        ID = sharedPref.getInt("ID",0);


        btnFalse.setOnClickListener(QuestActivity.this);
        btnTrue.setOnClickListener(QuestActivity.this);
        btnAnswer.setOnClickListener(QuestActivity.this);



    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id)
        {
            case R.id.btnAnswer:
                if(answerText.getText().toString().equals(currectAnswer))
                {
                    Toast.makeText(QuestActivity.this, "Good", Toast.LENGTH_SHORT).show();
                    db.addOneByID(ID);
                    this.recreate();
                    db.close();


                }
                Toast.makeText(QuestActivity.this, currectAnswer, Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnFalse:
                if(currectAnswer.equals("False"))
                {
                    Toast.makeText(QuestActivity.this, "Good", Toast.LENGTH_SHORT).show();
                    db.addOneByID(ID);
                    this.recreate();
                    db.close();
                }
                else
                {
                    Toast.makeText(QuestActivity.this, "Idiot",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnTrue:
                if(currectAnswer.equals("True"))
                {
                    Toast.makeText(QuestActivity.this, "Good", Toast.LENGTH_SHORT).show();
                    db.addOneByID(ID);
                    this.recreate();
                    db.close();
                }
                else
                {
                    Toast.makeText(QuestActivity.this, "Idiot",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private class GetItem extends AsyncTask<Void, HashMap<String, String>, HashMap<String, String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog
            pDialog = new ProgressDialog(QuestActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected HashMap<String, String> doInBackground(Void... arg0) {

            //using a HtppHandler for downdloading
            HttpHandler sh = new HttpHandler();

            //HashMap for moving the Json Strings
            HashMap<String, String> question = new HashMap<>();

            //Getting the JSON with the handler
            String jsonStr = sh.makeServiceCall(url);

            //Debug
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray quest = jsonObj.getJSONArray("results");

                    // looping through All Contacts
                    for (int i = 0; i < quest.length(); i++) {
                        JSONObject c = quest.getJSONObject(i);

                        //Save the paramets from the JSON
                        String category = c.getString("category");
                        String diff = c.getString("difficulty");
                        String questionS = c.getString("question");
                        String correct = c.getString("correct_answer");
                        String type = c.getString("type");

                        //Putting in the parameters to move them all in one case
                        question.put("category", category);
                        question.put("diff", diff);
                        question.put("theQuest",questionS);
                        question.put("correct", correct);
                        question.put("type", type);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return question;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            //Put the parameters in the UI
            catQuest.setText("Category: " + result.get("category"));
            diffQuest.setText("Difficulty " +result.get("diff"));
            typeQuest.setText("Type: "+result.get("type"));
            textQuest.setText(result.get("theQuest"));
            typer = result.get("type");
            currectAnswer = result.get("correct");
            typer();

        }
    }

    //Use this as a Function cause it cause sync problem with the asyncTask
    public void typer()
    {
        if(typer.equals("boolean"))
        {
            btnAnswer.setVisibility(View.GONE);
            answerText.setVisibility(View.GONE);
        }
        else
        {
            btnTrue.setVisibility(View.GONE);
            btnFalse.setVisibility(View.GONE);
        }
    }
}
