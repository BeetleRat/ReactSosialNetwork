package ru.beetlerat.socialnetwork.dto.message;

public class MessageResponse {

    private Integer userID;
    private String userName;
    private String imgURL;
    private String message;

    public MessageResponse() {

    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getMessage() {
        return message;
    }
}
