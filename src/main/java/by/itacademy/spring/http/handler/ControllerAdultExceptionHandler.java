package by.itacademy.spring.http.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "by.itacademy.spring.http.controller")
public class ControllerAdultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, HttpServletRequest request) {
        log.error("Failed to return response", exception);

        return "user/not-adult";
    }
}
