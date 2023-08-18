package ru.beetlerat.socialnetwork.dto.user.authorized;

public class ShortUserInfoResponse extends JWTokenDTOResponse {
    private ShortUserInfo userInfo;

    public ShortUserInfoResponse() {
        this.userInfo = new ShortUserInfo();
    }

    private ShortUserInfoResponse(ShortUserInfo userInfo, String accessToken, String refreshToken) {
        super(accessToken, refreshToken);
        this.userInfo = userInfo;
    }

    private ShortUserInfoResponse(int userId, String email, String username, String accessToken, String refreshToken) {
        super(accessToken, refreshToken);
        this.userInfo = ShortUserInfo.FromIDEmailUsername(userId, email, username);
    }

    public static ShortUserInfoResponse FromUserInfoAccessAndRefreshTokens(ShortUserInfo userInfo, String accessToken, String refreshToken) {
        return new ShortUserInfoResponse(userInfo, accessToken, refreshToken);
    }

    public static ShortUserInfoResponse FromIDEmailUsernameAccessAndRefreshTokens(int userId, String email, String username, String accessToken, String refreshToken) {
        return new ShortUserInfoResponse(userId, email, username, accessToken, refreshToken);
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
