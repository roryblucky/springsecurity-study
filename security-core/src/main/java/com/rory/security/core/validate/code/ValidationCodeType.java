package com.rory.security.core.validate.code;

public enum  ValidationCodeType {

    SMS {
        @Override
        public String getParamNameOnValidate() {
            return "smsCode";
        }
    },

    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return "imageCode";
        }
    };

    public abstract String getParamNameOnValidate();
}
