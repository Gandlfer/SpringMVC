package com.example.personal_project_1_darrylmingsenlee.Service;

import com.example.personal_project_1_darrylmingsenlee.DAO.QuizDAO;
import com.example.personal_project_1_darrylmingsenlee.Domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuizService {
    private final QuizDAO quizDAO;

    @Autowired
    public QuizService(QuizDAO quizDAO){
        this.quizDAO=quizDAO;
    }
    public List<Question> get3RandomQuestion(int category_id){
        return getRandomItems(quizDAO.getQuestionByCategoryId(category_id),3);
    }
    public List<Question> getRandomItems(List<Question> list, int n) {
        if (n > list.size()) {
            return list;
        }

        // Create a copy to avoid modifying the original list
        List<Question> copy = new ArrayList<>(list);

        // Shuffle the copy
        Collections.shuffle(copy);

        // Return the first n elements
        return copy.subList(0, n);
    }
    public Question getQuizById(int id){
        return quizDAO.getQuestionById(id);
    }
}
