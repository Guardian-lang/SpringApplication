package by.itacademy.unit;

import by.itacademy.spring.database.entity.Company;
import by.itacademy.spring.database.repository.CompanyRepository;
import by.itacademy.spring.dto.CompanyReadDto;
import by.itacademy.spring.listener.entity.EntityEvent;
import by.itacademy.spring.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    private static final Integer COMPANY_ID = 1;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void findBuId() {
        Mockito.doReturn(Optional.of(new Company(COMPANY_ID, null, Collections.emptyMap()))).when(companyRepository).findById(COMPANY_ID);

        var actualResult = companyService.findById(COMPANY_ID);
        assertTrue(actualResult.isPresent());

        var expectedResult = new CompanyReadDto(COMPANY_ID, null);

        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));

        verify(eventPublisher).publishEvent(any(EntityEvent.class));
    }
}
