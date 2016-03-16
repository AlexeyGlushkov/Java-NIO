package com.inculerate.diode.DAO.model;

/**
 * Created by alexey on 1/15/16.
 */
public class User {
    private int Id;
    private String Login;
    private String Pathword;

    public User(String login, String pathword) {
        Login = login;
        Pathword = pathword;
    }

    public User() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLogin() {
        return Login;
    }

    public String getPathword() {
        return Pathword;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public void setPathword(String pathword) {
        Pathword = pathword;
    }
}
