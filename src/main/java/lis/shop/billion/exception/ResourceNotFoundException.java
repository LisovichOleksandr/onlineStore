package lis.shop.billion.exception;

/**
 * Цей ексцепшин вакидаемо в сервісі, для того аби позбавитися від ResponseEntity в контроллерах,
 * використовуючи глобальний обробник GlobalExceptionHandler
 * */

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String messages){
        super(messages);
    }

}
