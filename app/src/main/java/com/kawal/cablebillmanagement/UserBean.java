package com.kawal.cablebillmanagement;

/**
 * Created by kawaldeep on 5/9/2017.
 */

public class UserBean {
    int id;
    String name;
    String uName;
    String uPhone;
    String uEmail;
    String uPassword;
    String uAddress;
    int UserType;

    public UserBean() {
    }

    public UserBean(int id, String name, String uName, String uPhone, String uEmail, String uPassword,  String uAddress, int userType) {
        this.id = id;
        this.name = name;
        this.uName = uName;
        this.uPhone = uPhone;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
        this.uAddress = uAddress;
        UserType = userType;
    }

    public UserBean(int id, String uName, String uPhone, String uEmail, String uPassword, String uAddress, int userType) {
        this.id = id;
        this.uName = uName;
        this.uPhone = uPhone;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
        this.uAddress = uAddress;
        UserType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uName='" + uName + '\'' +
                ", uPhone='" + uPhone + '\'' +
                ", uEmail='" + uEmail + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", uAddress='" + uAddress + '\'' +
                ", UserType=" + UserType +
                '}';
    }
}
