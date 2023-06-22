package by.itacademy.spring.dto;

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
public class ImageGetDto {
    Long id;
    Long userId;
    MultipartFile img;
}
