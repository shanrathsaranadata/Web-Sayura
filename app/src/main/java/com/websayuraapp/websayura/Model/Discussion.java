package com.websayuraapp.websayura.Model;

public class Discussion {

    private String discussionimage,discussioncourse,discussiondateandtime,discussionmassage,discussionusername,discussiontype;

    public Discussion() {
    }

    public Discussion(String discussionimage, String discussioncourse, String discussiondateandtime, String discussionmassage, String discussionusername, String discussiontype) {
        this.discussionimage = discussionimage;
        this.discussioncourse = discussioncourse;
        this.discussiondateandtime = discussiondateandtime;
        this.discussionmassage = discussionmassage;
        this.discussionusername = discussionusername;
        this.discussiontype = discussiontype;
    }

    public String getDiscussionimage() {
        return discussionimage;
    }

    public void setDiscussionimage(String discussionimage) {
        this.discussionimage = discussionimage;
    }

    public String getDiscussioncourse() {
        return discussioncourse;
    }

    public void setDiscussioncourse(String discussioncourse) {
        this.discussioncourse = discussioncourse;
    }

    public String getDiscussiondateandtime() {
        return discussiondateandtime;
    }

    public void setDiscussiondateandtime(String discussiondateandtime) {
        this.discussiondateandtime = discussiondateandtime;
    }

    public String getDiscussionmassage() {
        return discussionmassage;
    }

    public void setDiscussionmassage(String discussionmassage) {
        this.discussionmassage = discussionmassage;
    }

    public String getDiscussionusername() {
        return discussionusername;
    }

    public void setDiscussionusername(String discussionusername) {
        this.discussionusername = discussionusername;
    }

    public String getDiscussiontype() {
        return discussiontype;
    }

    public void setDiscussiontype(String discussiontype) {
        this.discussiontype = discussiontype;
    }
}
