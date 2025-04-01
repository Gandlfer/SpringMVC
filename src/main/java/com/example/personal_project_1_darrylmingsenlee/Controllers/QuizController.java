package com.example.personal_project_1_darrylmingsenlee.Controllers;

import com.example.personal_project_1_darrylmingsenlee.Domain.Attempt;
import com.example.personal_project_1_darrylmingsenlee.Domain.Choice;
import com.example.personal_project_1_darrylmingsenlee.Domain.Question;
import com.example.personal_project_1_darrylmingsenlee.Domain.User;
import com.example.personal_project_1_darrylmingsenlee.Service.AttemptService;
import com.example.personal_project_1_darrylmingsenlee.Service.QuizService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.personal_project_1_darrylmingsenlee.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class QuizController {
    private final SessionService sessionService;
    private QuizService quizService;
    private AttemptService attemptService;

    @Autowired
    public QuizController(QuizService quizService, AttemptService attemptService, SessionService sessionService) {
        this.quizService = quizService;
        this.attemptService = attemptService;
        this.sessionService = sessionService;
    }

    @GetMapping("/quiz")
    public String getQuiz(Model model, HttpSession session, @RequestParam("id") int id, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession checkSession = request.getSession(false);
        if(checkSession == null || checkSession.getAttribute("user") == null)
            return "redirect:/login";
        User u = (User) checkSession.getAttribute("user");
        if((boolean) checkSession.getAttribute("prevQuiz")){
            System.out.println("Here");
            redirectAttributes.addFlashAttribute("message","You can only start one quiz at a time.");
            return "redirect:/home";
        }
        List<Question> questions = quizService.get3RandomQuestion(id);
        sessionService.addSession(u.getId(),questions);
        session.setAttribute("prevQuiz",true);
        session.setAttribute("questions",questions);
        model.addAttribute("questions", questions);
        return "quiz";
    }

    @PostMapping("/quiz")
    public String submitQuiz(
            HttpServletRequest request,
            HttpSession session
    ) {
        Map<Integer,Integer> answers = new HashMap<>();
        for(String param : request.getParameterMap().keySet()){
            answers.put(Integer.parseInt(param.substring("selectedChoiceId_".length())),Integer.parseInt(request.getParameter(param)));
        }
        User u = (User) session.getAttribute("user");
        session.setAttribute("prevQuiz",false);
        sessionService.deleteSession(u.getId());
        attemptService.addUserAttempt(u.getId(),answers);

        return "redirect:/quiz-result?attempt_id="+attemptService.getAttemptByProfile(u.getId()).get(0).getId();
    }

    @GetMapping("/quiz-result")
    public String getAttemptResult(Model model,HttpSession session,HttpServletRequest request,@RequestParam("attempt_id") int id){
        HttpSession checkSession = request.getSession(false);
        if(checkSession == null || checkSession.getAttribute("user") == null)
            return "redirect:/login";
        //attemptService.getAttemptById(id).getAttempt_questions().forEach(((question, choice) -> System.out.println(question.getQuestion())));
        Attempt attempt = attemptService.getAttemptById(id);
        model.addAttribute("attempt",attempt);
        int correct = 0;
        for(Map.Entry<Question,Choice> question:attempt.getAttempt_questions().entrySet()){
            if(question.getValue().isIs_answer())
                correct++;
        }

        model.addAttribute("correct",correct);
        return "quiz-result";
    }

    @GetMapping("/continue-quiz")
    public String getContinueQuiz(HttpServletRequest request, Model model){
        HttpSession checkSession = request.getSession(false);
        if(checkSession == null || checkSession.getAttribute("user") == null)
            return "redirect:/login";
        User u = (User) checkSession.getAttribute("user");
        model.addAttribute("questionList",sessionService.getSession(u.getId()).getUserAnswer());
        return "continue-quiz";
    }
}
