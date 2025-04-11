package lis.shop.billion.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    // Шлях до директорії, в яку зберігатимуться аватари (налаштовується в application.yaml)
    @Value("${app.upload.dir}")
    private String loadDir;


    public Resource loadImage(String filename) throws MalformedURLException {
        Path rootLocation = Paths.get(loadDir);
        Path file = rootLocation.resolve(filename);
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read file: " + filename);
        }
    }

    public String saveImage(MultipartFile file) throws IOException {

        Path rootLocation = Paths.get(loadDir);

        // Створюєм папку якщо її немає
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }

        // Генеруєм унікальне ім'я для файла]
        String originalFilename = file.getOriginalFilename();
        String cleanFilename = originalFilename != null ?
                originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_") :
                "avatar";
        String newFilename = UUID.randomUUID() + "_" + cleanFilename;


        Files.copy(file.getInputStream(), rootLocation.resolve(newFilename));
        return newFilename;
    }

    public void deleteImage(String filename) throws IOException {
        Path rootLocation = Paths.get(loadDir);
        Path file = rootLocation.resolve(filename);
        Files.deleteIfExists(file);
    }
}
