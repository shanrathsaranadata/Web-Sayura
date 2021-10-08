package com.websayuraapp.websayura.Model;

public class Notification {

    private String dateandtime,image,name,text,title;

    public Notification() {
    }

    public Notification(String dateandtime, String image, String name, String text, String title) {
        this.dateandtime = dateandtime;
        this.image = image;
        this.name = name;
        this.text = text;
        this.title = title;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
