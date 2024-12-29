package com.example.mountaineerback.controller;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/verify")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {
    private static final String SECRET_KEY = Dotenv.load().get("SECRET_KEY_RECAPTCHA");

    @PostMapping()
    public ResponseEntity<String> verifyRecaptcha(@RequestBody String token) {


        String secretKey = SECRET_KEY; // Don't expose your secret key directly fetch it from secret variables
        String url = "https://www.google.com/recaptcha/api/siteverify";
        RestTemplate restTemplate = new RestTemplate(); // RestTemplate 是 Spring 提供的用於發送 HTTP 請求的class

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secretKey);
        map.add("response", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.ok("reCAPTCHA token is valid");
        } else {
            return ResponseEntity.badRequest().body("reCAPTCHA token is invalid");
        }
    }
}
