package lis.shop.billion.controller.jwtDto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) для authentication користувача.
 *
 * Цей клас використовується для оновлення access токена за допомогою
 * refresh токена котрий передається з front-end.
 *
 * @field refreshToken      refresh токен
 */

@Getter
@Setter
public class RefreshJwtRequest {

    public String refreshToken;

}
