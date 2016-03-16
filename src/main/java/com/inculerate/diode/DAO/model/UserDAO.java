package com.inculerate.diode.DAO.model;

import java.util.List;

/**
 * Created by alexey on 1/15/16.
 */
public interface UserDAO {
    void insert(User user);
    void updatePassword(User user);
    void updateUserInfo(User user);

    boolean authorization(String login, String password);
    boolean loginIsFree(String login);

    User getById(Integer id);

    List<User> getAll();
}
