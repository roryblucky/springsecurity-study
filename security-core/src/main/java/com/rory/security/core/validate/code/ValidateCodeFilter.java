package com.rory.security.core.validate.code;

import com.rory.security.core.properties.SecurityProperties;
import com.rory.security.core.validate.code.exception.ValidationCodeException;
import com.rory.security.core.validate.code.support.ValidationCodeProcessorHolder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private AuthenticationFailureHandler failureAuthenticationHandler;

    private Set<String> urls = new HashSet<>();

    @Autowired
    private SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Map<String, ValidationCodeType> urlMap = new HashMap<>();

    @Autowired
    private ValidationCodeProcessorHolder validationCodeProcessorHolder;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put("/authentication/form", ValidationCodeType.IMAGE);
        addURLToMap(securityProperties.getCode().getImage().getUrl(), ValidationCodeType.IMAGE);

        urlMap.put("/authentication/mobile", ValidationCodeType.SMS);
        addURLToMap(securityProperties.getCode().getSms().getUrl(), ValidationCodeType.SMS);
    }

    private void addURLToMap(String urlString, ValidationCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ValidationCodeType codeType = getValidateCodeType(request);
        if (codeType != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + codeType);
            try {
                validationCodeProcessorHolder.findValidationCodeProcessor(codeType)
                        .validate(new ServletWebRequest(request, response));
            } catch (ValidationCodeException e) {
               failureAuthenticationHandler.onAuthenticationFailure(request, response, e);
               return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private ValidationCodeType getValidateCodeType(HttpServletRequest request) {
        ValidationCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }
}
