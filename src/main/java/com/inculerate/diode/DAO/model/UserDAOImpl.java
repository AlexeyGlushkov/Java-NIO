package com.inculerate.diode.DAO.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String sql = "INSERT INTO users(login, password) VALUES(?,?)";
        jdbcTemplate.update(sql, getPreparedStatementInsert(user));
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET pathword=? WHERE id=?";
        jdbcTemplate.update(sql, getPreparedStatementUpdate(user));
    }

    @Override
    public void delete(User user) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", user.getId());
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
            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.setPathword(rs.getString("pathword"));
            return user;
        }
    };

    private PreparedStatementSetter getPreparedStatementUpdate(final User user) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
            int i = 0;
            ps.setString(++i, user.getPathword());
            }
        };
    }

    private PreparedStatementSetter getPreparedStatementInsert(final User user) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 0;
                ps.setString(++i, user.getLogin());
                ps.setString(++i, user.getPathword());
            }
        };
    }
}
