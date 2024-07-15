package br.com.fiap.mspagamento.model.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TipoPagamentoEnum {

    CARTAO("Cartão de Crédito"),
    BOLETO("Boleto Bancário"),
    DEBITO("Cartão de Débito"),
    PIX("PIX");

    private String nome;


}
