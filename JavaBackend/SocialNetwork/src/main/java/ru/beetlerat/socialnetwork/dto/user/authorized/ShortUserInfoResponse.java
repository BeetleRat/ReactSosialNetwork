package ru.beetlerat.socialnetwork.dto.user.authorized;

public class ShortUserInfoResponse extends ResponseToFront {
    private ShortUserInfo userInfo;

    public ShortUserInfoResponse() {
        this.userInfo = new ShortUserInfo();
    }

    private ShortUserInfoResponse(ShortUserInfo userInfo, String message, int resultCode) {
        super(message, resultCode);
        this.userInfo = userInfo;
    }

    private ShortUserInfoResponse(int userId, String email, String username, String message, int resultCode) {
        super(message, resultCode);
        this.userInfo = ShortUserInfo.FromIDEmailUsername(userId, email, username);
    }

    public static ShortUserInfoResponse FromUserInfoMessageAndCode(ShortUserInfo userInfo, String message, int resultCode) {
        return new ShortUserInfoResponse(userInfo, message, resultCode);
    }

    public static ShortUserInfoResponse FromAuthorizedUser(ShortUserInfo userInfo) {
        return ShortUserInfoResponse.FromUserInfoMessageAndCode(userInfo, "", Code.AUTHORIZED.getCode());
    }

    public ShortUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(ShortUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setUserID(int userID) {
        this.userInfo.setUserID(userID);
    }

    public void setEmail(String email) {
        this.userInfo.setEmail(email);
    }

    public void setUsername(String username) {
        this.userInfo.setUsername(username);
    }
}
