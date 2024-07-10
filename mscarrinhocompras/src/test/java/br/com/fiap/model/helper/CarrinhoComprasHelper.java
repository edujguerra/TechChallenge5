package br.com.fiap.model.helper;

import br.com.fiap.model.Enum.StatusPedidoEnum;
import br.com.fiap.model.ItemCarrinho;
import br.com.fiap.model.CarrinhoCompras;
import br.com.fiap.model.dtos.ProdutoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoComprasHelper {

    public static CarrinhoCompras gerarCarrinhoCompras() {
        List<ItemCarrinho> listaItens = new ArrayList<>();
        ItemCarrinho itemCarrinho = new ItemCarrinho(1, 1, 2);
        listaItens.add(itemCarrinho);

        return new CarrinhoCompras(1, "Julio Cesar","49988822212", listaItens,
                359.99, StatusPedidoEnum.PEDIDO_PAGO,"08554040");
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static ProdutoDTO gerarProduto() {
        ProdutoDTO produtoDTO = new ProdutoDTO(1, "Produto ficticio", "Descricao do produto ficticio",
                10, 235.50);
        return produtoDTO;
    }

}
