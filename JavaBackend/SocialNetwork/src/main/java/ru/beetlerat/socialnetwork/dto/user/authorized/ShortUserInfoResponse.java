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

    public static ShortUserInfoResponse Empty() {
        return new ShortUserInfoResponse(null, "", "");
    }

    public static ShortUserInfoResponse NotAuthorized() {
        ShortUserInfoResponse shortUserInfoResponse = ShortUserInfoResponse.Empty();
        shortUserInfoResponse.setMessage(Message.NOT_AUTHORIZED.getMessage());
        shortUserInfoResponse.setResultCode(Code.AUTHORIZED.getCode());

        return shortUserInfoResponse;
    }

    public static ShortUserInfoResponse NotFound() {
        ShortUserInfoResponse shortUserInfoResponse = ShortUserInfoResponse.Empty();
        shortUserInfoResponse.setMessage(Message.NOT_FOUND.getMessage());
        shortUserInfoResponse.setResultCode(Code.NOT_AUTHORIZED.getCode());

        return shortUserInfoResponse;
    }

    public static ShortUserInfoResponse FromExceptionMessage(String exceptionMessage) {
        ShortUserInfoResponse shortUserInfoResponse = ShortUserInfoResponse.Empty();
        shortUserInfoResponse.setMessage(exceptionMessage);
        shortUserInfoResponse.setResultCode(Code.EXCEPTION.getCode());

        return shortUserInfoResponse;
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
