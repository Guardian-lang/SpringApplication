package by.itacademy.spring.dto;

import by.itacademy.spring.database.entity.Role;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class UserReadDto {
    Long id;
    String username;
    LocalDate birthDate;
    String firstname;
    String lastname;
    String avatar;
    Role role;
    CompanyReadDto company;
    List<String> images;
}
