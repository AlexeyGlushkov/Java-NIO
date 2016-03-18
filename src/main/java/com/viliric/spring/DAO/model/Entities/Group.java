package com.viliric.spring.DAO.model.Entities;

/**
 * Created by alexey on 1/15/16.
 */
public class Group {
    private int Id;
    private String Name;


    public Group(String name) {
        Name = name;
    }

    public Group() {

    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }
}
