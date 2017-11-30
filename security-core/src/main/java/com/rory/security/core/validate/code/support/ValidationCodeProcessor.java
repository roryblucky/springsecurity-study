package com.rory.security.core.validate.code.support;

import com.rory.security.core.validate.code.exception.ValidationCodeException;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidationCodeProcessor {

    String SESSION_VALIDATION_CODE_KEY = "SESSION_VALIDATION_CODE_KEY_";

    void getCode(ServletWebRequest request) throws Exception;

    void validate(ServletWebRequest request) throws ValidationCodeException;
}
