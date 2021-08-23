package io.fahmikudo.wallet.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fahmikudo.wallet.model.BaseResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        var baseResponse = new BaseResponse();
        baseResponse.setStatus(401);
        baseResponse.setMessage("Check your JWT please!");
        baseResponse.setResult(null);

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write(mapper.writeValueAsString(baseResponse));
    }
}
