package com.viliric.DAO.model;

/**
 * Created by alexey on 1/15/16.
 */
public class User {
    private int Id;
    private String Login;
    private String Password;
    private String Surname;
    private String Name;
    private String Patronymic;
    private String Email;


    public User(String login, String password) {
        Login = login;
        Password = password;
    }

    public User() {

    }


    public String getSurname() {
        return Surname;
    }

    public String getName() {
        return Name;
    }

    public String getPatronymic() {
        return Patronymic;
    }

    public String getEmail() {
        return Email;
    }

    public String getLogin() {
        return Login;
    }

    public String getPassword() {
        return Password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPatronymic(String patronymic) {
        Patronymic = patronymic;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
