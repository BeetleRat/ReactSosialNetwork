package ru.beetlerat.socialnetwork.services.files;

import org.springframework.web.multipart.MultipartFile;
import ru.beetlerat.socialnetwork.models.UserModel;

public interface ImageService {
    byte[] getImageByName(String name);
    String savePhotoAndGetURL(UserModel user, MultipartFile imageFile);
}
