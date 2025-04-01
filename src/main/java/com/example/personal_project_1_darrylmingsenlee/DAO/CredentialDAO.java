package com.example.personal_project_1_darrylmingsenlee.DAO;

import com.example.personal_project_1_darrylmingsenlee.Domain.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CredentialDAO {

    JdbcTemplate jt;

    @Autowired
    public CredentialDAO(JdbcTemplate jt){
        this.jt = jt;
    }
    public Credential getCredential(String username){
        String query = "Select id,username,`password` from credential where username = ?";
        RowMapper<Credential> rm = (rs,row) -> new Credential(rs.getInt("id"),rs.getString("username"),rs.getString("password"));
        //jt.queryForObject(query,username,String.class);
        List<Credential> c = jt.query(query,rm,username);
        return c.isEmpty()?null:c.get(0);
    }
    public void addCredentials(String username,String password){
        String query = "Insert into credential (username,password) Value (?,?)";
        jt.update(query,username,password);
    }
}
