package com.example.personal_project_1_darrylmingsenlee.Controllers;

import com.example.personal_project_1_darrylmingsenlee.Domain.Attempt;
import com.example.personal_project_1_darrylmingsenlee.Domain.Category;
import com.example.personal_project_1_darrylmingsenlee.Domain.User;
import com.example.personal_project_1_darrylmingsenlee.Service.AttemptService;
import com.example.personal_project_1_darrylmingsenlee.Service.CategoryService;
import com.example.personal_project_1_darrylmingsenlee.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class HomeController {
    private final AttemptService attemptService;
    private final QuizService quizService;
    private CategoryService categoryService;

    @Autowired
    public HomeController(CategoryService categoryService, AttemptService attemptService, QuizService quizService){
        this.categoryService=categoryService;
        this.attemptService = attemptService;
        this.quizService = quizService;
    }

    @GetMapping("/home")
    public String getHome(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("user") == null)
            return "redirect:/login";
        User u = (User) session.getAttribute("user");

        HashMap<Integer,String> map = new HashMap<>();
        List<Category> categoryList=categoryService.getAllCategory();
        model.addAttribute("category",categoryList);
        for (Category category : categoryService.getAllCategory()) {
            map.put(category.getId(),category.getCategory());
        }
        model.addAttribute("categoryMap",map);

        List<Attempt> attempts= attemptService.getAttemptByProfile(u.getId());
        if(!attempts.isEmpty()){
            attempts = (attempts.size()>5) ?attempts.subList(0,5):attempts;
        }
        model.addAttribute("attempts",attempts);

        return "home";
    }
}
