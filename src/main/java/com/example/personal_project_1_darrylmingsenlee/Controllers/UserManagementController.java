package com.example.personal_project_1_darrylmingsenlee.Controllers;

import com.example.personal_project_1_darrylmingsenlee.Service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserManagementController {

    UserManagementService userManagementService;

    @Autowired
    public UserManagementController(UserManagementService userManagementService){
        this.userManagementService=userManagementService;
    }
    @GetMapping("/user-management")
    public String getPageOfUser(@RequestParam(value = "page",defaultValue = "1") int page, Model model){
        model.addAttribute("pageOfUsers",userManagementService.getCurrentPageUserResult(page));
        model.addAttribute("totalPages", (int) Math.ceil((double) userManagementService.getAllProfileCount()/ 5 ));
        model.addAttribute("currentPage",page);
        return "UserManagement";
    }
    @PostMapping("/suspend-user")
    public String suspendUser(@RequestParam(value = "page",defaultValue = "1") int page,@RequestParam(value = "profile_id") int profile_id){
        userManagementService.updateSuspendedUser(profile_id);
        return "redirect:/user-management?page="+page;
    }
}
