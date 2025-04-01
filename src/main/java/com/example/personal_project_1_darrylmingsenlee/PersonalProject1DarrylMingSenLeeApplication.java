package com.example.personal_project_1_darrylmingsenlee;

import com.example.personal_project_1_darrylmingsenlee.DAO.CredentialDAO;
import com.example.personal_project_1_darrylmingsenlee.DAO.ProfileDAO;
import com.example.personal_project_1_darrylmingsenlee.Service.UserLoginService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class PersonalProject1DarrylMingSenLeeApplication {

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(PersonalProject1DarrylMingSenLeeApplication.class, args);
//        UserLoginService loginService = (UserLoginService) ac.getBean("userLoginService");
//        loginService.createNewUser("test1","test1","Darryl","Lee",1);



    }

}
