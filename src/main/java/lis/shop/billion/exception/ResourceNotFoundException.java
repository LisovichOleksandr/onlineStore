package lis.shop.billion.exception;

/**
 * Цей ексцепшин вакидаемо в сервісі, для того аби позбавитися від ResponseEntity в контроллерах,
 * використовуючи глобальний обробник GlobalExceptionHandler
 * */

/**
 * Клас ResourceNotFoundException є власним (кастомним) винятком,
 * який наслідує від RuntimeException.
 *
 * Його зазвичай використовують в сервісі, для того аби позбавитися від ResponseEntity в контролерах,
 * використовуючи глобальний обробник GlobalExceptionHandler
 *
 * Конструктор приймає повідомлення, яке пояснює, який саме ресурс
 * не знайдено, і передає його до базового класу RuntimeException.
 */

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String messages){
        super(messages);
    }

}
