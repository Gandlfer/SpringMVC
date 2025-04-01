package com.example.personal_project_1_darrylmingsenlee.Service;

import com.example.personal_project_1_darrylmingsenlee.DAO.AttemptDAO;
import com.example.personal_project_1_darrylmingsenlee.DAO.QuizDAO;
import com.example.personal_project_1_darrylmingsenlee.Domain.Attempt;
import com.example.personal_project_1_darrylmingsenlee.Domain.Choice;
import com.example.personal_project_1_darrylmingsenlee.Domain.Question;
import com.example.personal_project_1_darrylmingsenlee.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttemptService {
    AttemptDAO attemptDAO;
    QuizDAO quizDAO;

    @Autowired
    public AttemptService(AttemptDAO attemptDAO,QuizDAO quizDAO){
        this.attemptDAO=attemptDAO;
        this.quizDAO = quizDAO;
    }

    public void addUserAttempt(int profile_id, Map<Integer,Integer> map){
        attemptDAO.addAttempt(profile_id);
        for(Map.Entry<Integer,Integer> m: map.entrySet()){
            attemptDAO.addQuestionsOfAttempt(attemptDAO.getAttemptByProfileId(profile_id,2,true).get(0).getId(),m.getKey(),m.getValue());;
        }
    }

    public List<Attempt> getAttemptByProfile(int profile_id){
        return attemptDAO.getAttemptByProfileId(profile_id,2,true);
    }
    public Attempt getAttemptById(int attempt_id){
        return attemptDAO.getAttemptById(attempt_id);
    }

    public List<Attempt> getAllAttempt(){
        return attemptDAO.getAllAttempt();
    }
    public List<Attempt> getCurrentPageAttempt(int page,Integer filter_user_id,Integer filter_category_id){
        return attemptDAO.getFilteredAttempts((page - 1)*5,filter_user_id,filter_category_id);
    }
    public int getFilterAttemptCount(Integer filter_user_id,Integer filter_category_id){
        return attemptDAO.getAllFilteredAttempts(filter_user_id, filter_category_id);
    }


}
