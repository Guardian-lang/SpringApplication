package by.itacademy.spring.mapper;

import by.itacademy.spring.database.entity.Image;
import by.itacademy.spring.dto.ImageSetDto;
import org.springframework.stereotype.Component;

@Component
public class ImageSetMapper implements Mapper<ImageSetDto, Image>{

    @Override
    public Image map(ImageSetDto object) {
        Image image = new Image();
        copy(object, image);
        return image;
    }

    @Override
    public Image map(ImageSetDto fromObject, Image toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ImageSetDto object, Image image) {
        image.setUser(object.getUser());
        image.setImg(object.getImg());
    }
}
