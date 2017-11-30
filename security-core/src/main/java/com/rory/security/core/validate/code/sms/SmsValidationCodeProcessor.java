package com.rory.security.core.validate.code.sms;

import com.rory.security.core.validate.code.sms.sender.SmsCodeSender;
import com.rory.security.core.validate.code.support.ValidationCode;
import com.rory.security.core.validate.code.support.impl.AbstractValidationCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsValidationCodeProcessor")
public class SmsValidationCodeProcessor extends AbstractValidationCodeProcessor<ValidationCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    public void sendCode(ServletWebRequest request, ValidationCode code) throws Exception {
        smsCodeSender.send(ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile"), code.getCode());
    }
}
