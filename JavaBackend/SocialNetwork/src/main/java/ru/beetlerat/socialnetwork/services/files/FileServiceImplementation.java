package ru.beetlerat.socialnetwork.services.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.beetlerat.socialnetwork.dao.UserDAO;
import ru.beetlerat.socialnetwork.models.ImageModel;
import ru.beetlerat.socialnetwork.models.User;
import ru.beetlerat.socialnetwork.repositories.ImageRepository;
import ru.beetlerat.socialnetwork.utill.exceptions.files.FileNotFoundInDatabaseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImplementation implements ImageService {
    private final ImageRepository imageRepository;
    private final UserDAO userDAO;
    @Value("${application.files.imageExtensions}")
    private final List<String> imageExtensions = new ArrayList<>();

    private ImageModel image;

    @Autowired
    public FileServiceImplementation(ImageRepository imageRepository, UserDAO userDAO) {
        this.imageRepository = imageRepository;
        this.userDAO = userDAO;
    }

    @Override
    public byte[] getImageByName(String name) {
        image = imageRepository.findByOriginalFileName(name);
        if (image == null) {
            throw new FileNotFoundInDatabaseException();
        }

        return image.getBytes();
    }

    @Override
    public String savePhotoAndGetURL(User user, MultipartFile photoFile) {
        if (!isInputDataCorrect(user, photoFile)) {
            return "";
        }

        image = getImageModelFromUser(user);

        if (tryToFillImageFromFile(photoFile) == false) {
            return "";
        }
        String imgURL = createURLForImage();
        setURLToUserAndImage(imgURL, user);

        updateDatabase(user);

        return imgURL;
    }

    private boolean isInputDataCorrect(User user, MultipartFile photoFile) {
        if (photoFile == null || photoFile.isEmpty() || user == null) {
            return false;
        }

        String fileExtension = getFileExtension(photoFile);

        return imageExtensions.contains(fileExtension);
    }

    private String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileExtension = "";
        try {
            fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println(e);
        }

        return fileExtension;
    }

    private ImageModel getImageModelFromUser(User user) {
        ImageModel photo = user.getProfilePhoto();
        if (photo == null) {
            photo = new ImageModel();
            photo.setUser(user);
        }

        return photo;
    }

    private boolean tryToFillImageFromFile(MultipartFile photoFile) {
        try {
            image.setBytes(photoFile.getBytes());
        } catch (IOException e) {
            System.out.println(e);

            return false;
        }

        image.setContentType(photoFile.getContentType());
        image.setName(photoFile.getName());
        image.setOriginalFileName(UUID.randomUUID() + photoFile.getOriginalFilename());
        image.setSize(photoFile.getSize());

        return true;
    }

    private String createURLForImage() {
        String serverBaseURL = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        return serverBaseURL + "/img/" + image.getOriginalFileName();
    }

    private void setURLToUserAndImage(String imgURL, User user) {
        user.setImgURL(imgURL);
        image.setUrl(imgURL);
    }

    private void updateDatabase(User user) {
        imageRepository.save(image);
        userDAO.update(user.getUserID(), user);
    }
}
