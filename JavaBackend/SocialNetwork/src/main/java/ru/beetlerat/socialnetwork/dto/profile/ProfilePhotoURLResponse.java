package ru.beetlerat.socialnetwork.dto.profile;

import ru.beetlerat.socialnetwork.dto.ResponseToFront;

public class ProfilePhotoURLResponse extends ResponseToFront {
    private String imgURL;

    public ProfilePhotoURLResponse() {

    }

    public ProfilePhotoURLResponse(String imgURL) {
        super("", Code.AUTHORIZED_AND_COMPLETED.getCode());
        this.imgURL = imgURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
