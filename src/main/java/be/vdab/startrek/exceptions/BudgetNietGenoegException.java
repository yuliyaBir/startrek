package be.vdab.startrek.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BudgetNietGenoegException extends RuntimeException{
    public BudgetNietGenoegException() {
        super("Onvoldoende budget");
    }
}
