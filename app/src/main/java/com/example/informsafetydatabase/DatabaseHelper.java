package com.example.informsafetydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "user4.db", null, 1);
    }

    // first time DB accessed
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTeacherTable = "CREATE TABLE IF NOT EXISTS Teacher (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT, " +
                "phone TEXT" +
                ")";

        String createGuardianTable = "CREATE TABLE IF NOT EXISTS Guardian (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT, " +
                "phone TEXT" +
                ")";

        String createUserTable = "CREATE TABLE IF NOT EXISTS User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "kindergarten_id INTEGER REFERENCES Kindergarten (id), " +
                "teacher_id INTEGER REFERENCES Teacher (id), " +
                "guardian_id INTEGER REFERENCES Guardian (id), " +
                "username TEXT, " +
                "password TEXT" +
                ")";

        db.execSQL(createTeacherTable);
        db.execSQL(createGuardianTable);
        db.execSQL(createUserTable);
    }

    // when DB version changed
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    // Insert teacher/guardian plus user
    public boolean insertUser(UserModel userModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        String destTable;

        // Select destination table according to Teacher/Guardian option
        if (userModel.isTeacher()) {
            destTable = "Teacher";
        }
        else {
            destTable = "Guardian";
        }

        // Insert personal info to Teacher/Guardian table
        cv1.put("name", userModel.getName());
        cv1.put("email", userModel.getEmail());
        cv1.put("phone", userModel.getPhone());
        long insert1 = db.insert(destTable, null, cv1);

        // Insert login data to User table
        if (userModel.isTeacher()) {
            cv2.put("teacher_id", insert1);
        }
        else {
            cv2.put("guardian_id", insert1);
        }
        cv2.put("password", userModel.getPassword());
        long insert2 = db.insert("user", null, cv2);

        // exit code 1 if successful, -1 otherwise
        if (insert1 == -1 || insert2 == -1) {
            return false;
        }
        else{
            return true;
        }
    }


    // Delete a user
    public boolean deleteUser(UserModel userModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString1;
        String queryString2;

        // Get teacher/guardian ID and create delete queries accordingly (incl User table)
        if (userModel.isTeacher()) {
            int delTeacherID = userModel.getTeacherID();
            queryString1 = "DELETE FROM User WHERE teacher_id = " + delTeacherID;
            queryString2 = "DELETE FROM Teacher WHERE id = " + delTeacherID;
        }
        else {
            int delGuardianID = userModel.getGuardianID();
            queryString1 = "DELETE FROM User WHERE guardian_id = " + delGuardianID;
            queryString2 = "DELETE FROM Guardian WHERE id = " + delGuardianID;

        }

        // Execute both deletes
        db.execSQL(queryString1);
        db.execSQL(queryString2);

        return true;

    }


//    // Get a list of all records
//    public List<UserModel> getUser() {
//
//        List<UserModel> returnList = new ArrayList<>();
//
//        // get data from database
//
//        String queryString = "SELECT * FROM User";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(queryString, null);
//
//        if(cursor.moveToFirst()) {
//            // Loop through result set, create new customer objects, put into return list
//            do {
//                int customerID = cursor.getInt(0);
//                String customerName = cursor.getString(1);
//                int customerAge = cursor.getInt(2);
//                boolean customerActive = cursor.getInt(3) == 1;
//
//                UserModel newCustomer = new UserModel(customerID, customerName, customerAge, customerActive);
//
//                returnList.add(newCustomer);
//
//            }
//            while(cursor.moveToNext());
//
//        }
//        else {
//            // do nothing
//        }
//
//        // clean up
//        cursor.close();
//        db.close();
//
//        return returnList;
//    }

}
