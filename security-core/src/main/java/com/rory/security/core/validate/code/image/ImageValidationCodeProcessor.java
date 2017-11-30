package com.rory.security.core.validate.code.image;

import com.rory.security.core.validate.code.support.impl.AbstractValidationCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

@Component("imageValidationCodeProcessor")
public class ImageValidationCodeProcessor extends AbstractValidationCodeProcessor<ImageCode> {

    @Override
    public void sendCode(ServletWebRequest request, ImageCode code) throws Exception {
        ImageIO.write(code.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
