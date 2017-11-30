package com.rory.security.core.validate.code;

import com.rory.security.core.validate.code.support.ValidationCodeProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ValidationCodeController {

    @Autowired
    private ValidationCodeProcessorHolder validateCodeProcessorHolder;

    @GetMapping("/code/{type}")
    public void createValidationCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeProcessorHolder.findValidationCodeProcessor(ValidationCodeType.valueOf(type.toUpperCase()))
                .getCode(new ServletWebRequest(request, response));
    }

}
