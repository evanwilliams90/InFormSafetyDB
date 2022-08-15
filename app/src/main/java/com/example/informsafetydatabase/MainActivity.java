package com.example.informsafetydatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // create references to buttons and other controls
    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_customerList;
    ArrayAdapter customerArrayAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        // test insert
        UserModel userModel = new UserModel(-1, 0, 0, "Teacher 1", "teacher1@gmail.com", "0210727600", "password01", "password01", true);
        boolean success = databaseHelper.insertUser(userModel);

        userModel = new UserModel(-1, 0, 0, "Teacher 2", "teacher2@gmail.com", "0210727598", "password02", "password01", true);
        success = databaseHelper.insertUser(userModel);

        userModel = new UserModel(-1, 0, 0, "Parent 1", "imaparent@gmail.com", "0270727676", "password03", "password01", false);
        success = databaseHelper.insertUser(userModel);

        userModel = new UserModel(-1, 0, 0, "Parent 2", "imaparenttoo@gmail.com", "0220727622", "password04", "password01", false);
        success = databaseHelper.insertUser(userModel);


        // test delete
        userModel = new UserModel(0, 0, 1, "", "", "", "", "", false);
        success = databaseHelper.deleteUser(userModel);


        //Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_SHORT).show();

    }

}