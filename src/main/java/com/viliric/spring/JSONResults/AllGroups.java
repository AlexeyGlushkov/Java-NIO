package com.viliric.spring.JSONResults;

import com.viliric.spring.DAO.model.Entities.Group;

import java.util.HashSet;

/**
 * Created by Lenovo on 17.03.2016.
 */
public class AllGroups {
    private HashSet<Group> groups = new HashSet<Group>();

    public HashSet<Group> getGroups() {
        return groups;
    }

    public void setGroups(HashSet<Group> groups) {
        this.groups = groups;
    }
}
