package ru.beetlerat.socialnetwork.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.beetlerat.socialnetwork.services.files.ImageService;
import ru.beetlerat.socialnetwork.utill.exceptions.files.FileNotFoundInDatabaseException;

@RestController
@RequestMapping("/img")
public class FileController {
    private final ImageService imageService;

    public FileController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable("filename") String filename) {
        byte[] image = imageService.getImageByName(filename);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @ExceptionHandler
    private ResponseEntity handleFileNotFoundInDatabaseException(
            FileNotFoundInDatabaseException exception) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}