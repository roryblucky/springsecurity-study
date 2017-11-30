package com.rory.security.core.validate.code.image;

import com.rory.security.core.validate.code.support.ValidationCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode extends ValidationCode {
    private BufferedImage image;

    public ImageCode(String code, int expiredIn, BufferedImage image) {
        super(code, expiredIn);
        this.image = image;
    }

    public ImageCode(String code, LocalDateTime expiredTime, BufferedImage image) {
        super(code, expiredTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
