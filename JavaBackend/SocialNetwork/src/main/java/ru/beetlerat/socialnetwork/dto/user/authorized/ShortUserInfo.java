package ru.beetlerat.socialnetwork.dto.user.authorized;

import ru.beetlerat.socialnetwork.models.UserModel;

public class ShortUserInfo {
    private int userID;
    private String email;
    private String username;
    private String imgURL;

    public ShortUserInfo() {

    }

    private ShortUserInfo(int userID, String email, String username) {
        this.userID = userID;
        this.email = email;
        this.username = username;
    }

    public static ShortUserInfo FromIDEmailUsername(int userID, String email, String username) {
        return new ShortUserInfo(userID, email, username);
    }

    public static ShortUserInfo FromUser(UserModel user) {
        ShortUserInfo newUser = new ShortUserInfo(user.getUserID(), user.getEmail(), user.getSecuritySettings().getUsername());
        newUser.setImgURL(user.getImgURL());

        return newUser;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
