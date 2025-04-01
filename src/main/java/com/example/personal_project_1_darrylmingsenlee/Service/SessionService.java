package com.example.personal_project_1_darrylmingsenlee.Service;

import com.example.personal_project_1_darrylmingsenlee.DAO.SessionDAO;
import com.example.personal_project_1_darrylmingsenlee.Domain.Question;
import com.example.personal_project_1_darrylmingsenlee.Domain.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {
    SessionDAO sessionDAO;

    @Autowired
    public SessionService(SessionDAO sessionDAO){
        this.sessionDAO=sessionDAO;
    }

    public Session getSession(int profile_id){
        return sessionDAO.getSessionByProfileID(profile_id);
    }

    public void addSession(int profile_id, List<Question> questionList){
        questionList.forEach(question -> sessionDAO.addSessionByProfileID(profile_id, question.getId()));
    }

    public void deleteSession(int profile_id){
        sessionDAO.deleteSessionByProfileID(profile_id);
    }
}
