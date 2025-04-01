package com.example.personal_project_1_darrylmingsenlee.Service;

import com.example.personal_project_1_darrylmingsenlee.DAO.QuizDAO;
import com.example.personal_project_1_darrylmingsenlee.Domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizManagementService {
    QuizDAO quizDAO;

    @Autowired
    public QuizManagementService(QuizDAO quizDAO){
        this.quizDAO = quizDAO;
    }

    public void updateQuestion(Question q){
        quizDAO.updateQuestion(q.getId(),q.getQuestion(),q.isSuspended());
        q.getChoiceList().forEach((choice -> quizDAO.updateChoice(choice.getId(),choice.getAnswer(), choice.getId() == q.getCorrect().getId())));
    }

    public List<Question> getAllQuiz(){
        return quizDAO.getAllQuestion();
    }

    public void addQuiz(Question question){
        int quiz_id=quizDAO.addQuestion(question.getCategory_id(),question.getQuestion(),question.isSuspended());
        question.getChoiceList().forEach(choice -> {quizDAO.addChoice(quiz_id,choice.getAnswer(), choice.isIs_answer());});
    }
}
