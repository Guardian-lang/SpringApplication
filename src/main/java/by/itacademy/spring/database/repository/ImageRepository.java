package by.itacademy.spring.database.repository;

import by.itacademy.spring.database.entity.Image;
import by.itacademy.spring.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select img from Image img " +
            "where img.user = :user")
    List<Image> findAllByUser(@Param("user") User user);
}
