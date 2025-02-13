package com.example.globalpie.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CaptchaController {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    @PostMapping("/verify-captcha")
    public ResponseEntity<Map<String, Object>> verifyCaptcha(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "CAPTCHA token missing"));
        }

        // Send verification request to Google
        RestTemplate restTemplate = new RestTemplate();
        String verifyUrl = GOOGLE_RECAPTCHA_VERIFY_URL + "?secret=" + recaptchaSecret + "&response=" + token;

        Map response = restTemplate.postForObject(verifyUrl, null, Map.class);
        boolean success = (Boolean) response.get("success");

        Map<String, Object> result = new HashMap<>();
        result.put("success", success);

        return ResponseEntity.ok(result);
    }
}


