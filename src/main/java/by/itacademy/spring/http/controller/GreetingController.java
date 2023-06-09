package by.itacademy.spring.http.controller;

import by.itacademy.spring.database.entity.Role;
import by.itacademy.spring.database.repository.CompanyRepository;
import by.itacademy.spring.dto.UserReadDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@SessionAttributes({"user"})
public class GreetingController {

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping("/hello")
    public String hello(Model model,
                        UserReadDto userReadDto) {
        model.addAttribute("user", userReadDto);
        return "greeting/hello";
    }

    @GetMapping("/bye")
    public String bye(@SessionAttribute("user") UserReadDto user) {
        return "greeting/bye";
    }

    @GetMapping("/hello/{id}")
    public ModelAndView hello2(ModelAndView mv,
                               @RequestParam Integer age,
                               @RequestHeader String accept,
                               @CookieValue("JSESSIONID") String jsessionId,
                               @PathVariable("id") Integer id) {
        mv.setViewName("greeting/hello");
        //mv.addObject("user", new UserReadDto(1L, "Andrei"));
        return mv;
    }
}
