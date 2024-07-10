package br.com.fiap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Integer id;

    @Column(name = "nm_produto", nullable = false)
    private String nome;

    @Column(name = "ds_descricao")
    private String descricao;

    @Column(name = "qt_estoque", nullable = false)
    private Integer quantidade_estoque;

    @Column(name = "pr_produto", nullable = false)
    private BigDecimal preco;

    @Column(name = "dt_update")
    private Timestamp dataUpdate;
}
