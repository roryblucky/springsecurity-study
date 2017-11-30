package com.rory.security.core.validate.code.sms.sender;

public interface SmsCodeSender {
    void send(String mobile, String code);
}
