package pete.atm.simu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pete.atm.simu.model.DispensingResultReport;

/**
 * Global exception handler of all restful API endpoints.
 */

@ControllerAdvice
public class ATMGlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ATMGlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<DispensingResultReport> exception(Exception exception) {
        LOGGER.error("Unexpected Exception is thrown", exception);
        DispensingResultReport errorReport = new DispensingResultReport(null);
        errorReport.setError(exception.getMessage());
        return new ResponseEntity<>(errorReport, HttpStatus.BAD_REQUEST);
    }
}
