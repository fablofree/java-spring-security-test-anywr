package io.anywr.javaspringsecuritytest.helper;

import io.anywr.javaspringsecuritytest.service.UserInfoUserDetails;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserHelper {

    public static UserInfoUserDetails getUserDetails(){
        SecurityContext context = SecurityContextHolder.getContext();
        UserInfoUserDetails user = (UserInfoUserDetails) context.getAuthentication().getPrincipal();
        return user;
    }
}
