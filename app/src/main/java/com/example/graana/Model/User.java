package com.example.graana.Model;

public class User {
    private String name;
    private String mail;
    private String password;
    private String phone;


    public User()
    {
        name="";
        mail="";
        password="";
        phone="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String name, String mail, String password, String phone) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.phone = phone;
    }
}
