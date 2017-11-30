package com.rory.security.core.validate.code.sms;

import com.rory.security.core.properties.SecurityProperties;
import com.rory.security.core.validate.code.support.ValidationCode;
import com.rory.security.core.validate.code.support.ValidationCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsValidationCodeGenerator")
public class SmsCodeGenerator implements ValidationCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidationCode generateValidationCode(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidationCode(code, securityProperties.getCode().getSms().getExpiredIn());
    }

}
