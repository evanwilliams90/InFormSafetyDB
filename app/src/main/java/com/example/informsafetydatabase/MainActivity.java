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

    // Registration function
    public void registerUser(RegistrationForm registrationForm) /*throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException*/ {

        // Check that password and confirm password match
        if (registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {

            // If user has an ECE Centre email address, register them as a Teacher
            // Otherwise register them as a Guardian (default value 0).
            if (registrationForm.getEmail().contains("@huttkindergartens.org.nz")) {
                registrationForm.setTeacher(1);
            }

            // Encrypt email, phone, password
            try {
                registrationForm.setEmail(EncryptDecrypt.encrypt(registrationForm.getEmail()));
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            try {
                registrationForm.setPhone(EncryptDecrypt.encrypt(registrationForm.getPhone()));
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
            try {
                registrationForm.setPassword(EncryptDecrypt.encrypt(registrationForm.getPassword()));
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
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
            Toast.makeText(MainActivity.this, "Registered " + registrationForm.getName(), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Password and Confirm Password do not match", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        // test insert (delete records first)
        databaseHelper.deleteUsers();



        registrationForm = new RegistrationForm("Teacher 1", "teacher1@huttkindergartens.org.nz", "0210727600", "password01", "password01");
        registerUser(registrationForm);

        registrationForm = new RegistrationForm("Teacher 2", "teacher2@huttkindergartens.org.nz", "0210727598", "password02", "password02");
        registerUser(registrationForm);

        registrationForm = new RegistrationForm("Parent 1", "imaparent@gmail.com", "0270727676", "password03", "password03");
        registerUser(registrationForm);

        registrationForm = new RegistrationForm("Parent 2", "imaparenttoo@gmail.com", "0220727622", "password04", "password04");
        registerUser(registrationForm);



        // test login
//        loginForm = new LoginForm("teacher1@huttkindergartens.org.nz", "password01");
//        //loginForm = new LoginForm("imaparent@gmail.com", "password03");
//        String inputPassword = loginForm.getPassword();
//        String correctPassword = databaseHelper.selectPassword(loginForm.getEmail());
//
//        // If correct password given, generate a UserModel with all of the user's data
//        if (inputPassword.equals(correctPassword)) {
//
//            userModel = databaseHelper.selectUser(loginForm.getEmail());
//            //Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_LONG).show();
//
//        }
//        else {
//            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//        }


        // Test Change User Details (through Settings) - must be logged in first
//        changeUserDetailsForm = new ChangeUserDetailsForm("First Teacher", "firstteacher@huttkindergartens.org.nz", "02101010101", "password01x");
//
//        // Check that entered password matches user's current password
//        if (changeUserDetailsForm.getPassword().equals(userModel.getPassword())) {
//
//            // If name/email/phone are blank, fill them in from User Model
//            if (changeUserDetailsForm.getName().equals("")) {
//                changeUserDetailsForm.setName(userModel.getName());
//            }
//
//            if (changeUserDetailsForm.getEmail().equals("")) {
//                changeUserDetailsForm.setEmail(userModel.getEmail());
//            }
//
//            if (changeUserDetailsForm.getPhone().equals("")) {
//                changeUserDetailsForm.setPhone(userModel.getPhone());
//            }
//
//            // Update the entered user details in the database
//            databaseHelper.updateUserDetails(userModel.getUserID(),
//                    changeUserDetailsForm.getName(),
//                    changeUserDetailsForm.getEmail(),
//                    changeUserDetailsForm.getPhone());
//
//            Toast.makeText(MainActivity.this, "Updated details", Toast.LENGTH_LONG).show();
//
//        }
//        else {
//            Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
//        }


        // Test Change Password (through Settings) - must be logged in first
//        changePasswordForm = new ChangePasswordForm("password01", "imapassword", "imapasswordx");
//
//        // Check that entered password matches user's current password
//        if (changePasswordForm.getCurrentPassword().equals(userModel.getPassword())) {
//
//            // Check that new password matches confirm password
//            if (changePasswordForm.getNewPassword().equals(changePasswordForm.getConfirmPassword())) {
//
//                // Update the password in the database
//                databaseHelper.updatePassword(userModel.getEmail(), changePasswordForm.getNewPassword());
//
//                // Update the password in the logged in user object
//                userModel.setPassword(changePasswordForm.getNewPassword());
//
//                // toast usermodel new password to check it worked
//                Toast.makeText(MainActivity.this, "Changed password to " + userModel.getPassword(), Toast.LENGTH_LONG).show();
//
//            }
//            else {
//                Toast.makeText(MainActivity.this, "New Password and Confirm Password do not match", Toast.LENGTH_LONG).show();
//            }
//        }
//        else {
//            Toast.makeText(MainActivity.this, "Current Password is incorrect", Toast.LENGTH_LONG).show();
//        }



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