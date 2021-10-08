package com.websayuraapp.websayura.Model;

public class Users {

    private String courses,email,name,password,username;

    public Users() {

    }

    public Users(String courses, String email, String name, String password, String username) {
        this.courses = courses;
        this.email = email;
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
