package by.itacademy.spring.database.repository;

import by.itacademy.spring.database.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    // Optional Entity Future
    Optional<Company> findByName(String name);

    // List, Stream (batch)
    List<Company> findAllByNameContainingIgnoreCase(String fragment);

}
