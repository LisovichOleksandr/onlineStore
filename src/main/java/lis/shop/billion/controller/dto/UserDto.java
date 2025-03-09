package lis.shop.billion.controller.dto;

import java.time.LocalDateTime;

public record UserDto (
        Long id,
        String firstName,
        String lastName,
        Integer age,
        String email,
        LocalDateTime createdAt,
        String photoUrl
) {
}
