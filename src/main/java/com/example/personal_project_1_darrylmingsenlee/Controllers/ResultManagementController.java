package com.example.personal_project_1_darrylmingsenlee.Controllers;

import com.example.personal_project_1_darrylmingsenlee.DAO.ProfileDAO;
import com.example.personal_project_1_darrylmingsenlee.Domain.Attempt;
import com.example.personal_project_1_darrylmingsenlee.Domain.Category;
import com.example.personal_project_1_darrylmingsenlee.Domain.User;
import com.example.personal_project_1_darrylmingsenlee.Service.AttemptService;
import com.example.personal_project_1_darrylmingsenlee.Service.CategoryService;
import com.example.personal_project_1_darrylmingsenlee.Service.UserLoginService;
import com.example.personal_project_1_darrylmingsenlee.Service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ResultManagementController {
    private final CategoryService categoryService;
    private final ProfileDAO profileDAO;
    private final UserLoginService userLoginService;
    private final UserManagementService userManagementService;
    AttemptService attemptService;

    @Autowired
    public ResultManagementController(AttemptService attemptService, CategoryService categoryService, ProfileDAO profileDAO, UserLoginService userLoginService, UserManagementService userManagementService){
        this.attemptService=attemptService;
        this.categoryService = categoryService;
        this.profileDAO = profileDAO;
        this.userLoginService = userLoginService;
        this.userManagementService = userManagementService;
    }

    @GetMapping("/result-management")
    public String getAllAttempt(@RequestParam(value = "page",defaultValue = "1") int page,
                                @RequestParam(value = "userId", required = false) Integer filter_user_id,
                                @RequestParam(value = "categoryId", required = false) Integer filter_categoryId,
                                Model model){
        List<Attempt> pagesOfAttempts = attemptService.getCurrentPageAttempt(page,filter_user_id,filter_categoryId);
        Map<Integer,String> categoryMap = new HashMap<>();
        Map<Integer, User> profileMap = new HashMap<>();
        pagesOfAttempts.forEach((attempt -> {
            int categoryId= attempt.getAttempt_questions().keySet().stream().findFirst().get().getCategory_id();
            if(!categoryMap.containsKey(categoryId)){
                Category c = categoryService.getCategory(categoryId);
                categoryMap.put(c.getId(),c.getCategory());
            }
            if(!profileMap.containsKey(attempt.getProfile_id())){
                User u = profileDAO.getProfileById(attempt.getProfile_id());
                profileMap.put(u.getId(),u);
            }
        }));
        model.addAttribute("pageOfAttempts",pagesOfAttempts);
        model.addAttribute("totalPages", (int) Math.ceil((double) attemptService.getFilterAttemptCount(filter_user_id,filter_categoryId)/ 5 ));
        model.addAttribute("currentPage",page);
        model.addAttribute("profileMap",profileMap);
        model.addAttribute("allProfile",userManagementService.getAllUser());
        model.addAttribute("allCategory",categoryService.getAllCategory());
        model.addAttribute("categoryMap",categoryMap);
        return "ResultManagement";
    }
}
