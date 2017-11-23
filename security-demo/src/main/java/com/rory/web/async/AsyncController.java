package com.rory.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {
    private Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @GetMapping("/order")
    public Callable<String> order() throws Exception {
        logger.info("主线程开始");
        Callable<String> result = () -> {
            logger.info("副线程开始");
            Thread.sleep(4000);
            logger.info("副线程返回");
            return "return";
        };

        //DeferredResult
        logger.info("主线程返回");
        return result;
    }
}
