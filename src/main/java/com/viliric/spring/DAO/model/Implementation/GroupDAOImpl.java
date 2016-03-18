package com.viliric.spring.DAO.model.Implementation;

import com.viliric.spring.DAO.model.Entities.Group;
import com.viliric.spring.DAO.model.Interfaces.GroupDAO;
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
public class GroupDAOImpl implements GroupDAO {

    private NamedParameterJdbcTemplate namedTemplate;

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private String sql;

    @Autowired
    public void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public void insert(Group group) {
        sql = "INSERT INTO public.groups(name) VALUES (?);";
        jdbcTemplate.update(sql, getPreparedStatementInsert(group));
    }

    @Override
    public void update(Group group) {
        sql = " UPDATE public.groups " +
                "   SET name=? " +
                " WHERE  group_id=? ";
        jdbcTemplate.update(sql, getPreparedStatementUpdate(group));
    }

    @Override
    public void delete(Group group) {
        sql = " DELETE FROM public.groups " +
                " WHERE group_id=? ;";
        jdbcTemplate.update(sql, getPreparedStatementUpdate(group));
    }

    @Override
    public Group getById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM groups WHERE group_id = " + id, rowMapper);
    }

    @Override
    public List<Group> selectAll() {
        return jdbcTemplate.query("SELECT * FROM groups", rowMapper);
    }

    private PreparedStatementSetter getPreparedStatementInsert(final Group group) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 0;
                ps.setString(++i, group.getName());
            }
        };
    }

    private PreparedStatementSetter getPreparedStatementUpdate(final Group group) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 0;
                ps.setString(++i, group.getName());
                ps.setInt(++i, group.getId());
            }
        };
    }

    private RowMapper<Group> rowMapper = new RowMapper<Group>() {
        @Override
        public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group group = new Group();
            group.setId(rs.getInt("group_id"));
            group.setName(rs.getString("name"));
            return group;
        }
    };
}
