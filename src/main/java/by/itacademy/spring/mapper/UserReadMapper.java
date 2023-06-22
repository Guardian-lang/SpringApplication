package by.itacademy.spring.mapper;

import by.itacademy.spring.database.entity.User;
import by.itacademy.spring.dto.CompanyReadDto;
import by.itacademy.spring.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto map(User object) {
        CompanyReadDto company = Optional.ofNullable(object.getCompany())
                .map(companyReadMapper::map)
                .orElse(null);
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getBirthDate(),
                object.getFirstname(),
                object.getLastname(),
                object.getAvatar(),
                object.getRole(),
                company
        );
    }

    public User mapToUser(UserReadDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private void copy(UserReadDto object, User user) {
        user.setId(object.getId());
        user.setUsername(object.getUsername());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setCompany(companyReadMapper.mapToCompany(object.getCompany()));

        Optional.ofNullable(object.getImage())
                .ifPresent(user::setAvatar);
    }
}