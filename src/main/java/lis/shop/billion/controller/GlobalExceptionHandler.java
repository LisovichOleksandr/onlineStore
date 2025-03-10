package lis.shop.billion.controller;

import lis.shop.billion.controller.errorDto.AppError;
import lis.shop.billion.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Глобальний обробник виключень для REST API.
 * Обробляє як валідаційні помилки, так і кастомні бізнес-винятки.
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Обробляє винятки {@link MethodArgumentNotValidException}, що виникають при невдалій валідації аргументів.
     *
     * @param ex виняток, що містить список помилок валідації
     * @return ResponseEntity з HTTP статусом 400 (BAD_REQUEST) та тілом, що містить мапу з назвами полів
     * і повідомленнями про помилки
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Обробляє винятки, коли ресурс не знайдено.
     *
     * @param exception кастомний виняток
     * @return об'єкт AppError з повідомленням та кодом 404
     */
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(
                new AppError(HttpStatus.NOT_FOUND.value(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
