package com.viliric.spring.DAO.model.Interfaces;

import com.viliric.spring.DAO.model.Entities.Group;

import java.util.List;

/**
 * Created by alexey on 1/15/16.
 */
public interface GroupDAO {
    void insert(Group group);
    void update(Group group);
    void delete(Group group);
    Group getById(Integer id);
    List<Group> selectAll();
}
