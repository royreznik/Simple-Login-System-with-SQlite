package com.rr.royreznik.trysqllite;

/**
 * Created by royreznik on 24/05/2017.
 */
public class User {
    // Labels table name
    public static final String TABLE = "Users";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_name = "User";
    public static final String KEY_pass = "Password";


    // property help us to keep data
    public static int IDS=100;
    public int ID;
    public String name;
    public String Password;

    public User(String name,String Password)
    {
        IDS++;
        this.ID = IDS;
        this.name = name;
        this.Password = Password;
    }

    public User(int a, String name,String Password)
    {
        this.ID = a;
        this.name = name;
        this.Password = Password;
    }
    public User()
    {
    }


}
