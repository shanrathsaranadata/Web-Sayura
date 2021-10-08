package com.websayuraapp.websayura.Model;

public class Feedback {

    private String comment,description,education,name,profile;

    public Feedback() {
    }

    public Feedback(String comment, String description, String education, String name, String profile) {
        this.comment = comment;
        this.description = description;
        this.education = education;
        this.name = name;
        this.profile = profile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
