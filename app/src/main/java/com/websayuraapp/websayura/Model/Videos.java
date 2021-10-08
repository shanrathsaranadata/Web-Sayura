package com.websayuraapp.websayura.Model;

public class Videos {

    private String subject,videolink,mainvideo;

    public Videos() {
    }

    public Videos(String subject, String videolink, String mainvideo) {

        this.subject = subject;
        this.videolink = videolink;
        this.mainvideo = mainvideo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public String getMainvideo() {
        return mainvideo;
    }

    public void setMainvideo(String mainvideo) {
        this.mainvideo = mainvideo;
    }
}
