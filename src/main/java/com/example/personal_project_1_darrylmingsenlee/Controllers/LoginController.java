package com.example.personal_project_1_darrylmingsenlee.Controllers;

import com.example.personal_project_1_darrylmingsenlee.Domain.User;
import com.example.personal_project_1_darrylmingsenlee.Service.SessionService;
import com.example.personal_project_1_darrylmingsenlee.Service.UserLoginService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    private final SessionService sessionService;
    private UserLoginService loginService;

    @Autowired
    public LoginController(UserLoginService usl, SessionService sessionService){
        this.loginService = usl;
        this.sessionService = sessionService;
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        // redirect to /quiz if user is already logged in
        if (session != null && session.getAttribute("user") != null) {
            return "redirect:/quiz";
        }

        return "test";
    }
    @GetMapping("/login")
    public String getLogin(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        // redirect to /quiz if user is already logged in
        if (session != null && session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        //System.out.println("Here");
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String username,
                            @RequestParam String password,
                            HttpServletRequest request, Model model) {

        Optional<User> possibleUser = loginService.getUser(username, password,model);

        if(possibleUser.isPresent()) {
            HttpSession oldSession = request.getSession(false);
            // invalidate old session if it exists
            if (oldSession != null) oldSession.invalidate();

            // generate new session
            HttpSession newSession = request.getSession(true);

            // store user details in session
            newSession.setAttribute("user", possibleUser.get());
            newSession.setAttribute("prevQuiz",!(sessionService.getSession(possibleUser.get().getId())==null));
            return "redirect:/home";
        } else { // if user details are invalid
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession oldSession = request.getSession(false);
        // invalidate old session if it exists
        if(oldSession != null) oldSession.invalidate();
        return "login";
    }

    @GetMapping("/create-account")
    public String getCreateAccount(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("user") != null)
            return "redirect:/home";
        return "createAccount";
    }
    @PostMapping("/create-account")
    public String postCreateAccount(@RequestParam String username,
                                @RequestParam String password,
                                    @RequestParam String firstname,
                                    @RequestParam String lastname,
                                HttpServletRequest request,Model model){
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("user") != null) return "redirect:/home";
        if(!loginService.checkUserExist(username).isPresent()){
            model.addAttribute("message","Created Account");
            loginService.createNewUser(username,password,firstname,lastname,1);
            return "redirect:/login";
        }
        else{
            model.addAttribute("message","User already exist");
        }
        return "createAccount";

    }
}
