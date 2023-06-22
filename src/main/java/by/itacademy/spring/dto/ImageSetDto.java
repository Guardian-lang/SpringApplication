package by.itacademy.spring.dto;

import by.itacademy.spring.database.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Value
@Builder
@Getter
@Setter
public class ImageSetDto {
    User user;
    MultipartFile img;
}
