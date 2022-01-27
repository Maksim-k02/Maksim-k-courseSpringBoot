package com.epam.brest.dao;


import com.epam.brest.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class StudentDaoJDBCImpl implements StudentDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_ALL_STUDENTS}")
    private String sqlGetAllStudent;

    @Value("${SQL_CREATE_STUDENT}")
    private String sqlCreateStudent;

    public StudentDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate= namedParameterJdbcTemplate;
    }

    @Override
    public List<Student> findAll() {
        return namedParameterJdbcTemplate.query(sqlGetAllStudent,new StudentRowMapper());
    }

    @Override
    public Integer create(Student student) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("studentName", student.getStudentName().toUpperCase());
        KeyHolder keyHolder =  new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateStudent, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(Student student) {
        return null;
    }

    @Override
    public Integer delete(Integer studentId) {
        return null;
    }

    private class StudentRowMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student = new Student();
            student.setStudentId(resultSet.getInt("student_id"));
            student.setStudentName(resultSet.getString("student_name"));
            return student;
        }
    }
}
