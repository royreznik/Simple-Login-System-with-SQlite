package com.rr.royreznik.trysqllite;

/**
 * Created by royreznik on 24/05/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

//DB Helper

public class DBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "ScoreUsers.db";

    public DBHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_STUDENT = "CREATE TABLE IF NOT EXISTS " + User.TABLE  + "("
                + User.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + User.KEY_name + " TEXT, "
                + User.KEY_score + " INTEGER, "
                + User.KEY_pass + " TEXT )";

        db.execSQL(CREATE_TABLE_STUDENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);

        onCreate(db);

    }

    public int insert(User user) {

        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_ID, user.ID);
        values.put(User.KEY_pass,user.Password);
        values.put(User.KEY_name, user.name);
        values.put(User.KEY_score,user.score);

        // Inserting Row
        long student_Id = db.insert(User.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    /*public boolean updateScore(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_ID, user.ID);
        values.put(User.KEY_pass,user.Password);
        values.put(User.KEY_name, user.name);
        values.put(User.KEY_score,user.score);

        db.update(User.TABLE,values,User.KEY_name + "=?",new String[]{username});
        db.close();
        return true;
    }*/


    public User getUserById(int Id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_ID + "," +
                User.KEY_name + "," +
                User.KEY_pass +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string;// It's a good practice to use parameter ?, instead of concatenate string

        //int iCount =0;
        User user = new User();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                user.ID =cursor.getInt(cursor.getColumnIndex(User.KEY_ID));
                user.name =cursor.getString(cursor.getColumnIndex(User.KEY_name));
                user.Password  =cursor.getString(cursor.getColumnIndex(User.KEY_pass));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return user;
    }

    public boolean loginByUserName(User user)
    {
        String pass="";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_name + "," +
                User.KEY_pass +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_name + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(user.name) } );

        if (cursor.moveToFirst()) {
            do {
                pass  =cursor.getString(cursor.getColumnIndex(User.KEY_pass));
            } while (cursor.moveToNext());
        }
        if(pass.equals(user.Password)) return true;
        return false;
    }



    public ArrayList<String> getUserList() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_name + "," +
                User.KEY_pass + "," +
                User.KEY_ID + "," +
                User.KEY_score +
                " FROM " + User.TABLE;

        //Student student = new Student();
        ArrayList<String> userList1 = new ArrayList<String>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {

                userList1.add(cursor.getString(cursor.getColumnIndex(User.KEY_name)) +" "+ cursor.getString(cursor.getColumnIndex(User.KEY_pass))+" "+cursor.getString(cursor.getColumnIndex(User.KEY_score))+" "+cursor.getString(cursor.getColumnIndex(User.KEY_ID)));


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList1;

    }

    public void deleteByID(int ID) {

        SQLiteDatabase db = this.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(User.TABLE, User.KEY_ID + "= ?", new String[] { String.valueOf(ID) });
        db.close(); // Closing database connection
    }

    public void deleteByName(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(User.TABLE, User.KEY_name + "= ?", new String[] { String.valueOf(name) });
        db.close(); // Closing database connection
    }

    public void clearTable()   {
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //Getting the ID by the userName
    public int getIDbyUser(User user)
    {
        int ids=0;
        String id="";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_ID +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_name + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(user.name) } );

        if (cursor.moveToFirst()) {
            do {
                id  =cursor.getString(cursor.getColumnIndex(User.KEY_ID));
            } while (cursor.moveToNext());
        }
        ids = Integer.parseInt(id);
        return ids;
    }

    //Adding one by ID
    public void addOneByID(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] bindingArgs = new String[]{"1", id+""};

        db.execSQL("UPDATE " + User.TABLE +
                        " SET " + User.KEY_score + " = " + User.KEY_score + " + ?" +
                        " WHERE " + User.KEY_ID + " = ?",
                bindingArgs);
        db.close();

    }

    //Setting Score by ID
    public void setScoreByID(int id, int Score)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] bindingArgs = new String[]{Score+"", id+""};

        db.execSQL("UPDATE " + User.TABLE +
                        " SET " + User.KEY_score + " = " + " + ?" +
                        " WHERE " + User.KEY_ID + " = ?",
                bindingArgs);
        db.close();

    }


    public boolean isNameTaken(String a)
    {
        int c=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_name +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_name + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(a) } );

        if (cursor.moveToFirst()) {
            do {
                c++;
            } while (cursor.moveToNext());
        }

        if(c == 0)
        {
            return false;
        }
        return true;
    }

}
