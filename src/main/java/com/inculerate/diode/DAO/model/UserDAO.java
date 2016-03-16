package com.inculerate.diode.DAO.model;

import java.util.List;

/**
 * Created by alexey on 1/15/16.
 */
public interface UserDAO {
    void insert(User user);

    void update(User user);

    void delete(User user);

    boolean authorization(String login, String password);
    boolean loginIsFree(String login);

    User getById(Integer id);

    List<User> getAll();
}
