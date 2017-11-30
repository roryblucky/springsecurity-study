package com.rory.security.core.validate.code;

import com.rory.security.core.validate.code.image.ImageCodeGenerator;
import com.rory.security.core.validate.code.sms.sender.DefaultSmsCodeSender;
import com.rory.security.core.validate.code.sms.sender.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationCodeConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "imageValidationCodeGenerator")
    public ImageCodeGenerator imageValidationCodeGenerator() {
        return new ImageCodeGenerator();
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }
}
