package lis.shop.billion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основний клас запуску Spring Boot застосунку.
 * Анотація @SpringBootApplication поєднує в собі:
 * - @Configuration — вказує, що клас містить конфігурацію Spring-контейнера;
 * - @EnableAutoConfiguration — дозволяє автоматичну конфігурацію Spring;
 * - @ComponentScan — включає сканування компонентів у поточному пакеті та підпакетах.
 *
 *  Лісович Олександр Васильович
 */

@SpringBootApplication
public class OlbApplication {

	/**
	 * Головний метод — точка входу в застосунок.
	 * Запускає Spring Boot додаток з використанням вбудованого Tomcat сервера.
	 *
	 * @param args аргументи командного рядка (опціонально)
	 */
	public static void main(String[] args) {
		SpringApplication.run(OlbApplication.class, args);
	}

}
