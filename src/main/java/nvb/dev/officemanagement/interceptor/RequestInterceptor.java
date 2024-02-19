package nvb.dev.officemanagement.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import nvb.dev.officemanagement.exception.InvalidHeaderException;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

import static nvb.dev.officemanagement.constant.Constant.X_SYSTEM_NAME;
import static nvb.dev.officemanagement.constant.Constant.X_SYSTEM_PASSWORD;

@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {

        HttpHeaders headers = generateHttpHeaders();

        String name = Objects.requireNonNull(headers.get(X_SYSTEM_NAME)).getFirst();
        String password = Objects.requireNonNull(headers.get(X_SYSTEM_PASSWORD)).getFirst();

        if (!Objects.equals(request.getHeader(X_SYSTEM_NAME), name) &&
                !Objects.equals(request.getHeader(X_SYSTEM_PASSWORD), password)) {
            throw new InvalidHeaderException();
        }

        return true;
    }

    private HttpHeaders generateHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(X_SYSTEM_NAME, "default-user");
        httpHeaders.set(X_SYSTEM_PASSWORD, "Aa123456");
        return httpHeaders;
    }
}
