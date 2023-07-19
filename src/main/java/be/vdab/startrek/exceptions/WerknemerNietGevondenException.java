package be.vdab.startrek.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WerknemerNietGevondenException extends RuntimeException{
    public WerknemerNietGevondenException(long id) {
        super("De werknemer is niet gevonden. Id: " + id);
    }
}
