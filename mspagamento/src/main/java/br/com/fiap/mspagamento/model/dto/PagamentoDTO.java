package br.com.fiap.mspagamento.model.dto;

import br.com.fiap.mspagamento.model.Enum.TipoPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoDTO {

    private Integer id;

    private Integer idCarrinhoDeCompras;

    private List<ItemCarrinhoDTO> itensCarrinho;

    private int quantidadeTotal;

    private double valorTotal;

    private TipoPagamentoEnum tipoPagamento;

    private String statusPagamento;
}
