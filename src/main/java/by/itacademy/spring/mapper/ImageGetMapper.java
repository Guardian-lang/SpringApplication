package by.itacademy.spring.mapper;

import by.itacademy.spring.database.entity.Image;
import by.itacademy.spring.dto.ImageGetDto;
import by.itacademy.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageGetMapper implements Mapper<Image, ImageGetDto> {

    private final UserService userService;


    @Override
    public ImageGetDto map(Image object) {
        return ImageGetDto.builder()
                .id(object.getId())
                .userId(object.getUser().getId())
                .img(object.getImg())
                .build();
    }
}
