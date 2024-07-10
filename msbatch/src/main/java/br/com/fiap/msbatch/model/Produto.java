package br.com.fiap.msbatch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Produto {

    private Integer id;

    private String nome;

    private String descricao;

    private Integer quantidade;

    private BigDecimal preco;

    private LocalDateTime dataUpdate;
}
