package com.example.personal_project_1_darrylmingsenlee.Controllers;

import com.example.personal_project_1_darrylmingsenlee.Domain.Choice;
import com.example.personal_project_1_darrylmingsenlee.Domain.Question;
import com.example.personal_project_1_darrylmingsenlee.Service.QuizManagementService;
import com.example.personal_project_1_darrylmingsenlee.Service.CategoryService;
import com.example.personal_project_1_darrylmingsenlee.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AdminQuizController {

    private final CategoryService categoryService;
    QuizService quizService;
    QuizManagementService quizManagementService;
    @Autowired
    public AdminQuizController(QuizService quizService, CategoryService categoryService, QuizManagementService quizManagementService){
        this.quizService=quizService;
        this.categoryService = categoryService;
        this.quizManagementService = quizManagementService;
    }

    @GetMapping("/question-management")
    public String getAllQuiz(Model model) {
        model.addAttribute("quizList", quizManagementService.getAllQuiz());
        model.addAttribute("categoryList",categoryService.getAllCategory());
        return "QuestionManagement";
    }
    @GetMapping("/question-edit")
    public String getQuiz(@RequestParam("quiz_id") int id, Model model) {
        model.addAttribute("question",quizService.getQuizById(id));
//        model.addAttribute("quizList",adminQuizService.getAllQuiz());
//        model.addAttribute("categoryList",categoryService.getAllCategory());
        return "QuestionEdit";
    }
    @PostMapping("/question-edit")
    public String postQuiz(
            @RequestParam("question") String questionText,
            @RequestParam("id") int quizId,
            @RequestParam("correctChoiceId") int correctChoiceId,
            @RequestParam(value = "suspended", required = false,defaultValue = "false") boolean suspended,
            @RequestParam Map<String, String> choices) {
        System.out.println(suspended);
        Question question = new Question(quizId, 0, questionText, new ArrayList<Choice>(),suspended);

        List<Choice> choiceList = new ArrayList<>();

        for (Map.Entry<String, String> entry : choices.entrySet()) {
            String paramName = entry.getKey();

            if (paramName.startsWith("choice_")) {

                int choiceId = Integer.parseInt(paramName.substring("choice_".length()));
                String choiceText = entry.getValue();


                Choice choice = new Choice(choiceId, choiceText, choiceId == correctChoiceId);


                choiceList.add(choice);
            }
        }

        question.setChoiceList(choiceList);

        Choice correctChoice = new Choice(correctChoiceId, choices.get("choice_" + correctChoiceId), true);
        question.setCorrect(correctChoice);

        quizManagementService.updateQuestion(question);
        return "redirect:/question-management";
    }

    @GetMapping("/add-question")
    public String getAddQuestion(Model model){
        model.addAttribute("categoryList",categoryService.getAllCategory());
        return "AddQuestion";
    }
    @PostMapping("/add-question")
    public String postAddQuestion(
            @RequestParam(value = "suspended",required = false ,defaultValue = "false") boolean suspended,
            @RequestParam("category_id") int category_id,
            @RequestParam("question") String question,
            @RequestParam("choice_1") String choice1,
            @RequestParam("choice_2") String choice2,
            @RequestParam("choice_3") String choice3,
            @RequestParam("choice_4") String choice4,
            @RequestParam("correctChoice") int correctChoice){

        List<Choice> choiceList = new ArrayList<>();
        choiceList.add(new Choice(1,choice1,false));
        choiceList.add(new Choice(2,choice2,false));
        choiceList.add(new Choice(3,choice3,false));
        choiceList.add(new Choice(4,choice4,false));
        choiceList.get(correctChoice-1).setIs_answer(true);
        quizManagementService.addQuiz(new Question(1,category_id,question,choiceList,suspended));

        return "redirect:/question-management";
    }
}
