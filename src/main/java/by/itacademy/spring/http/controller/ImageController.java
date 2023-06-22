package by.itacademy.spring.http.controller;

import by.itacademy.spring.database.entity.Image;
import by.itacademy.spring.service.ImageService;
import by.itacademy.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;

    @GetMapping("/{userId}/{imageNo}")
    public String findImage(@PathVariable("userId") Long userId, @PathVariable("imageNo") Integer imageNumber, Model model) {
        return userService.findById(userId)
                .map(user -> {
                    List<MultipartFile> images = imageService.findAllByUser(user);
                    model.addAttribute("image", images.get(imageNumber));
                    return "user/images";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{userId}/{imageNo}/prev")
    public String previousImage(@PathVariable("userId") Long userId, @PathVariable("imageNo") Integer imageNumber, Model model) {
        if (imageNumber != 0) {
            return findImage(userId, imageNumber - 1, model);
        }
        else return findImage(userId, imageNumber, model);
    }

    @GetMapping("/{userId}/{imageNo}/next")
    public String nextImage(@PathVariable("userId") Long userId, @PathVariable("imageNo") Integer imageNumber, Model model) {
        Integer imagesNumber = userService.findById(userId)
                .map(user -> {
                    List<MultipartFile> images = imageService.findAllByUser(user);
                    return images.size();
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!Objects.equals(imageNumber, imagesNumber)) {
            return findImage(userId, imageNumber + 1, model);
        }
        else return findImage(userId, imageNumber, model);
    }

    @PostMapping("/{userId}/{imageNo}/delete")
    public String delete(@PathVariable("userId") Long userId, @PathVariable("imageNo") Integer imageNumber, Model model) {
        List<Image> images = userService.findById(userId)
                .map(imageService::findAllImagesByUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if(!imageService.delete(images.get(imageNumber).getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/images";
    }
}
