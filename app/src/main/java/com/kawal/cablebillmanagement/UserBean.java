package com.kawal.cablebillmanagement;

import android.widget.Spinner;

/**
 * Created by kawaldeep on 5/9/2017.
 */

public class UserBean {
    int id;
    String uName;
    String uPhone;
    String uEmail;
    String uPassword;
    String uAddress;
    int UserType;
    String connectionType;

    public UserBean() {
    }

    public UserBean(int id, String uName, String uPhone, String uEmail, String uPassword,  String uAddress, int userType,String connectionType) {
        this.id = id;
        this.uName = uName;
        this.uPhone = uPhone;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
        this.uAddress = uAddress;
        UserType = userType;
        this.connectionType = connectionType;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

       public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public String getConnection() {
        return connectionType;
    }

    public void setConnection(String connection) {
        this.connectionType = connection;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", uName='" + uName + '\'' +
                ", uPhone='" + uPhone + '\'' +
                ", uEmail='" + uEmail + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", uAddress='" + uAddress + '\'' +
                ", UserType=" + UserType +
                ", connection='" + connectionType + '\'' +
                '}';
    }

}
