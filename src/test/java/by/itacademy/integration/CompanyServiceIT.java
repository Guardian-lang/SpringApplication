package by.itacademy.integration;

import by.itacademy.annotation.IT;
import by.itacademy.spring.database.properties.DatabaseProperties;
import by.itacademy.spring.dto.CompanyReadDto;
import by.itacademy.spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CompanyServiceIT {
    private static final Integer COMPANY_ID = 1;

    private final CompanyService companyService;
    private final DatabaseProperties databaseProperties;

    @Test
    void findById() {
        var actualResult = companyService.findById(COMPANY_ID);
        assertTrue(actualResult.isPresent());

        var expectedResult = new CompanyReadDto(COMPANY_ID, null);

        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }
}
