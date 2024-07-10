package br.com.fiap.mslogin.exception;

import java.util.List;

public record ValidationErrorResponse(
        List<String> errors
) {
}
