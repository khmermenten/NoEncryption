package com.example.noencryption;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView ins = findViewById(R.id.insertion);
        TextView sel = findViewById(R.id.selection);

        //create database
        SQLiteDatabase db = new DBHandler(getBaseContext()).getWritableDatabase();
        String path = "";
        // use getCanonicalPath because sqlite has trouble with symbolic links,
        // which getAbsolutePath gives
        try {
            path = getDatabasePath("test.db").getCanonicalPath();
        } catch (IOException e) {e.printStackTrace();}
        // call the native functions and print the result to the screen
        String query = "CREATE TABLE Dog(name varchar(20), breed varchar(20))";
        executeQuery(path, query);
        query = "INSERT INTO Dog VALUES ('Scooby', 'Great Dane')";
        String result = executeQuery(path, query);
        ins.setText(result);
        String contents = executeQuery(path, "SELECT * FROM Dog");
        sel.setText(contents);
    }

    public native String executeQuery(String path, String query);
}
