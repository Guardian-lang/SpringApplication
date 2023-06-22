package by.itacademy.spring.http.controller;

import by.itacademy.spring.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
}
