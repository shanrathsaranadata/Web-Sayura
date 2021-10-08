package com.websayuraapp.websayura.Model;

public class Joinclass {

    private String classbatch,classlink,classmassage,classtitle,classtype,date,lecturename,time;
    public Joinclass() {
    }

    public Joinclass(String classbatch, String classlink, String classmassage, String classtitle, String classtype, String date, String lecturename, String time) {
        this.classbatch = classbatch;
        this.classlink = classlink;
        this.classmassage = classmassage;
        this.classtitle = classtitle;
        this.classtype = classtype;
        this.date = date;
        this.lecturename = lecturename;
        this.time = time;
    }

    public String getClassbatch() {
        return classbatch;
    }

    public void setClassbatch(String classbatch) {
        this.classbatch = classbatch;
    }

    public String getClasslink() {
        return classlink;
    }

    public void setClasslink(String classlink) {
        this.classlink = classlink;
    }

    public String getClassmassage() {
        return classmassage;
    }

    public void setClassmassage(String classmassage) {
        this.classmassage = classmassage;
    }

    public String getClasstitle() {
        return classtitle;
    }

    public void setClasstitle(String classtitle) {
        this.classtitle = classtitle;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLecturename() {
        return lecturename;
    }

    public void setLecturename(String lecturename) {
        this.lecturename = lecturename;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
