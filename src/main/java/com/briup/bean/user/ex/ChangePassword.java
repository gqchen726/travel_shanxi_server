package com.briup.bean.user.ex;

public class ChangePassword {

    private String mobileNumber;

    private String oldPassword;

    private String newPassword;

    public ChangePassword() {
    }

    public ChangePassword(String mobileNumber, String oldPassword, String newPassword) {
        this.mobileNumber = mobileNumber;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
