package ru.beetlerat.socialnetwork.services.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.beetlerat.socialnetwork.dao.UserDAO;
import ru.beetlerat.socialnetwork.models.ImageModel;
import ru.beetlerat.socialnetwork.models.UserModel;
import ru.beetlerat.socialnetwork.repositories.ImageRepository;
import ru.beetlerat.socialnetwork.utill.exceptions.files.CanNotReadFileException;
import ru.beetlerat.socialnetwork.utill.exceptions.files.FileNotFoundInDatabaseException;
import ru.beetlerat.socialnetwork.utill.exceptions.files.FileNotFoundInDiskException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImplementation implements ImageService {
    private final ImageRepository imageRepository;
    private final UserDAO userDAO;

    @Value("${application.files.imageExtensions}")
    private final List<String> imageExtensions = new ArrayList<>();
    @Value("${application.files.imagePath}")
    private String uploadPath;

    private ImageModel image;
    private File removeImage;

    @Autowired
    public FileServiceImplementation(
            ImageRepository imageRepository, UserDAO userDAO) {
        this.imageRepository = imageRepository;
        this.userDAO = userDAO;
    }

    @Override
    public byte[] getImageByName(String name) {
        String imagePath = getImagePathFromDatabase(name);

        return getBytesFromFile(imagePath);
    }

    private String getImagePathFromDatabase(String name) {
        image = imageRepository.findByName(name);
        if (image == null) {
            throw new FileNotFoundInDatabaseException();
        }

        return image.getImagePath();
    }

    private byte[] getBytesFromFile(String imagePath) {
        File imageFile = new File(imagePath);

        return getBytesFromFile(imageFile);
    }

    private byte[] getBytesFromFile(File imageFile) {
        if (!imageFile.exists()) {
            throw new FileNotFoundInDiskException();
        }

        try {
            return Files.readAllBytes(Path.of(imageFile.getPath()));
        } catch (IOException e) {
            throw new CanNotReadFileException();
        }
    }

    @Override
    public String savePhotoAndGetURL(UserModel user, MultipartFile photoFile) {
        if (!isInputDataCorrect(user, photoFile)) {
            return "";
        }

        image = getImageModelFromUser(user);

        fillImageFromFile(photoFile);

        if (!createFileByPathWasSuccessful(photoFile, image.getImagePath())) {
            return "";
        }

        String imgURL = createURLForImage();
        setURLToUserAndImage(imgURL, user);

        updateDatabase(user);

        return imgURL;
    }

    private boolean isInputDataCorrect(
            UserModel user, MultipartFile photoFile) {
        if (photoFile == null || photoFile.isEmpty() || user == null) {
            return false;
        }

        if (!isUploadPathCorrect()) {
            return false;
        }

        String fileExtension = getFileExtension(photoFile);

        return imageExtensions.contains(fileExtension);
    }

    private boolean isUploadPathCorrect() {
        File uploadDirectory = new File(uploadPath);

        if (!uploadDirectory.isDirectory()) {
            return false;
        }

        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdir();
        }

        return true;
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

    private ImageModel getImageModelFromUser(UserModel user) {
        ImageModel photo = user.getProfilePhoto();
        if (photo == null) {
            photo = new ImageModel();
            photo.setUser(user);
        } else {
            removeImage = new File(photo.getImagePath());
        }

        return photo;
    }

    private void fillImageFromFile(MultipartFile photoFile) {
        String fileName = UUID.randomUUID() + photoFile.getOriginalFilename();
        image.setImagePath(uploadPath + fileName);
        image.setName(fileName);
    }

    private boolean createFileByPathWasSuccessful(
            MultipartFile photoFile, String path) {
        try {
            photoFile.transferTo(new File(path));
        } catch (IOException e) {
            System.out.println("Ошибка сохранения файла: " + e);
            return false;
        }

        return true;
    }

    private String createURLForImage() {
        String serverBaseURL =
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .toUriString();

        return serverBaseURL + "/img/" + image.getName();
    }

    private void setURLToUserAndImage(String imgURL, UserModel user) {
        user.setImgURL(imgURL);
        image.setUrl(imgURL);
    }

    private void updateDatabase(UserModel user) {
        imageRepository.save(image);
        userDAO.update(user.getUserID(), user);
        if (removeImage.exists()) {
            removeImage.delete();
        }
    }
}
