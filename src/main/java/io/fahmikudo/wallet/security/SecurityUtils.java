package io.fahmikudo.wallet.security;

import com.google.common.base.Strings;
import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.exception.UserNotFound;
import io.fahmikudo.wallet.repository.UserRepository;
import io.fahmikudo.wallet.security.jwt.JwtConfig;
import io.fahmikudo.wallet.security.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static io.fahmikudo.wallet.util.controller.BaseController.checkGuestAccessAuthorization;

@Component
public class SecurityUtils {

    private final JwtConfig jwtConfig;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public SecurityUtils(JwtConfig jwtConfig, JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtConfig = jwtConfig;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public final User getUserFromJwt(String apiKeyHeader, String authorizationHeader) throws UserNotFound, HttpException {
        User user;
        if ((Strings.isNullOrEmpty(apiKeyHeader) || !checkGuestAccessAuthorization(apiKeyHeader)) || (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix()))) {
            throw new HttpException("Api key or Authorization wrong or empty", HttpStatus.BAD_REQUEST);
        } else {
            user = getUserFromRequestHeader(authorizationHeader);
        }
        return user;
    }

    public final User getUserFromServlet(HttpServletRequest request) throws UserNotFound, HttpException {
        User user;
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        String apiKeyHeader = request.getHeader("api-key");

        if ((Strings.isNullOrEmpty(apiKeyHeader) || !checkGuestAccessAuthorization(apiKeyHeader)) || (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix()))) {
            throw new HttpException("Api key or Authorization wrong or empty", HttpStatus.BAD_REQUEST);
        } else {
            user = getUserFromRequestHeader(authorizationHeader);
        }

        return user;
    }

    private User getUserFromRequestHeader(String authorizationHeader) throws UserNotFound {
        Optional<User> user;
        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
        String userId;

        try {
            userId = jwtUtil.extractUserID(jwtConfig.accessKey(), token);
        } catch (RuntimeException e) {
            throw new IllegalStateException("Check your JWT please!");
        }

        user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFound("Check your JWT please!");
        }

        return user.get();
    }

}
