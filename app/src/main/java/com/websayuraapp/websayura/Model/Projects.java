package com.websayuraapp.websayura.Model;

public class Projects {

    private String batchname,link;

    public Projects() {
    }

    public Projects(String batchname, String link) {
        this.batchname = batchname;
        this.link = link;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
