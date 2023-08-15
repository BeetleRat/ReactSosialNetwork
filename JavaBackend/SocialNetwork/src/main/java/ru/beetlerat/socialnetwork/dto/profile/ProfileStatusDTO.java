package ru.beetlerat.socialnetwork.dto.profile;

public class ProfileStatusDTO {
    private String status;

    public ProfileStatusDTO() {
        this.status = "";
    }

    public ProfileStatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
