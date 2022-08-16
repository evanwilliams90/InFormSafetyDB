package com.example.informsafetydatabase;

// RegistrationForm object holds the data entered into a submitted Registration form.

public class RegistrationForm {

    private int userID;
    private int teacherID;
    private int guardianID;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
    public boolean isTeacher;


    public RegistrationForm(int userID, int teacherID, int guardianID, String name, String email, String phone, String password, String confirmPassword, boolean isTeacher) {
        this.userID = userID;
        this.teacherID = teacherID;
        this.guardianID = guardianID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.isTeacher = isTeacher;
    }


    // toString - for printing
    @Override
    public String toString() {
        return "registrationForm{" +
                "id=" + userID +
                ", teacherID='" + teacherID + '\'' +
                ", guardianID='" + guardianID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", isTeacher=" + isTeacher +
                '}';
    }


    // Getters and setters

    public RegistrationForm() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getGuardianID() {
        return guardianID;
    }

    public void setGuardianID(int guardianID) {
        this.guardianID = guardianID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

}
