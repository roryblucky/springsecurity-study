package com.rory.security.core.validate.code.sms.sender;

public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println("mobile send code ==> " + code);
    }
}
