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
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<Object> exception(NumberFormatException exception) {
        String errorMessage = "The amount must be integer in between 20 and 30000.";
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
