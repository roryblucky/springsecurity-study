package com.rory.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rory.security.core.properties.LoginType;
import com.rory.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("failureAuthenticationHandler")
public class FailureAuthenticationHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(FailureAuthenticationHandler.class);

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(objectMapper.writeValueAsString(exception));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
