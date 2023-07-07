package by.itacademy.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ServiceAspect {

    @Pointcut("within(by.itacademy.spring.service.UserService)")
    public void isUserService() {
    }

    @Pointcut("execution(public * create(*))")
    public void isCreateMethod() {
    }

    @Before(value = "isUserService() && isCreateMethod() && args(userDto) && target(userService)", argNames = "userDto, userService")
    public void addLoggingBeforeCreateMethod(Object userDto, Object userService) {
        log.info("Before create user in class {}, with dto {}", userService, userDto);
    }


}
