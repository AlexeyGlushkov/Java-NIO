package com.viliric.spring.DAO.model;

import java.util.List;

/**
 * Created by alexey on 1/15/16.
 */
public interface StudentDAO {
    void insert(Student book);

    void update(Student book);

    void delete(Student book);

    Student getById(Integer id);

    List<Student> getAll();
}
