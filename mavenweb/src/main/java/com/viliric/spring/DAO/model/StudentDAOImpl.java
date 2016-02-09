package com.viliric.spring.DAO.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by alexey on 1/15/16.
 */

@Repository
@Service
public class StudentDAOImpl implements StudentDAO {

    private NamedParameterJdbcTemplate namedTemplate;

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public void insert(Student student) {
        String sql = "INSERT INTO students(surname, name, patronymic) VALUES(?,?,?)";
        jdbcTemplate.update(sql, getPreparedStatementSetter(student));
    }

    @Override
    public void update(Student book) {
        String sql = "UPDATE students SET surname=?, name=?, patronymic=? WHERE id=?";
        jdbcTemplate.update(sql, getPreparedStatementSetter(book));
    }

    @Override
    public void delete(Student book) {
        jdbcTemplate.update("DELETE FROM students WHERE id=?", book.getId());
    }

    @Override
    public Student getById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM students", rowMapper);
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query("SELECT * FROM students", rowMapper);
    }

    private RowMapper<Student> rowMapper = new RowMapper<Student>() {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student book = new Student();
            book.setId(rs.getInt("id"));
            book.setSurname(rs.getString("surname"));
            book.setName(rs.getString("name"));
            book.setPatronymic(rs.getString("patronymic"));
            return book;
        }
    };

    private PreparedStatementSetter getPreparedStatementSetter(final Student student) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 0;
                ps.setString(++i, student.getSurname());
                ps.setString(++i, student.getName());
                ps.setString(++i, student.getPatronymic());
                //ps.setInt(++i, student.getId());
            }
        };
    }
}
