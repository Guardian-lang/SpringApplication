package by.itacademy.integration.database.repository;

import by.itacademy.annotation.IT;
import by.itacademy.spring.database.entity.Role;
import by.itacademy.spring.database.repository.UserRepository;
import by.itacademy.spring.dto.PersonalInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@IT
@RequiredArgsConstructor
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void checkProjections() {
        var users = userRepository.findAllByCompanyId(1);
        assertThat(users).hasSize(2);
    }

    @Test
    void findFirst4ByTest() {
        var pageable = PageRequest.of(0, 4, Sort.by("birthDate"));
        var pageable2 = PageRequest.of(0, 4, Sort.by("birthDate")
                .and(Sort.by("firstname")).and(Sort.by("lastname")));
        var users = userRepository.findFirst4By(pageable);
        var users2 = userRepository.findFirst4By(pageable2);
        assertThat(users).hasSize(4);
        assertThat(users2).hasSize(4);
        assertTrue(users.get().findFirst().get().getFirstname().equals("Vlad"));
        assertTrue(users2.get().findFirst().get().getFirstname().equals("Kate"));
    }


//    @Test
//    void findFirst4ByBirth() {
//        var users = userRepository.findFirst4By(
//                Sort.by("birthDate"));
//        assertFalse(users.isEmpty());
//        assertThat(users).hasSize(4);
//    }

//    @Test
//    void findFirst4ByBirthAndFio() {
//        var users = userRepository.findFirst4By(
//                Sort.by("birthDate")
//                        .and(Sort.by("firstname")
//                                .and(Sort.by("lastname"))));
//        assertFalse(users.isEmpty());
//        assertThat(users).hasSize(4);
//    }

    @Test
    void checkPageable() {
        var pageable = PageRequest.of(0, 2, Sort.by("id"));
        var slice = userRepository.findAllBy(pageable);
        slice.forEach(u -> System.out.println(u.getId()));
        while (slice.hasNext()) {
            slice = userRepository.findAllBy(slice.nextPageable());
            slice.forEach(u -> System.out.println(u.getId()));
        }
    }


    @Test
    void find3FirstTest() {
        var users = userRepository.findFirst3By(
                Sort.by("firstname").and(Sort.by("lastname")).descending());
        assertFalse(users.isEmpty());
        assertThat(users).hasSize(3);
    }

    @Test
    void testFind3FirstByOrderByIdDesc() {
        var users = userRepository.findFirst3ByOrderByIdDesc();
        assertFalse(users.isEmpty());
        assertThat(users).hasSize(3);
    }

    @Test
    void findFirstByOrderByIdDescTest() {
        var user = userRepository.findFirstByOrderByIdDesc();
        assertTrue(user.isPresent());
        user.ifPresent(u -> assertEquals("Kate", u.getFirstname()));
    }

    @Test
    void updateRoleTest() {
        var admin = userRepository.findById(1L);
        assertEquals(Role.ADMIN, admin.get().getRole());
        admin.get().getCompany().getName();
        var result = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, result);

        var sameAdmin = userRepository.findById(1L);
        assertEquals(Role.USER, sameAdmin.get().getRole());
    }

    @Test
    void findAllByFirstnameContainingAndLastnameContainingTest() {
        var result = userRepository.findAllBy("a", "a");
        assertTrue(!result.isEmpty());
        assertThat(result).hasSize(3);
    }
}
