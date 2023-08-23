package ru.beetlerat.socialnetwork.dto.profile;

import ru.beetlerat.socialnetwork.dto.ResponseToFront;

public class ProfileStatusResponse extends ResponseToFront {
    private String status;

    public ProfileStatusResponse() {

    }

    private ProfileStatusResponse(String status) {
        super("", Code.AUTHORIZED_AND_COMPLETED.getCode());
        this.status = status;
    }

    public static ProfileStatusResponse FromStatus(String status) {
        return new ProfileStatusResponse(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
