package by.itacademy.spring.service;

import by.itacademy.spring.database.entity.Image;
import by.itacademy.spring.database.repository.ImageRepository;
import by.itacademy.spring.dto.UserReadDto;
import by.itacademy.spring.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
//    public void setBucket(@Value("${app.image.bucket}")
//                                  String bucket) {
//        this.bucket = bucket;
//    }

    @SneakyThrows
    public int upload(String imagePath, InputStream content) {
        Path fullImagePath = Path.of(bucket, imagePath);
        byte out = 0;

        try (content) {
            var bites = content.readAllBytes();
            for (byte bite : bites) {
                out += bite;
            }
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
        return out;
    }


    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);

        return Files.exists(fullImagePath)
                ? Optional.of(Files.readAllBytes(fullImagePath))
                : Optional.empty();
    }

    public List<MultipartFile> findAllByUser(UserReadDto user) {
        List<Image> images = imageRepository.findAllByUser(userReadMapper.mapToUser(user));
        List<MultipartFile> out = new ArrayList<>();
        for (Image image : images) {
            out.add(image.getImg());
        }
        return out;
    }

    public List<Image> findAllImagesByUser(UserReadDto user) {
        return imageRepository.findAllByUser(userReadMapper.mapToUser(user));
    }

    @Transactional
    public boolean delete(Long id) {
        return imageRepository.findById(id)
                .map(entity -> {
                    imageRepository.delete(entity);
                    imageRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
