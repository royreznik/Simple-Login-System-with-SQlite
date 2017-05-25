package com.rr.royreznik.trysqllite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//The backEnd of the Normal User InterFace, Contain AsyncTask that donwlaod Images

public class NormalLoginActivity extends AppCompatActivity {

    Button load;
    EditText url;


    DownLoadImages downloadimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_login);

        load = (Button)findViewById(R.id.btnImage);
        url = (EditText)findViewById(R.id.EditUrl);


        downloadimage=new DownLoadImages();

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urls = url.getText().toString();
                downloadimage.execute(urls);
                //made by RoyReznik
            }
        });



    }

    //Download Images
    public class DownLoadImages extends AsyncTask<String, Integer, Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap=null;
            HttpURLConnection httpcon = null;
            try {
                android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
                android.os.StrictMode.setThreadPolicy(policy);

                URL url=new URL(params[0]);
                //Init the connection to the web
                httpcon=(HttpURLConnection) url.openConnection();
                //Ceck if there is connection


                //This abstract class is the superclass of all classes representing an input stream of bytes.
                InputStream is=httpcon.getInputStream();
                //Creates Bitmap objects from various sources, including files, streams, and byte-arrays.
                bitmap = BitmapFactory.decodeStream(is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            ImageView image=(ImageView) findViewById(R.id.imageView);
            if(result!=null)
                image.setImageBitmap(result);
        }
    }



}
