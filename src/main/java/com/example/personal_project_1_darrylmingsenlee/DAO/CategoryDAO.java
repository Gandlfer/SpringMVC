package com.example.personal_project_1_darrylmingsenlee.DAO;

import com.example.personal_project_1_darrylmingsenlee.Domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAO {

    JdbcTemplate jt;

    @Autowired
    public CategoryDAO(JdbcTemplate jt){
        this.jt=jt;
    }
    public List<Category> getAllCategory(){
        String query = "Select id,category from category";
        RowMapper<Category> rm = (rs,rowNum)->{
            return new Category(rs.getInt("id"),rs.getString("category"));
        };
        return jt.query(query,rm);
    }
    public Category getCategory(int id){
        String query = "Select id,category from category where id = ?";
        RowMapper<Category> rm = (rs,rowNum)->{
            return new Category(rs.getInt("id"),rs.getString("category"));
        };
        List<Category> list = jt.query(query,rm,id);
        return (list.isEmpty())?null:list.get(0);
    }
    public void addCategory(String category){
        String query = "Insert into category(category) values (?)";
        jt.update(query,category);
    }
}
