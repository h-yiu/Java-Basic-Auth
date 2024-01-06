package io.hyao.custombasicauth.component;

import io.hyao.custombasicauth.entity.UserInfo;
import io.hyao.custombasicauth.repository.JdbcUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.IOException;
import java.util.Base64;

@Component
public class MyInterceptor implements HandlerInterceptor {
    private final JdbcUserRepository jdbcUserRepository;
    private final PasswordEncoder passwordEncoder;
    public MyInterceptor(JdbcUserRepository jdbcUserRepository, PasswordEncoder passwordEncoder) {
        this.jdbcUserRepository = jdbcUserRepository;
        this.passwordEncoder = passwordEncoder;
    }
    private boolean verifyUser( String header) {
        if (header != null && header.startsWith("Basic")) {
            String originCredential = header.substring(6);
            byte[] decodeBytes = Base64.getDecoder().decode(originCredential);
            String userCredentials = new String(decodeBytes);
            String[] parts = userCredentials.split(":", 2);
            String userName = parts[0];
            String password = parts[1];
            UserInfo userInfo = jdbcUserRepository.checkUserByUserName(userName);
            if (userInfo == null) {
                return false;
            } else {
                String storedName = userInfo.getUserName();
                String encodePw = userInfo.getPassword();
                if (!storedName.equals(userName)){
                    return false;
                } else {
                    return passwordEncoder.matches(password, encodePw);
                }
            }
        }
        return false;
    }
    private boolean myAuth(HttpServletRequest request) {
        String header = request.getHeader("Auth");

        return verifyUser(header);
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws IOException {
        if (!myAuth(request)) {
            System.out.println(request.getLocale());
            response.sendError(401, "unauthorized");
        }
        return myAuth(request);
    }
}
