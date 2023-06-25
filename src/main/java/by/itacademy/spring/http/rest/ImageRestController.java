package by.itacademy.spring.http.rest;

import by.itacademy.spring.database.entity.Image;
import by.itacademy.spring.service.ImageService;
import by.itacademy.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageRestController {

    private final ImageService imageService;
    private final UserService userService;

    @GetMapping(value = "/{userId}/{imageNo}", params = {"userId", "imageNo"})
    public Optional<MultipartFile> findImage(@PathVariable("userId") Long userId,
            @PathVariable("imageNo") Integer imageNumber) {
        return userService.findById(userId)
                .map(user -> imageService.findAllByUser(user).get(imageNumber));
    }

    @GetMapping(value = "/{userId}/{imageNo}/prev", params = {"userId", "imageNo"})
    public Optional<MultipartFile> previousImage(@PathVariable("userId") Long userId,
                                @PathVariable("imageNo") Integer imageNumber) {
        if (imageNumber != 0) {
            return findImage(userId, imageNumber - 1);
        }
        else return findImage(userId, imageNumber);
    }

    @GetMapping(value = "/{userId}/{imageNo}/next", params = {"userId", "imageNo"})
    public Optional<MultipartFile> nextImage(@PathVariable("userId") Long userId,
                            @PathVariable("imageNo") Integer imageNumber) {
        Integer imagesNumber = userService.findById(userId)
                .map(user -> {
                    List<MultipartFile> images = imageService.findAllByUser(user);
                    return images.size();
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!Objects.equals(imageNumber, imagesNumber)) {
            return findImage(userId, imageNumber + 1);
        }
        else return findImage(userId, imageNumber);
    }

    @DeleteMapping(value = "/{userId}/{imageNo}/delete", params = {"userId", "imageNo"})
    public ResponseEntity<?> delete(@PathVariable("userId") Long userId,
                                    @PathVariable("imageNo") Integer imageNumber) {
        List<Image> images = userService.findById(userId)
                .map(imageService::findAllImagesByUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return imageService.delete(images.get(imageNumber).getId())
                ? noContent().build()
                : notFound().build();
    }
}
