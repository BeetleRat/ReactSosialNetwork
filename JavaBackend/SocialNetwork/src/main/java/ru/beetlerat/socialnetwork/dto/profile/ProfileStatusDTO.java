package ru.beetlerat.socialnetwork.dto.profile;

import javax.validation.constraints.NotNull;

public class ProfileStatusDTO {
    @NotNull(message = "Status should not be empty")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileStatusDTO that = (ProfileStatusDTO) o;

        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        return status != null ? status.hashCode() : 0;
    }
}
