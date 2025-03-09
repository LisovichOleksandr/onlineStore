package lis.shop.billion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    public ResponseEntity<String> getMapping(){
        return ResponseEntity.ok().body("You are ADMIN!!!!!");
    }
}
