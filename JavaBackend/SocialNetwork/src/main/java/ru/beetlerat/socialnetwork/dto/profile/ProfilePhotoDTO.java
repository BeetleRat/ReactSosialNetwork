package ru.beetlerat.socialnetwork.dto.profile;

import org.springframework.web.multipart.MultipartFile;

public class ProfilePhotoDTO {
    private MultipartFile image;

    public ProfilePhotoDTO() {

    }

    public ProfilePhotoDTO(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
