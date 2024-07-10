package br.com.fiap.model.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private Integer quantidade_estoque;
    private double preco;
}
