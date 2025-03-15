package lis.shop.billion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контролер для управління адміністративними операціями.
 * Цей контролер обробляє HTTP-запити, призначені для адміністраторів.
 */

@Controller
@RequestMapping("/api/admin")
public class AdminController {

    /**
     * Обробляє GET-запити за шляхом "/api/admin".
     *
     * @return ResponseEntity зі статусом 200 OK і повідомленням "You are ADMIN!!!!!"
     */
    @GetMapping
    public ResponseEntity<String> getMapping(){
        return ResponseEntity.ok().body("You are ADMIN!!!!!");
    }
}
