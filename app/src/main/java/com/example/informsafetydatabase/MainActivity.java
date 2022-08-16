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
    ResetPasswordForm resetPasswordForm;
    ChangePasswordForm changePasswordForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        // test insert (delete records first)
        databaseHelper.deleteUsers();

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
        //loginForm = new LoginForm("teacher1@huttkindergartens.org.nz", "password01");
        loginForm = new LoginForm("imaparent@gmail.com", "password03");
        String inputPassword = loginForm.getPassword();
        String correctPassword = databaseHelper.selectPassword(loginForm.getEmail());

        // If correct password given, generate a UserModel with all of the user's data
        if (inputPassword.equals(correctPassword)) {

            userModel = databaseHelper.selectUser(loginForm.getEmail());
            //Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
        }


        // Test Change Password (through Settings) - must be logged in first
        changePasswordForm = new ChangePasswordForm("password03", "imapassword", "imapassword");

        // Check that entered password matches user's current password
        if (changePasswordForm.getCurrentPassword().equals(userModel.getPassword())) {

            // Check that new password matches confirm password
            if (changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {

                // Update the password in the database
                databaseHelper.updatePassword(userModel.getEmail(), changePasswordForm.getNewPassword());

                // Update the password in the logged in user object
                userModel.setPassword(changePasswordForm.getNewPassword());

                // toast usermodel new password to check it worked
                Toast.makeText(MainActivity.this, "Changed password to " + userModel.getPassword(), Toast.LENGTH_LONG).show();

            }
            else {
                Toast.makeText(MainActivity.this, "New Password and Confirm Password do not match", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Current Password is incorrect", Toast.LENGTH_LONG).show();
        }



        // test password reset
//        resetPasswordForm = new ResetPasswordForm("teacher1@huttkindergartens.org.nz", "mynewpassword", "mynewpassword");
//        if (resetPasswordForm.getNewPassword().equals(resetPasswordForm.getConfirmPassword())) {
//            databaseHelper.updatePassword(resetPasswordForm.getEmail(), resetPasswordForm.getNewPassword());
//            Toast.makeText(MainActivity.this, "Changed password to " + resetPasswordForm.getNewPassword(), Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(MainActivity.this, "Confirm Password must match New Password", Toast.LENGTH_LONG).show();
//        }



        // test delete
//        userModel = new UserModel(3, -1, 1, "", "", "", "", "", false);
//        databaseHelper.deleteUser(userModel);
//        databaseHelper.deleteGuardian(userModel);

        //Toast.makeText(MainActivity.this, registrationForm.toString(), Toast.LENGTH_SHORT).show();

    }

}