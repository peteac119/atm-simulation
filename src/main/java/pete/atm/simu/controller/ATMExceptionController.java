package pete.atm.simu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pete.atm.simu.exception.ATMException;

@ControllerAdvice
public class ATMExceptionController {
    @ExceptionHandler(value = ATMException.class)
    public ResponseEntity<Object> exception(ATMException exception) {
        System.out.println("handler got trigger");
        System.out.println("Type: " + exception.getClass().getSimpleName());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
