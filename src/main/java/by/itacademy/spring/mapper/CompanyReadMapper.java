package by.itacademy.spring.mapper;

import by.itacademy.spring.database.entity.Company;
import by.itacademy.spring.dto.CompanyReadDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto>{

    @Override
    public CompanyReadDto map(Company object) {
        return new CompanyReadDto(
                object.getId(),
                object.getName()
        );
    }

    public Company mapToCompany(CompanyReadDto object) {
        return Company.builder()
                .id(object.id())
                .name(object.name())
                .build();
    }
}
