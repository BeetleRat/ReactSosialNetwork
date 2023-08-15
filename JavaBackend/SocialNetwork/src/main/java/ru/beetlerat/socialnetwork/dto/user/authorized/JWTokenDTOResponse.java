package ru.beetlerat.socialnetwork.dto.user.authorized;

public class JWTokenDTOResponse extends ResponseToFront {
    private String refreshToken;
    private String accessToken;


    public JWTokenDTOResponse() {
    }

    protected JWTokenDTOResponse(String accessToken, String refreshToken) {
        super("", Code.NEW_TOKEN_RECEIVED.getCode());
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static JWTokenDTOResponse FromAccessAndRefreshToken(String accessToken, String refreshToken) {
        return new JWTokenDTOResponse(accessToken,refreshToken);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
