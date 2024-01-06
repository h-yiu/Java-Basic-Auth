package io.hyao.custombasicauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class MyController {

    @PostMapping("/res")
    public ResponseEntity<Map<String,String>> resourceSample () {
        return ResponseEntity.ok(Map.of("cstatus", "003", "message", "Good Things"));
    }

}
