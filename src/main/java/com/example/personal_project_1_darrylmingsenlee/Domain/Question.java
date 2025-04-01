package com.example.personal_project_1_darrylmingsenlee.Domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Question {
    int id;
    int category_id;
    String question;
    List<Choice> choiceList;
    Choice correct;

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    boolean suspended;

    public Question(int id, int category_id, String question, List<Choice> choiceList,boolean suspended) {
        this.id = id;
        this.category_id = category_id;
        this.question = question;
        this.choiceList = choiceList;
        Collections.shuffle(this.choiceList);
        Optional<Choice> c = choiceList.stream().filter((choice -> choice.is_answer)).findFirst();
        this.correct = c.orElse(null);
        this.suspended=suspended;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Choice> getChoiceList() {
        return choiceList;
    }

    public void setChoiceList(List<Choice> choiceList) {
        this.choiceList = choiceList;
    }

    public Choice getCorrect() {
        return correct;
    }

    public void setCorrect(Choice correct) {
        this.correct = correct;
    }

}
