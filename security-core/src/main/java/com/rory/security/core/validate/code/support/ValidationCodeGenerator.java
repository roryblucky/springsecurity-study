package com.rory.security.core.validate.code.support;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidationCodeGenerator {
    ValidationCode generateValidationCode(ServletWebRequest request);
}
