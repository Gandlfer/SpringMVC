package com.example.personal_project_1_darrylmingsenlee.Domain;

public class User {

    int id;
    String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;
    String firstname;
    String lastname;
    Role role;
    boolean is_suspended;
    public User(int id, String username, String firstname, String lastname, Role role, boolean is_suspended) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.is_suspended = is_suspended;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isIs_suspended() {
        return is_suspended;
    }

    public void setIs_suspended(boolean is_suspended) {
        this.is_suspended = is_suspended;
    }
}
