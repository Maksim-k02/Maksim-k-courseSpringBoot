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

    @Value("${SQL_GET_STUDENT_BY_ID}")
    private String sqlGetStudentById;

    @Value("${SQL_UPDATE_STUDENT}")
    private String sqlUpdateStudent;

    @Value("${SQL_DELETE_STUDENT_BY_ID}")
    private String sqlDeleteStudentById;

    public StudentDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate= namedParameterJdbcTemplate;
    }

    @Override
    public List<Student> findAll() {
        return namedParameterJdbcTemplate.query(sqlGetAllStudent,new StudentRowMapper());
    }

    @Override
    public Student getStudentById(Integer studentId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("studentId",studentId);
        return namedParameterJdbcTemplate.queryForObject(sqlGetStudentById,sqlParameterSource,new StudentRowMapper());
    }

    @Override
    public Integer create(Student student) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("studentName", student.getStudentName())
                .addValue("email",student.getEmail())
                .addValue("courseNumber", student.getCourseNumber())
                .addValue("courseId", student.getCourseId())
                .addValue("studentDate", student.getStudentDate());
        KeyHolder keyHolder =  new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateStudent, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(Student student) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("studentId", student.getStudentId())
                .addValue("studentName", student.getStudentName())
                .addValue("email",student.getEmail())
                .addValue("courseNumber", student.getCourseNumber())
                .addValue("courseId", student.getCourseId())
                .addValue("studentDate", student.getStudentDate());
        return namedParameterJdbcTemplate.update(sqlUpdateStudent,sqlParameterSource);
    }

    @Override
    public Integer delete(Integer studentId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("studentId", studentId);
        return namedParameterJdbcTemplate.update(sqlDeleteStudentById, sqlParameterSource);
    }

    private class StudentRowMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student = new Student();
            student.setStudentId(resultSet.getInt("student_id"));
            student.setStudentName(resultSet.getString("student_name"));
            student.setEmail(resultSet.getString("email"));
            student.setCourseNumber(resultSet.getInt("course_number"));
            student.setCourseId(resultSet.getInt("course_id"));
            student.setStudentDate(resultSet.getDate("student_date").toLocalDate());
            return student;
        }
    }
}
