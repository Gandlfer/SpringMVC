package com.example.personal_project_1_darrylmingsenlee.Domain;

import java.util.List;
import java.util.Map;

public class Session {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Question> getUserAnswer() {
        return questionList;
    }

    public void setUserAnswer(List<Question> questionList) {
        this.questionList = questionList;
    }

    User user;
    List<Question> questionList;

    public Session(User user,List<Question> questionList){
        this.user=user;
        this.questionList=questionList;
    }

}
