package br.com.fiap.mspagamento.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.mspagamento.model.Pagamento;
import br.com.fiap.mspagamento.model.Enum.TipoPagamentoEnum;

public class PagamentoHelper {
    public static Pagamento gerarPagamento() {
    Pagamento pagamento = new Pagamento();
        pagamento.setIdCarrinhoDeCompras(1);
        pagamento.setQuantidadeTotal(10);
        pagamento.setValorTotal(500);
        pagamento.setTipoPagamento(TipoPagamentoEnum.DEBITO);
        pagamento.setStatusPagamento("P");

        return pagamento;
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
