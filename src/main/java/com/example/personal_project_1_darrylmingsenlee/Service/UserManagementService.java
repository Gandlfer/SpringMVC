package com.example.personal_project_1_darrylmingsenlee.Service;

import com.example.personal_project_1_darrylmingsenlee.DAO.ProfileDAO;
import com.example.personal_project_1_darrylmingsenlee.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagementService {
    ProfileDAO profileDAO;

    @Autowired
    public UserManagementService(ProfileDAO profileDAO){
        this.profileDAO=profileDAO;
    }
    public List<User> getCurrentPageUserResult(int page){
        return profileDAO.getAllProfileByOffset((page - 1)*5);
    }
    public int getAllProfileCount(){
        return profileDAO.getAllProfile().size();
    }
    public List<User> getAllUser(){
        return profileDAO.getAllProfile();
    }
    public void updateSuspendedUser(int profile_id){
        profileDAO.getProfileById(profile_id);
        profileDAO.updateSuspendProfile(profile_id,!profileDAO.getProfileById(profile_id).isIs_suspended());
    }
}
