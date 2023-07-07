package by.itacademy.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
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

    @Pointcut("within(by.itacademy.spring.service.ImageService)")
    public void isImageService() {
    }

    @Pointcut("execution(public * create(*))")
    public void isCreateMethod() {
    }

    @Pointcut("execution(public void upload(*))")
    public void isUploadMethod() {
    }

    @Before(value = "isUserService() " +
            "&& isCreateMethod() " +
            "&& args(userDto) " +
            "&& target(userService)",
            argNames = "userDto, userService")
    public void addLoggingBeforeCreateMethod(Object userDto, Object userService) {
        log.info("Before create user in class {}, with dto {}", userService, userDto);
    }

    @AfterReturning(value = "isUserService() " +
            "&& isCreateMethod() " +
            "&& target(userService)" +
            "&& this(userServiceProxy)",
            returning = "userDto",
            argNames = "userService, userServiceProxy, userDto")
    public void addLoggingAfterReturningCreateMethod(Object userService, Object userServiceProxy, Object userDto) {
        log.info("AfterReturning create user in class {}, result is {}", userService, userDto);
    }

    @AfterReturning(value = "isImageService()" +
            "&& isUploadMethod()" +
            "&& target(imageService)" +
            "&& this(imageServiceProxy)",
    returning = "imageSize",
    argNames = "imageService, imageServiceProxy, imageSize")
    public void addLoggingAfterReturningUploadMethod(Object imageService,
                                                     Object imageServiceProxy,
                                                     Object imageSize) {
        log.info("Image uploaded with size {}, in class {}", imageSize, imageService);
        int i = (int) imageSize;
        if (i > 1048576) {
            System.out.println("Image with size more than 1MB is forbidden to upload");
        }
    }
}
