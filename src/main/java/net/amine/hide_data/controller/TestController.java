package net.amine.hide_data.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/api/test")
    public String test(@RequestParam String username, @RequestParam String pin, @RequestParam String cardNumber) {
        logger.info("Request received with username: {}, pin: {}, cardNumber: {}", username, pin, cardNumber);
        return "Request received for user: " + username;
    }
}
