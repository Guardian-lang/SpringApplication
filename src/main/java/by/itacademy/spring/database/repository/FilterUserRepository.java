package by.itacademy.spring.database.repository;

import by.itacademy.spring.database.entity.User;
import by.itacademy.spring.dto.UserFilter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilterUserRepository {
    List<User> findAllByFilter(UserFilter filter);
}
