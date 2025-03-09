package lis.shop.billion.controller.jwtDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) для authentication користувача.
 *
 * Цей клас використовується для передачі access і refresh токенів
 * на front-end
 *
 * @field type             тип для authentication
 * @field accessToken      access токен
 * @field refreshToken     refresh токен
 */

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

}