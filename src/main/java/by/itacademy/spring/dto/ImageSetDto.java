package by.itacademy.spring.dto;

import by.itacademy.spring.database.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.stereotype.Component;

@Component
@Value
@Builder
@Getter
@Setter
public class ImageSetDto {
    User user;
    byte[] img;
}
