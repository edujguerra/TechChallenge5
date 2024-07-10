package br.com.fiap.model.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum StatusPedidoEnum {

    PEDIDO_RECEBIDO("Recebido", "Seu pedido foi recebido e está sendo processado."),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento", "Seu pedido foi aprovado e está aguardando pagamento."),
    PEDIDO_PAGO("Pagamento aprovado!","Pagamento aprovado! Pedido sendo preparado para entrega."),
    PREPARANDO_PARA_ENVIO("Pedido em preparação","Seu pedido está sendo preparado para envio."),
    EM_TRANSITO("Pedido em trânsito","Seu pedido está à caminho. Acompanhe a entrega do(s) produto(s)."),
    ENTREGUE("Produto entregue", "Produto foi entregue! Agradecemos sua compra!");

    private String nome;
    private String descricao;

    // Método estático para obter o enum a partir do nome
    public static StatusPedidoEnum obterStatusPorNome(String nome) {
        return Arrays.stream(values())
                .filter(status -> status.nome.equalsIgnoreCase(nome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status " + nome + " não encontrado"));
    }

    // Método estático para obter o enum a partir do nome ou de uma string igual ao próprio nome do enum
    public static StatusPedidoEnum obterStatusPorNomeOuStringEnum(String status) {
        return Arrays.stream(values())
                .filter(enumValue -> enumValue.getNome().equalsIgnoreCase(status) || enumValue.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhum status encontrado com o nome fornecido: " + status));
    }

}
