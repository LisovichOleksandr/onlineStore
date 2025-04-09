package lis.shop.billion.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Контролер для завантаження аватарів користувачів.
 * Обробляє POST-запити для збереження файлу на сервері.
 */

@RestController
@RequestMapping("/api/user")
public class AvatarController {

    // Шлях до директорії, в яку зберігатимуться аватари (налаштовується в application.yaml)
    @Value("${app.upload.dir}")
    private String uploadDir;

    /**
     * Завантажує аватар користувача на сервер.
     *
     * @param file Multipart файл, надісланий у запиті
     * @return ResponseEntity зі статусом OK і назвою збереженого файлу, або помилкою
     */
    @PostMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam("file")MultipartFile file) throws IOException {

        if (file.isEmpty()){
            return ResponseEntity.badRequest().body("Файл пустий");
        }

        // Створюєм папку якщо її немає
        File uploadPath = new File(uploadDir);
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        // Генеруєм унікальне ім'я для файла
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File destination = new File(uploadDir + fileName);
        file.transferTo(destination);

        return ResponseEntity.ok("Файл збережено" + fileName);
    }
}
