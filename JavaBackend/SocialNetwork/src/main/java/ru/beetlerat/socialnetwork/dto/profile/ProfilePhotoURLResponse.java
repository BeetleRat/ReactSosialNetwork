package ru.beetlerat.socialnetwork.dto.profile;

import ru.beetlerat.socialnetwork.dto.ResponseToFront;

public class ProfilePhotoURLResponse extends ResponseToFront {
    private String imgURL;

    public ProfilePhotoURLResponse() {

    }

    private ProfilePhotoURLResponse(String imgURL) {
        super("", Code.AUTHORIZED_AND_COMPLETED.getCode());
        this.imgURL = imgURL;
    }

    public static ProfilePhotoURLResponse FromImgURL(String imgURL) {
        return new ProfilePhotoURLResponse(imgURL);
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
