package br.com.fiap.mspagamento.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrinhoDTO {

    private Integer id;
    private Integer idProduto;
    private int quantidade;
}
