package lis.shop.billion.controller;

import lis.shop.billion.securityConfiguration.jwt.JwtAuthentication;
import lis.shop.billion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


/**
 * Контролер для завантаження аватарів користувачів.
 * Обробляє POST-запити для збереження файлу на сервері.
 */

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AvatarController {

    // Шлях до директорії, в яку зберігатимуться аватари (налаштовується в application.yaml)
    @Value("${app.upload.dir}")
    private String uploadDir;

    private final UserService userService;

    /**
     * Завантажує аватар користувача на сервер.
     *
     * @param file Multipart файл, надісланий у запиті
     * @return ResponseEntity зі статусом OK і назвою збереженого файлу, або помилкою
     */
    @PostMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam("photo")MultipartFile file) throws IOException {

        if (file.isEmpty()){
            return ResponseEntity.badRequest().body("Файл пустий");
        }

        // Створюєм папку якщо її немає
        File uploadPath = new File(uploadDir);
        if(!uploadPath.exists()){
            uploadPath.mkdirs();
        }

        // Генеруєм унікальне ім'я для файла]
        String originalFilename = file.getOriginalFilename();

        String cleanFilename = originalFilename != null ?
                originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_") :
                "avatar";
        String fileName = UUID.randomUUID() + "_" + cleanFilename;
        // створюємо повний шлях до файлу
        File destination = new File(uploadDir + File.separator + fileName);
        try {
            // положили файл в директорію
            file.transferTo(destination.toPath());

            // Отримуємо JWT-аутентифікацію з контексту безпеки
            JwtAuthentication auth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) {
                // Якщо користувач не автентифікований — повертаємо статус 401
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            // Отримуємо email з JWT і прив'язуем фотографію до користувача по емайлу
            String email = (String) auth.getPrincipal();
            userService.savePhoto(email, fileName);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Не вдалося зберегти файл");
        }

        return ResponseEntity.ok(Map.of(
                "message", "Файл збережено",
                "fileName", fileName
        ).toString());
    }
}
