package lis.shop.billion.openApi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Клас конфігурації OpenAPI для генерації документації за допомогою Swagger.
 *
 * Цей клас використовує анотацію {@link OpenAPIDefinition}, щоб визначити загальну інформацію
 * про API системи лояльності, включаючи назву, опис, версію та контактні дані розробника.
 *
 * Завдяки цій конфігурації, документація OpenAPI (Swagger UI) буде містити зрозумілу
 * інформацію для користувачів та розробників, які працюють із API.
 *
 * Немає необхідності додавати логіку в цей клас — достатньо самої анотації.
 */

@OpenAPIDefinition(
        info = @Info(
                title = "Loyalty System Api",
                description = "API системы лояльности",
                version = "1.0.0",
                contact = @Contact(
                        name = "Lisovych Oleksander",
                        email = "lisovysh@gmail.com",
                        url = "https://oleksandr.lisovych.dev"
                )
        )
)
public class Config {
}
