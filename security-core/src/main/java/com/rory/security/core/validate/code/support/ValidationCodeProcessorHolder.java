package com.rory.security.core.validate.code.support;

import com.rory.security.core.validate.code.ValidationCodeType;
import com.rory.security.core.validate.code.exception.ValidationCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ValidationCodeProcessorHolder {

    @Autowired
    private Map<String, ValidationCodeProcessor> validationCodeProcessors;

    public ValidationCodeProcessor findValidationCodeProcessor(ValidationCodeType codeType) {
        String name = codeType.toString().toLowerCase() + ValidationCodeProcessor.class.getSimpleName();
        ValidationCodeProcessor processor = validationCodeProcessors.get(name);
        if (processor == null) {
            throw new ValidationCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }


}
