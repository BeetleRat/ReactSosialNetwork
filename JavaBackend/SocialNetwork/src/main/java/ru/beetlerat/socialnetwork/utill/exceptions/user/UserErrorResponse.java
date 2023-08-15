package ru.beetlerat.socialnetwork.utill.exceptions.user;


import java.util.Date;

public class UserErrorResponse{
    private int status;
    private String massage;
    private Date timestamp;

    public UserErrorResponse(int status, String massage) {
        this.status = status;
        this.massage = massage;
        this.timestamp = new Date();
    }

    public int getStatus() {
        return status;
    }

    public String getMassage() {
        return massage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
