package by.itacademy.spring.database.repository;

import by.itacademy.spring.database.entity.Role;
import by.itacademy.spring.database.entity.User;
import by.itacademy.spring.database.pool.ConnectionPool;
import by.itacademy.spring.dto.IPersonalInfo;
import by.itacademy.spring.dto.PersonalInfo;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    List<PersonalInfo> findAllByCompanyId(Integer companyId);
//    <T> List<T> findAllByCompanyId(Integer companyId, Class<T> clazz);

    @Query(value = "SELECT " +
            "firstname, " +
            "lastname, " +
            "birth_date birthDate " +
            "FROM users " +
            "WHERE company_id = :companyId",
            nativeQuery = true)
    List<IPersonalInfo> findAllByCompanyId(Integer companyId);


    //List<User> findFirst4By(Sort sort);

    Page<User> findFirst4By(Pageable pageable);

    @Query(value = "SELECT u.* FROM users u " +
            " WHERE u.firstname = :username",
            nativeQuery = true)
    List<User> findAllByUsername(String username);

    @Query("select u from User u " +
            "where u.firstname like %:firstname% and u.lastname like %:lastname% ")
    List<User> findAllBy(String firstname, String lastname);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.role = :role " +
            "where u.id in (:ids)")
    int updateRole(Role role, Long... ids);

    Optional<User> findFirstByOrderByIdDesc();

    List<User> findFirst3ByOrderByIdDesc();

    List<User> findFirst3By(Sort sort);

    Page<User> findAllBy(Pageable pageable);

//    int updateRole(Role role, Sort sort, Pageable pageable, Long... ids);
}
