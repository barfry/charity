package pl.coderslab.charity.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.exception.UserNotVerifiedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserNotVerifiedAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        if (hasUserNotVerifiedRootCause(exception)) {
            response.sendRedirect("/user-not-verified");
        } else {
            response.sendRedirect("/login-error");
        }
    }

    private boolean hasUserNotVerifiedRootCause(AuthenticationException exception) {
        Throwable cause = exception;
        while (cause.getCause() != null) {
            cause = cause.getCause();
            if (cause instanceof UserNotVerifiedException) {
                return true;
            }
        }
        return false;
    }
}

