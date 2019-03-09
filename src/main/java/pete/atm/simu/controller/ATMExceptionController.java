package pete.atm.simu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pete.atm.simu.model.DispensingResultReport;

@ControllerAdvice
public class ATMExceptionController {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<DispensingResultReport> exception(Exception exception) {
        System.out.println("Exception is thrown: " + exception);
        DispensingResultReport errorReport = new DispensingResultReport(null, null);
        errorReport.setError(exception.getMessage());
        return new ResponseEntity<>(errorReport, HttpStatus.BAD_REQUEST);
    }
}
