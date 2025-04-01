package com.example.personal_project_1_darrylmingsenlee.DAO;

import com.example.personal_project_1_darrylmingsenlee.Domain.Choice;
import com.example.personal_project_1_darrylmingsenlee.Domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class QuizDAO {
    JdbcTemplate jt;
    @Autowired
    public QuizDAO(JdbcTemplate jt){
        this.jt=jt;
    }
    public List<Choice> getAllChoiceForQuestionId(int id){
        String query = "select id,choice,is_correct from choice where quiz_id = ?";
        RowMapper<Choice> rm = ((rs, rowNum) -> {return new Choice(rs.getInt("id"),rs.getString("choice"),rs.getBoolean("is_correct"));});
        return jt.query(query,rm,id);
    }
    public Question getQuestionById(int id){
        String query = "Select id,category_id,question,suspended from quiz where id = ?";
        RowMapper<Question> rm = ((rs,rowNum)->{return new Question(
                rs.getInt("id"),rs.getInt("category_id"),rs.getString("question"),getAllChoiceForQuestionId(rs.getInt("id")),rs.getBoolean("suspended"));
        }
        );
        return jt.query(query,rm,id).get(0);
    }
    public List<Question> getQuestionByCategoryId(int category_id){
        String query = "Select id,category_id,question,suspended from quiz where category_id = ? and suspended = false";
        RowMapper<Question> rm = ((rs,rowNum)->{return new Question(
                rs.getInt("id"),rs.getInt("category_id"),rs.getString("question"),getAllChoiceForQuestionId(rs.getInt("id")),rs.getBoolean("suspended"));
            }
        );
        return jt.query(query,rm,category_id);
    }
    public List<Question> getAllQuestion(){
        String query = "Select id,category_id,question,suspended from quiz order by category_id";
        RowMapper<Question> rm = ((rs,rowNum)->{return new Question(
                rs.getInt("id"),rs.getInt("category_id"),rs.getString("question"),getAllChoiceForQuestionId(rs.getInt("id")),rs.getBoolean("suspended"));
        }
        );
        return jt.query(query,rm);
    }
    public void updateQuestion(int id,String question,boolean suspended){
        String query = "Update quiz set question = ?, suspended = ? where id = ?";
        jt.update(query,question,suspended,id);
    }
    public void updateChoice(int choice_id,String answer,boolean is_answer){
        String query = "Update choice set choice = ?, is_correct=? where id = ?";
        jt.update(query,answer,is_answer,choice_id);
    }

    public int addQuestion(int category_id,String question,boolean suspended){
        String sql = "INSERT INTO quiz (question,category_id,suspended) VALUES (?,?,?)";
        // Create KeyHolder to capture generated ID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Execute the insert and capture the generated key
        jt.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, question);
            ps.setInt(2, category_id);
            ps.setBoolean(3, suspended);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
    public void addChoice(int quiz_id,String choice,boolean is_correct){
        String sql = "INSERT INTO choice (quiz_id,choice,is_correct) VALUES (?,?,?)";
        jt.update(sql,quiz_id,choice,is_correct);
    }
}
