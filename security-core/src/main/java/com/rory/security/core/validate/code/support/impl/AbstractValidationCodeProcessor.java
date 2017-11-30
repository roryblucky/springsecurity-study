package com.rory.security.core.validate.code.support.impl;

import com.rory.security.core.validate.code.ValidationCodeType;
import com.rory.security.core.validate.code.exception.ValidationCodeException;
import com.rory.security.core.validate.code.support.ValidationCode;
import com.rory.security.core.validate.code.support.ValidationCodeGenerator;
import com.rory.security.core.validate.code.support.ValidationCodeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidationCodeProcessor<T extends ValidationCode> implements ValidationCodeProcessor {

    @Autowired
    private Map<String, ValidationCodeGenerator> generators;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void getCode(ServletWebRequest request) throws Exception {
        T code = this.generateCode(request);
        saveToSession(request, code);
        sendCode(request, code);
    }

    @SuppressWarnings("unchecked")
    private T generateCode(ServletWebRequest request) {
        String type = getGeneratorType().toString().toLowerCase();
        String generatorName = type + ValidationCodeGenerator.class.getSimpleName();
        ValidationCodeGenerator generator = generators.get(generatorName);
        return (T) generator.generateValidationCode(request);
    }

    private ValidationCodeType getGeneratorType() {
        String url = StringUtils.substringBefore(this.getClass().getSimpleName(), "ValidationCodeProcessor");
        return ValidationCodeType.valueOf(url.toUpperCase());
    }

    private void saveToSession(ServletWebRequest request, T code) {
        sessionStrategy.setAttribute(request, getSessionKey(), code);
    }

    private String getSessionKey() {
        return SESSION_VALIDATION_CODE_KEY + getGeneratorType().toString().toUpperCase();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) throws ValidationCodeException {
        ValidationCodeType processorType = getGeneratorType();
        String sessionKey = getSessionKey();

        T codeInSession = (T) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidationCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidationCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidationCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidationCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidationCodeException(processorType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }

    public abstract void sendCode(ServletWebRequest request, T code) throws Exception;
}
