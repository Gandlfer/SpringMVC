package com.example.personal_project_1_darrylmingsenlee.Service;

import com.example.personal_project_1_darrylmingsenlee.DAO.CredentialDAO;
import com.example.personal_project_1_darrylmingsenlee.DAO.ProfileDAO;
import com.example.personal_project_1_darrylmingsenlee.Domain.User;
import com.example.personal_project_1_darrylmingsenlee.Domain.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class UserLoginService {
    private final CredentialDAO credentialDAO;
    private final ProfileDAO profileDAO;

    @Autowired
    public UserLoginService(CredentialDAO c,ProfileDAO p){
        this.credentialDAO = c;
        this.profileDAO = p;
    }

    public Optional<User> getUser(String username, String password, Model model){
        Optional <Credential> o = Optional.ofNullable(credentialDAO.getCredential(username));
        if(o.isPresent()){
            User u = profileDAO.getProfileByCredentialId(o.get().getId());
            if(!o.get().getPassword().equals(password)){
                model.addAttribute("message","Password is incorrect!");
                return Optional.empty();
            }
            else if(u.isIs_suspended()) {
                model.addAttribute("message","User is suspended!");
                return Optional.empty();
            }
            else{
                return Optional.of(u);
            }
        }
        else{
            model.addAttribute("message","User does not exist!");
            return Optional.empty();
        }
        //System.out.println(o.get().getId());
        //return o.isPresent() ? Optional.ofNullable(profileDAO.getProfileByCredentialId(o.get().getId())):Optional.empty();
    }

    public void createNewUser(String username,String password,String firstname,String lastname,int role_id){
        if(!Optional.ofNullable(credentialDAO.getCredential(username)).isPresent()){
            credentialDAO.addCredentials(username,password);
            profileDAO.addNewProfile(firstname, lastname, role_id, credentialDAO.getCredential(username).getId());
        }
    }
    public Optional<Credential> checkUserExist(String username){
        return Optional.ofNullable(credentialDAO.getCredential(username));
    }
}
