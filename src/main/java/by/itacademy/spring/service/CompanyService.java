package by.itacademy.spring.service;

import by.itacademy.spring.database.repository.CompanyRepository;
import by.itacademy.spring.dto.CompanyReadDto;
import by.itacademy.spring.listener.entity.AccessType;
import by.itacademy.spring.listener.entity.EntityEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CompanyService(CompanyRepository companyRepository, ApplicationEventPublisher eventPublisher) {
        this.companyRepository = companyRepository;
        this.eventPublisher = eventPublisher;
    }

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.DELETE));
                    return new CompanyReadDto(entity.getId());
                });
    }
}
