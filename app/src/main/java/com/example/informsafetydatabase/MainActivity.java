package com.example.informsafetydatabase;

import static com.example.informsafetydatabase.EncryptDecrypt.*;

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

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


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
    ChangeUserDetailsForm changeUserDetailsForm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        // test insert (delete records first)
        databaseHelper.deleteUsers();


//      test registration
        registrationForm = new RegistrationForm("Teacher 1", "teacher1@huttkindergartens.org.nz", "0210727600", "password01", "password01");
        registerUser(registrationForm);

//        registrationForm = new RegistrationForm("Teacher 2", "teacher2@huttkindergartens.org.nz", "0210727598", "password02", "password02");
//        registerUser(registrationForm);
//
//        registrationForm = new RegistrationForm("Parent 1", "imaparent@gmail.com", "0270727676", "password03", "password03");
//        registerUser(registrationForm);
//
//        registrationForm = new RegistrationForm("Parent 2", "imaparenttoo@gmail.com", "0220727622", "password04", "password04");
//        registerUser(registrationForm);



//      test login
        loginForm = new LoginForm("teacher1@huttkindergartens.org.nz", "password01");
        logInUser(loginForm);
//        Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_SHORT).show();

        //        loginForm = new LoginForm("imaparent@gmail.com", "password03");




//         Test Change User Details (through Settings) - must be logged in first
        changeUserDetailsForm = new ChangeUserDetailsForm("First Teacher", "firstteacher@huttkindergartens.org.nz", "02101010101", "password01");
        changeUserDetails(userModel, changeUserDetailsForm);

//        // Test log in again
        loginForm = new LoginForm("firstteacher@huttkindergartens.org.nz", "password01");
        logInUser(loginForm);
//        Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_SHORT).show();

//        int myid = databaseHelper.getIDFromEmail("firstteacher@huttkindergartens.org.nz");
//        Toast.makeText(MainActivity.this, "id: " + myid, Toast.LENGTH_SHORT).show();

        // Test Change Password (through Settings) - must be logged in first
        changePasswordForm = new ChangePasswordForm("password01", "imapassword", "imapassword");
        changePassword(userModel, changePasswordForm);


        // test password reset
        resetPasswordForm = new ResetPasswordForm("firstteacher@huttkindergartens.org.nz", "mynewpassword", "mynewpassword");

        // Check that new password and confirm password fields match
        if (resetPasswordForm.getNewPassword().equals(resetPasswordForm.getConfirmPassword())) {

            // Get user id matching the email address


            // Update the database with new password (encrypted)
            databaseHelper.updatePassword(resetPasswordForm.getEmail(), resetPasswordForm.getNewPassword());
            Toast.makeText(MainActivity.this, "Changed password to " + resetPasswordForm.getNewPassword(), Toast.LENGTH_SHORT).show();

            // checking
            // Get user ID matching the provided email
//            int myid = databaseHelper.getIDFromEmail(resetPasswordForm.getEmail());
//            String myEmail = resetPasswordForm.getEmail();
//            // Get and decrypt password matching the provided id
//            String myPassword = databaseHelper.selectPassword(myid);

            // Do login
            loginForm = new LoginForm(resetPasswordForm.getEmail(), resetPasswordForm.getNewPassword());
            logInUser(loginForm);



        }
        else {
            Toast.makeText(MainActivity.this, "Confirm Password must match New Password", Toast.LENGTH_SHORT).show();
        }


    int myid = databaseHelper.getIDFromEmail("firstteacher@huttkindergartens.org.nz");
    // Get and decrypt password matching the provided id
    String myPassword = databaseHelper.selectPassword(myid);
        Toast.makeText(MainActivity.this, "Testing", Toast.LENGTH_SHORT).show();



        // test delete
//        userModel = new UserModel(3, -1, 1, "", "", "", "", "", false);
//        databaseHelper.deleteUser(userModel);
//        databaseHelper.deleteGuardian(userModel);

        //Toast.makeText(MainActivity.this, registrationForm.toString(), Toast.LENGTH_SHORT).show();

    }




    // Register a user
    public void registerUser(RegistrationForm registrationForm) {

        // Check that password and confirm password match
        if (registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {

            // If user has an ECE Centre email address, register them as a Teacher
            // Otherwise register them as a Guardian (default value 0).
            if (registrationForm.getEmail().contains("@huttkindergartens.org.nz")) {
                registrationForm.setTeacher(1);
            }

            // Insert to Teacher/Guardian table according to isTeacher flag
            if (registrationForm.isTeacher() == 1) {
                teacherID = databaseHelper.insertTeacher(registrationForm);
                registrationForm.setTeacherID((int) teacherID);
            }
            else {
                guardianID = databaseHelper.insertGuardian(registrationForm);
                registrationForm.setGuardianID((int) guardianID);
            }

            // Insert to User table
            userID = databaseHelper.insertUser(registrationForm);
            Toast.makeText(MainActivity.this, "Registered " + registrationForm.getName(), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
        }

    }


//    Log in a user
    public void logInUser(LoginForm loginForm) {

        // Encrypt email to match to database
        //String encEmail = encrypt(loginForm.getEmail());

        // Get user ID matching the provided email
        int id = databaseHelper.getIDFromEmail(loginForm.getEmail());

        // Get and decrypt password matching the provided id
        String correctPassword = databaseHelper.selectPassword(id);

        // If correct password given, generate a UserModel with all of the user's data
        if (loginForm.getPassword().equals(correctPassword)) {

            userModel = databaseHelper.selectUser(id);

            Toast.makeText(MainActivity.this, "Logged in as " + userModel.getName(), Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }


    // Update a user's details - must be logged in first
    public void changeUserDetails(UserModel userModel, ChangeUserDetailsForm changeUserDetailsForm) {
        // Check input password matches current password
        if (changeUserDetailsForm.getPassword().equals(userModel.getPassword())) {

            // If name/email/phone are blank, fill them in from User Model
            if (changeUserDetailsForm.getName().equals("")) {
                changeUserDetailsForm.setName(userModel.getName());
            }

            if (changeUserDetailsForm.getEmail().equals("")) {
                changeUserDetailsForm.setEmail(userModel.getEmail());
            }

            if (changeUserDetailsForm.getPhone().equals("")) {
                changeUserDetailsForm.setPhone(userModel.getPhone());
            }

            // Update the entered user details in the database
            databaseHelper.updateUserDetails(userModel.getUserID(),
                    changeUserDetailsForm.getName(),
                    changeUserDetailsForm.getEmail(),
                    changeUserDetailsForm.getPhone());

            // Update the user model;
            userModel.setName(changeUserDetailsForm.getName());
            userModel.setEmail(changeUserDetailsForm.getEmail());
            userModel.setPhone(changeUserDetailsForm.getPhone());

            // Toast updated user
            //Toast.makeText(MainActivity.this, "Updated to: " + userModel.toString(), Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
        }
    }


    // Change a user's password - must be logged in first
    public void changePassword(UserModel userModel, ChangePasswordForm changePasswordForm) {

        // Check that entered password matches user's current password
        if (changePasswordForm.getCurrentPassword().equals(userModel.getPassword())) {

            // Check that new password matches confirm password
            if (changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {

                // Update the password in the database
                databaseHelper.updatePassword(userModel.getEmail(), changePasswordForm.getNewPassword());

                // Update the password in the logged in user object
                userModel.setPassword(changePasswordForm.getNewPassword());

                // toast usermodel new password to check it worked
                Toast.makeText(MainActivity.this, "Changed password to " + userModel.getPassword(), Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(MainActivity.this, "New Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Current Password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}