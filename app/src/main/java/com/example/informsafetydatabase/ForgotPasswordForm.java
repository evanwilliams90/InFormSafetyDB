package com.example.informsafetydatabase;

public class ForgotPasswordForm {

    private String newPassword;
    private String confirmPassword;


    public ForgotPasswordForm(String password) {
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }


    // toString - for printing
    @Override
    public String toString() {
        return "forgotPasswordForm{" +
                ", newPassword='" + newPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }


    // Getters and setters

    public ForgotPasswordForm() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


}
