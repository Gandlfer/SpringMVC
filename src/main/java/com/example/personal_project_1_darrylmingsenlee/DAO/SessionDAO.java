package com.example.personal_project_1_darrylmingsenlee.DAO;

import com.example.personal_project_1_darrylmingsenlee.Domain.Choice;
import com.example.personal_project_1_darrylmingsenlee.Domain.Question;
import com.example.personal_project_1_darrylmingsenlee.Domain.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SessionDAO {
    private final QuizDAO quizDAO;
    JdbcTemplate jt;
    ProfileDAO profileDAO;

    @Autowired
    public SessionDAO(JdbcTemplate jt, ProfileDAO profileDAO, QuizDAO quizDAO){
        this.jt=jt;
        this.profileDAO=profileDAO;
        this.quizDAO = quizDAO;
    }

    public Session getSessionByProfileID(int profile_id){
        String query="Select quiz_id from session where profile_id=?";
        // RowMapper to map each row to a Question and Choice map
        RowMapper<Question> rm = ((rs, rowNum) -> {
            return quizDAO.getQuestionById(rs.getInt("quiz_id"));
        }
        );
        List<Question> q = jt.query(query,rm,profile_id);
        return !q.isEmpty() ? new Session(profileDAO.getProfileById(profile_id),q):null;
    }
    public void addSessionByProfileID(int profile_id,int quiz_id){
        String query="Insert into session(profile_id,quiz_id) values(?,?)";
        jt.update(query,profile_id,quiz_id);
    }
    public void deleteSessionByProfileID(int profile_id){
        String query="delete from session where profile_id=?";
        jt.update(query,profile_id);
    }
}
