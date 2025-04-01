package com.example.personal_project_1_darrylmingsenlee.Domain;

public class Choice {
    int id;
    String answer;

    public boolean isIs_answer() {
        return is_answer;
    }

    public void setIs_answer(boolean is_answer) {
        this.is_answer = is_answer;
    }

    boolean is_answer;
    public Choice(int id, String answer,boolean is_answer) {
        this.id = id;
        this.answer = answer;
        this.is_answer = is_answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }



}
