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
    long userID;
    long teacherID;
    long guardianID;
    RegistrationForm registrationForm;
    UserModel userModel;
    LoginForm loginForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        // test insert
        registrationForm = new RegistrationForm("Teacher 1", "teacher1@huttkindergartens.org.nz", "0210727600", "password01", "password01");
        teacherID = databaseHelper.insertTeacher(registrationForm);
        registrationForm.setTeacherID((int) teacherID);
        userID = databaseHelper.insertUser(registrationForm);

        registrationForm = new RegistrationForm("Teacher 2", "teacher2@huttkindergartens.org.nz", "0210727598", "password02", "password01");
        teacherID = databaseHelper.insertTeacher(registrationForm);
        registrationForm.setTeacherID((int) teacherID);
        userID = databaseHelper.insertUser(registrationForm);

        registrationForm = new RegistrationForm("Parent 1", "imaparent@gmail.com", "0270727676", "password03", "password01");
        guardianID = databaseHelper.insertGuardian(registrationForm);
        registrationForm.setGuardianID((int) guardianID);
        userID = databaseHelper.insertUser(registrationForm);

        registrationForm = new RegistrationForm("Parent 2", "imaparenttoo@gmail.com", "0220727622", "password04", "password01");
        guardianID = databaseHelper.insertGuardian(registrationForm);
        registrationForm.setGuardianID((int) guardianID);
        userID = databaseHelper.insertUser(registrationForm);



        // test login
        loginForm = new LoginForm("teacher1@huttkindergartens.org.nz", "password01");
        String correctPassword = databaseHelper.selectPassword(loginForm.getEmail());
        String inputPassword = loginForm.getPassword();

        // If correct password given, generate a UserModel with all of the user's data
        if (inputPassword.equals(correctPassword)) {

            UserModel usermodel = databaseHelper.selectUser(loginForm.getEmail());
            Toast.makeText(MainActivity.this, usermodel.toString(), Toast.LENGTH_LONG).show();





        }
        else {
            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
        }


        // test delete
//        userModel = new UserModel(3, -1, 1, "", "", "", "", "", false);
//        databaseHelper.deleteUser(userModel);
//        databaseHelper.deleteGuardian(userModel);

        //Toast.makeText(MainActivity.this, registrationForm.toString(), Toast.LENGTH_SHORT).show();

    }

}