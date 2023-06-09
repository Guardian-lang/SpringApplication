package by.itacademy.spring.service;

import by.itacademy.spring.database.repository.CompanyRepository;
import by.itacademy.spring.dto.CompanyReadDto;
import by.itacademy.spring.listener.entity.AccessType;
import by.itacademy.spring.listener.entity.EntityEvent;
import by.itacademy.spring.mapper.CompanyReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CompanyReadMapper companyReadMapper;

    public List<CompanyReadDto> findAll() {
        return companyRepository.findAll().stream()
                .map(companyReadMapper::map)
                .toList();
    }

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.DELETE));
                    return new CompanyReadDto(entity.getId(), null);
                });
    }
}
