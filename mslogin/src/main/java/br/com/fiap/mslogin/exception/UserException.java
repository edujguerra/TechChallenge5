package br.com.fiap.mslogin.exception;

public class UserException extends RuntimeException {

    private final int status;

    private final String message;

    public UserException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
