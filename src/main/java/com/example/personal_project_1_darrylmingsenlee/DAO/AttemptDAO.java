package com.example.personal_project_1_darrylmingsenlee.DAO;

import com.example.personal_project_1_darrylmingsenlee.Domain.Attempt;
import com.example.personal_project_1_darrylmingsenlee.Domain.Choice;
import com.example.personal_project_1_darrylmingsenlee.Domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AttemptDAO {
    private final QuizDAO quizDAO;
    JdbcTemplate jt;
    @Autowired
    public AttemptDAO(JdbcTemplate jt, QuizDAO quizDAO){
        this.jt= jt;
        this.quizDAO = quizDAO;
    }

    public void addAttempt(int profile_id){
        String query = "insert into user_quiz_attempt(profile_id) values (?)";
        System.out.println("insert into user_quiz_attempt(profile_id) values ("+profile_id+")");
        jt.update(query,profile_id);
    }
    public void addQuestionsOfAttempt(int attempt_id,int quiz_id,int selectedChoice_id){
        String query = "insert into user_quiz_attempt_question(quiz_id,user_choice_id,attempt_id) values(?,?,?)";
        System.out.println("insert into user_quiz_attempt_question(quiz_id,user_choice_id,attempt_id) values("+quiz_id+","+selectedChoice_id+","+attempt_id+")");
        jt.update(query,quiz_id,selectedChoice_id,attempt_id);
    }
    public Map<Question, Choice> getAttemptQuestionsById(int attempt_id) {
        String query = "select id,quiz_id,user_choice_id,attempt_id from user_quiz_attempt_question where attempt_id = ?";

        return jt.query(query, (PreparedStatementSetter) ps -> {
            ps.setInt(1, attempt_id);
        }, rs -> {
            Map<Question, Choice> tempMap = new LinkedHashMap<>();
            while (rs.next()) {
                Question q = quizDAO.getQuestionById(rs.getInt("quiz_id"));
                int userChoice = rs.getInt("user_choice_id");
                tempMap.put(q, q.getChoiceList().stream().filter(choice -> choice.getId()==userChoice).findFirst().get());
            }
            return tempMap;
        });
    }

    public List<Attempt> getAttemptByProfileId(int profile_id){
        String query = "select id,profile_id,attempt_date from user_quiz_attempt where profile_id = ?";
        RowMapper<Attempt> rm = (rs,rowNum)->{return new Attempt(rs.getInt("id"),rs.getInt("profile_id"),
                rs.getTimestamp("attempt_date").toLocalDateTime(),getAttemptQuestionsById(rs.getInt("id")));};
        return jt.query(query,rm,profile_id);
    }
    public List<Attempt> getAttemptByProfileId(int profile_id,int sortBy,boolean desc){
        StringBuilder query = new StringBuilder("select id,profile_id,attempt_date from user_quiz_attempt where profile_id = ?");
        String col = "";
        switch (sortBy){
            case 1:
                col="id";
                break;
            case 2:
                col="attempt_date";
                break;
        }
        if(!col.isEmpty()){
            query.append(" order by ").append(col);
        }
        if(desc){
            query.append(" desc");
        }
        RowMapper<Attempt> rm = (rs,rowNum)->{return new Attempt(rs.getInt("id"),rs.getInt("profile_id"),
                rs.getTimestamp("attempt_date").toLocalDateTime(),getAttemptQuestionsById(rs.getInt("id")));};
        return jt.query(query.toString(),rm,profile_id);
    }

    public Attempt getAttemptById(int id){
        String query = "select id,profile_id,attempt_date from user_quiz_attempt where id = ?";
        RowMapper<Attempt> rm = (rs,rowNum)->{return new Attempt(rs.getInt("id"),rs.getInt("profile_id"),
                rs.getTimestamp("attempt_date").toLocalDateTime(),getAttemptQuestionsById(rs.getInt("id")));};
        return jt.query(query,rm,id).get(0);
    }

    public List<Attempt> getAllAttempt(){
        String query = "select id,profile_id,attempt_date from user_quiz_attempt order by attempt_date desc";
        RowMapper<Attempt> rm = (rs,rowNum)->{return new Attempt(rs.getInt("id"),rs.getInt("profile_id"),
                rs.getTimestamp("attempt_date").toLocalDateTime(),getAttemptQuestionsById(rs.getInt("id")));};
        return jt.query(query,rm);
    }
    public List<Attempt> getAllAttemptByOffset(int offset){
        String query = "select id,profile_id,attempt_date from user_quiz_attempt order by attempt_date desc limit 5 offset ?";
        RowMapper<Attempt> rm = (rs,rowNum)->{return new Attempt(rs.getInt("id"),rs.getInt("profile_id"),
                rs.getTimestamp("attempt_date").toLocalDateTime(),getAttemptQuestionsById(rs.getInt("id")));};
        return jt.query(query,rm,offset);
    }
    String temp ="select id,profile_id,attempt_date from user_quiz_attempt" +
            " where profile_id= ?" +
            " and" +
            " id in (select distinct attempt_id from user_quiz_attempt_question where quiz_id in (select id from quiz where category_id = ?))" +
            " order by attempt_date desc limit 5 offset ?";
    public List<Attempt> getFilteredAttempts(int offset, Integer filter_user_id,Integer filter_category_id){
        StringBuilder query = new StringBuilder("select id,profile_id,attempt_date from user_quiz_attempt");
        if (filter_user_id!=null){
            query.append(" where profile_id= ?");
        }
        if (filter_user_id!=null && filter_category_id!=null){
            query.append(" and");
        }
        if (filter_category_id!=null){
            query.append(" id in (select distinct attempt_id from user_quiz_attempt_question where quiz_id in (select id from quiz where category_id = ?))");
        }
        query.append(" order by attempt_date desc limit 5 offset ?");
        RowMapper<Attempt> rm = (rs,rowNum)->{return new Attempt(rs.getInt("id"),rs.getInt("profile_id"),
                rs.getTimestamp("attempt_date").toLocalDateTime(),getAttemptQuestionsById(rs.getInt("id")));};
        if(filter_user_id!=null && filter_category_id!=null){
            return jt.query(query.toString(),rm,filter_user_id,filter_category_id,offset);
        }
        else if(filter_user_id!=null){
            return jt.query(query.toString(),rm,filter_user_id,offset);
        }
        else if(filter_category_id!=null){
            return jt.query(query.toString(),rm,filter_category_id,offset);
        }
        return jt.query(query.toString(),rm,offset);
    }
    public Integer getAllFilteredAttempts(Integer filter_user_id,Integer filter_category_id){
        StringBuilder query = new StringBuilder("select count(id) from user_quiz_attempt");
        if (filter_user_id!=null){
            query.append(" where profile_id= ?");
        }
        if (filter_user_id!=null && filter_category_id!=null){
            query.append(" and");
        }
        if (filter_category_id!=null){
            query.append(" id in (select distinct attempt_id from user_quiz_attempt_question where quiz_id in (select id from quiz where category_id = ?))");
        }

        if(filter_user_id!=null && filter_category_id!=null){
            return jt.queryForObject(query.toString(),Integer.class,filter_user_id,filter_category_id);
        }
        else if(filter_user_id!=null){
            return jt.queryForObject(query.toString(),Integer.class,filter_user_id);
        }
        else if(filter_category_id!=null){
            return jt.queryForObject(query.toString(),Integer.class,filter_category_id);
        }
        return jt.queryForObject(query.toString(),Integer.class);
    }
}
