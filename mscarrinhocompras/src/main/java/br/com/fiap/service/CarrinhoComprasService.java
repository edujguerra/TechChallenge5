package br.com.fiap.service;

import br.com.fiap.infra.security.SecurityFilter;
import br.com.fiap.model.Enum.StatusPedidoEnum;
import br.com.fiap.model.ItemCarrinho;
import br.com.fiap.model.CarrinhoCompras;
import br.com.fiap.repository.CarrinhoComprasRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarrinhoComprasService {

    @Autowired
    private CarrinhoComprasRepository carrinhoComprasRepository;

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String RETIRAR_ESTOQUE = "retirar";
    private static final String INSERIR_ESTOQUE = "inserir";

    public CarrinhoComprasService(CarrinhoComprasRepository carrinhoComprasRepository, SecurityFilter securityFilter,
                                  RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.carrinhoComprasRepository = carrinhoComprasRepository;
        this.securityFilter = securityFilter;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<CarrinhoCompras> listarItensCarrinho() {
        return carrinhoComprasRepository.findAll();
    }

    public CarrinhoCompras obterCarrinhoCompras(Integer carrinhoComprasId) {
        CarrinhoCompras carrinhoCompras = carrinhoComprasRepository.findById(carrinhoComprasId).orElse(null);
        if (carrinhoCompras != null) {
            return carrinhoCompras;
        } else {
            throw new NoSuchElementException("Carrinho de compras com código {} não encontrado" + carrinhoComprasId);
        }
    }

    public List<CarrinhoCompras> obterCarrinhoComprasPorStatus(String statusCarrinhoCompras) {
        StatusPedidoEnum statusPedidoEnum = StatusPedidoEnum.obterStatusPorNomeOuStringEnum(statusCarrinhoCompras);
        List<CarrinhoCompras> listaCarrinhoCompras = carrinhoComprasRepository.findByStatus(statusPedidoEnum);
        if (listaCarrinhoCompras.isEmpty()) {
            throw new NoSuchElementException("Não existem Carrinho de Compras com o status solicitado");
        }
        return listaCarrinhoCompras;
    }

    public CarrinhoCompras adicionarNoCarrinhoCompras(CarrinhoCompras carrinhoCompras) {

        boolean produtosDisponiveis = verificarDisponibilidadeProdutos(carrinhoCompras.getItensCarrinho());

        if (!produtosDisponiveis) {
            throw new NoSuchElementException("Um ou mais produtos não estao disponiveis");
        }

        double valorTotal = calcularValorTotal(carrinhoCompras.getItensCarrinho());
        carrinhoCompras.setValorTotal(valorTotal);
        carrinhoCompras.setStatus(StatusPedidoEnum.PEDIDO_RECEBIDO);

        atualizarEstoqueProdutos(carrinhoCompras.getItensCarrinho(), RETIRAR_ESTOQUE);

        return carrinhoComprasRepository.save(carrinhoCompras);
    }

    public void cancelarCarrinhoCompras(Integer carrinhoComprasId) {
        CarrinhoCompras carrinhoExistente = carrinhoComprasRepository.findById(carrinhoComprasId).orElse(null);

        if (carrinhoExistente != null) {
            carrinhoComprasRepository.delete(carrinhoExistente);

            atualizarEstoqueProdutos(carrinhoExistente.getItensCarrinho(), INSERIR_ESTOQUE);
        } else {
            throw new NoSuchElementException("CarrinhoCompras com código {} não encontrado" + carrinhoComprasId);
        }
    }

    public CarrinhoCompras atualizarStatus(Integer carrinhoComprasId, String status) {
        CarrinhoCompras carrinhoCompras = carrinhoComprasRepository.findById(carrinhoComprasId).orElse(null);
        StatusPedidoEnum statusPedidoEnum = StatusPedidoEnum.obterStatusPorNomeOuStringEnum(status);

        if (carrinhoCompras != null) {
            carrinhoCompras.setStatus(statusPedidoEnum);
            carrinhoComprasRepository.save(carrinhoCompras);
        } else {
            throw new NoSuchElementException("CarrinhoCompras com código {} não encontrado" + carrinhoComprasId);
        }

        return carrinhoCompras;
    }

    public List<ItemCarrinho> obterItensCarrinho(Integer carrinhoComprasId) {

        List<ItemCarrinho> listaItensCarrinho;
        CarrinhoCompras carrinhoCompras = carrinhoComprasRepository.findById(carrinhoComprasId).orElse(null);

        if (carrinhoCompras.getItensCarrinho() != null) {
            listaItensCarrinho = carrinhoCompras.getItensCarrinho();
        } else {
            throw new NoSuchElementException("Carrinho de compras com código {} não encontrado" + carrinhoComprasId);
        }

        return listaItensCarrinho;
    }

    private boolean verificarDisponibilidadeProdutos(List<ItemCarrinho> itensCarrinhos) {

        for (ItemCarrinho itemCarrinho : itensCarrinhos) {
            Integer idProduto = itemCarrinho.getIdProduto();
            int quantidade = itemCarrinho.getQuantidade();

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", securityFilter.getTokenBruto());

            URI uri = UriComponentsBuilder.fromUriString("http://msprodutos:8082/api/produtos/{produtoId}")
                    .buildAndExpand(idProduto)
                    .toUri();

            RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.GET, uri);

            ResponseEntity<String> response = restTemplate.exchange(request, String.class);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NoSuchElementException("Produto não encontrado");
            } else {
                try {
                    JsonNode produtoJson = objectMapper.readTree(response.getBody());
                    int quantidadeEstoque = produtoJson.get("quantidade_estoque").asInt();

                    if (quantidadeEstoque < quantidade) {
                        return false;
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Erro no metodo verificarDisponibilidadeProdutos");
                }
            }
        }
        return true;
    }

    private Double calcularValorTotal(List<ItemCarrinho> itensCarrinho) {
        double valorTotal = 0.0;

        for (ItemCarrinho itemCarrinho : itensCarrinho) {
            Integer idProduto = itemCarrinho.getIdProduto();
            int quantidade = itemCarrinho.getQuantidade();

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", securityFilter.getTokenBruto());

            URI uri = UriComponentsBuilder.fromUriString( "http://msprodutos:8082/api/produtos/{idProduto}")
                    .buildAndExpand(idProduto)
                    .toUri();

            RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.GET, uri);

            ResponseEntity<String> response = restTemplate.exchange(request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    JsonNode produtoJson = objectMapper.readTree(response.getBody());
                    double preco = produtoJson.get("preco").asDouble();
                    valorTotal += preco * quantidade;
                } catch (IOException e) {
                    throw new RuntimeException("Erro no metodo verificarDisponibilidadeProdutos");
                }
            }
        }
        return valorTotal;
    }

    private void atualizarEstoqueProdutos(List<ItemCarrinho> itensCarrinho, String entradaSaida) {

        for (ItemCarrinho itemCarrinho : itensCarrinho) {
            Integer idProduto = itemCarrinho.getIdProduto();
            int quantidade = itemCarrinho.getQuantidade();

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Authorization", securityFilter.getTokenBruto());

            URI uri = UriComponentsBuilder.fromUriString("http://msprodutos:8082/api/produtos/atualizar/estoque/{idProduto}/{quantidade}/{entradaSaida}")
                    .buildAndExpand(idProduto, quantidade, entradaSaida)
                    .toUri();

            RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.PUT, uri);

            ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        }
    }

}
