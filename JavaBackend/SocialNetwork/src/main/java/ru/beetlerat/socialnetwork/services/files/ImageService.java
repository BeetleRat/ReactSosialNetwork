package ru.beetlerat.socialnetwork.services.files;

import org.springframework.web.multipart.MultipartFile;
import ru.beetlerat.socialnetwork.models.User;

public interface ImageService {
    byte[] getImageByName(String name);
    String savePhotoAndGetURL(User user, MultipartFile imageFile);
}
