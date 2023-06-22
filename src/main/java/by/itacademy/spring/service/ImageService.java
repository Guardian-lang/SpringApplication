package by.itacademy.spring.service;

import by.itacademy.spring.database.entity.Image;
import by.itacademy.spring.database.repository.ImageRepository;
import by.itacademy.spring.database.repository.UserRepository;
import by.itacademy.spring.dto.UserReadDto;
import by.itacademy.spring.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.bucket}")
    private String bucket;

    private final ImageRepository imageRepository;
    private final UserReadMapper userReadMapper;

//    @Autowired
//    public void setBucket(@Value("${app.image.backet}")
//                                  String bucket) {
//        this.bucket = bucket;
//    }

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        Path fullImagePath = Path.of(bucket, imagePath);

        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }

    }


    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);

        return Files.exists(fullImagePath)
                ? Optional.of(Files.readAllBytes(fullImagePath))
                : Optional.empty();
    }

    public List<byte[]> findAllByUser(UserReadDto user) {
        List<Image> images = imageRepository.findAllByUser(userReadMapper.mapToUser(user));
        List<byte[]> out = new ArrayList<>();
        for (Image image : images) {
            out.add(image.getImg());
        }
        return out;
    }
}
