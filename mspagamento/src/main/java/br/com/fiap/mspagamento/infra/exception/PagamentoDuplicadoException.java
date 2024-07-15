package br.com.fiap.mspagamento.infra.exception;

public class PagamentoDuplicadoException extends RuntimeException {

    public PagamentoDuplicadoException() {
        super("Este carrinho ja se encontra com pagamento realizado.");
    }

    public PagamentoDuplicadoException(String message) {
        super(message);
    }

    public PagamentoDuplicadoException(String message, Throwable cause) {
        super(message, cause);
    }
}
