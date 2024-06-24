package Goweb.FormMaker.exception;

public class ResponseNotFoundException extends RuntimeException {
    public ResponseNotFoundException(Long responseId) {
        super("response with id " + responseId + " not found.");
    }
}
