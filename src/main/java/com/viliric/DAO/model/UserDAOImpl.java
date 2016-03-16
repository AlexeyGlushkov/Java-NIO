package com.viliric.DAO.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


/**
 * Created by alexey on 1/15/16.
 */

@Repository
@Service
public class UserDAOImpl implements UserDAO {

    private NamedParameterJdbcTemplate namedTemplate;

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO users(" +
                "            login, password, surname, name, patronymic, email)" +
                "    VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, getPreparedStatementInsert(user));
    }

    @Override
    public void updatePassword(User user) {
        String sql = "UPDATE users SET password=? WHERE user_id=?";
        jdbcTemplate.update(sql, getPreparedStatementUpdatePassword(user));
    }

    @Override
    public void updateUserInfo(User user) {
        String sql = "UPDATE users " +
                " SET surname=?, name=?, " +
                " patronymic=?, email=? " +
                " WHERE user_id=?;";
        jdbcTemplate.update(sql, getPreparedStatementUserInfo(user));
    }

    @Override
    public boolean authorization(final String login, final String password) {
        SimpleJdbcCall sjc = new SimpleJdbcCall(jdbcTemplate).withFunctionName("auth");
        SqlParameterSource in = new MapSqlParameterSource().addValue("lg", login).addValue("ps", password);
        int result = sjc.executeFunction(int.class, in);
        return result == 1;
    }

    @Override
    public boolean loginIsFree(String login) {
        SimpleJdbcCall sjc = new SimpleJdbcCall(jdbcTemplate).withFunctionName("loginIsFree");
        SqlParameterSource in = new MapSqlParameterSource().addValue("lg", login);
        int result = sjc.executeFunction(int.class, in);
        return result == 0;
    }

    @Override
    public User getById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users", rowMapper);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users", rowMapper);
    }

    private RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setSurname(rs.getString("surname"));
            user.setName(rs.getString("name"));
            user.setPatronymic(rs.getString("patronymic"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    };

    private PreparedStatementSetter getPreparedStatementUpdatePassword(final User user) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
            int i = 0;
            ps.setString(++i, user.getPassword());
            ps.setInt(++i, user.getId());
            }
        };
    }

    private PreparedStatementSetter getPreparedStatementUserInfo(final User user) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 0;
                ps.setString(++i, user.getSurname());
                ps.setString(++i, user.getName());
                ps.setString(++i, user.getPatronymic());
                ps.setString(++i, user.getEmail());
                ps.setInt(++i, user.getId());
            }
        };
    }

    private PreparedStatementSetter getPreparedStatementInsert(final User user) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 0;
                ps.setString(++i, user.getLogin());
                ps.setString(++i, user.getPassword());
                ps.setString(++i, user.getSurname());
                ps.setString(++i, user.getName());
                ps.setString(++i, user.getPatronymic());
                ps.setString(++i, user.getEmail());
            }
        };
    }
}
