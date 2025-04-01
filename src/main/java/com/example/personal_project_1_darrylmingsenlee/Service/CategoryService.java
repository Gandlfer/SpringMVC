package com.example.personal_project_1_darrylmingsenlee.Service;

import com.example.personal_project_1_darrylmingsenlee.DAO.CategoryDAO;
import com.example.personal_project_1_darrylmingsenlee.Domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    CategoryDAO categoryDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }
    public List<Category> getAllCategory(){
        return categoryDAO.getAllCategory();
    }
    public Category getCategory(int id){
        return categoryDAO.getCategory(id);
    }
}
