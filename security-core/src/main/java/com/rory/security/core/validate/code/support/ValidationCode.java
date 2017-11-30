package com.rory.security.core.validate.code.support;

import java.time.LocalDateTime;

public class ValidationCode {
    private String code;
    private LocalDateTime expiredTime;

    public ValidationCode(String code, int expiredIn) {
        this.code = code;
        this.expiredTime = LocalDateTime.now().plusSeconds(expiredIn);
    }

    public ValidationCode(String code, LocalDateTime expiredTime) {
        this.code = code;
        this.expiredTime = expiredTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public boolean isExpired() {
        return this.getExpiredTime().isBefore(LocalDateTime.now());
    }
}
