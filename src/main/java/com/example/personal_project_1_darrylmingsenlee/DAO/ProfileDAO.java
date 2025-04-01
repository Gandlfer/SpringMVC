package com.example.personal_project_1_darrylmingsenlee.DAO;

import com.example.personal_project_1_darrylmingsenlee.Domain.Role;
import com.example.personal_project_1_darrylmingsenlee.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileDAO {
    JdbcTemplate jt;

    @Autowired
    public ProfileDAO(JdbcTemplate jt){
        this.jt = jt ;
    }

    public List<User> getAllProfile(){
        String query = "Select p.id,c.username,r.role,p.firstname,p.lastname,p.role_id,p.suspended from profile p join credential c on c.id = p.credential_id join role r on p.role_id = r.id";
        RowMapper<User> rm = (rs,row) ->{
            return new User(rs.getInt("p.id"),rs.getString("c.username"),rs.getString("p.firstname"),rs.getString("p.lastname"),
                    new Role(rs.getInt("p.role_id"),rs.getString("r.role")),rs.getBoolean("p.suspended"));
        };

        return jt.query(query,rm);
    }
    public List<User> getAllProfileByOffset(int offset){
        String query = "Select p.id,c.username,r.role,p.firstname,p.lastname,p.role_id,p.suspended from profile p join credential c on c.id = p.credential_id join role r on p.role_id = r.id limit 5 offset ?";
        RowMapper<User> rm = (rs,row) ->{
            return new User(rs.getInt("p.id"),rs.getString("c.username"),rs.getString("p.firstname"),rs.getString("p.lastname"),
                    new Role(rs.getInt("p.role_id"),rs.getString("r.role")),rs.getBoolean("p.suspended"));
        };

        return jt.query(query,rm,offset);
    }

    public User getProfileByCredentialId(int id){
        String query = "Select p.id,c.username,r.role,p.firstname,p.lastname,p.role_id,p.suspended from profile p join credential c on c.id = p.credential_id join role r on p.role_id = r.id where c.id= ?";
        RowMapper<User> rm = (rs,row) ->{
            return new User(rs.getInt("p.id"),rs.getString("c.username"),rs.getString("p.firstname"),rs.getString("p.lastname"),
                    new Role(rs.getInt("p.role_id"),rs.getString("r.role")),rs.getBoolean("p.suspended"));
        };
        List<User> result = jt.query(query,rm,id);
        return result.get(0);
    }
    public User getProfileById(int id){
        String query = "Select p.id,c.username,r.role,p.firstname,p.lastname,p.role_id,p.suspended from profile p join credential c on c.id = p.credential_id join role r on p.role_id = r.id where p.id= ?";
        RowMapper<User> rm = (rs,row) ->{
            return new User(rs.getInt("p.id"),rs.getString("c.username"),rs.getString("p.firstname"),rs.getString("p.lastname"),
                    new Role(rs.getInt("p.role_id"),rs.getString("r.role")),rs.getBoolean("p.suspended"));
        };
        List<User> result = jt.query(query,rm,id);
        return result.get(0);
    }

    public void addNewProfile(String firstname,String lastname,int role_id,int credential_id){
        String query = "Insert into profile(firstname,lastname,role_id,credential_id) values (?,?,?,?)";
        jt.update(query,firstname,lastname,role_id,credential_id);
    }

    public void updateSuspendProfile(int profile_id,boolean suspended){
        String query = "Update profile set suspended = ? where id = ?";
        jt.update(query,suspended,profile_id);
    }
}
