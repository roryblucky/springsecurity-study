package com.rory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerErrorAdvice {

    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> hanldeUserNotExistException(UserNotExistException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", ex.getUserId());
        map.put("message", ex.getMessage());
        return map;
    }
}
