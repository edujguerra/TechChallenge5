package br.com.fiap.controller;

import br.com.fiap.infra.security.SecurityFilter;
import br.com.fiap.model.CarrinhoCompras;
import br.com.fiap.model.ItemCarrinho;
import br.com.fiap.service.CarrinhoComprasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/carrinhos")
public class CarrinhoComprasController {

    @Autowired
    private CarrinhoComprasService carrinhoComprasService;

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private RestTemplate restTemplate;

    public CarrinhoComprasController(CarrinhoComprasService carrinhoComprasService) {
        this.carrinhoComprasService = carrinhoComprasService;
    }

    @PostMapping
    public ResponseEntity<?> adicionarAoCarrinho(@RequestBody CarrinhoCompras carrinhoCompras) {
        try {
            CarrinhoCompras novoCarrinho = carrinhoComprasService.adicionarNoCarrinhoCompras(carrinhoCompras);
            return new ResponseEntity<>(novoCarrinho, HttpStatus.CREATED);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>("Um ou mais produtos não estao disponiveis", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<CarrinhoCompras> listarItensCarrinhoCompras() {
        return carrinhoComprasService.listarItensCarrinho();
    }

    @GetMapping("/{carrinhoComprasId}")
    public CarrinhoCompras obterCarrinhoComprasPorId(@PathVariable Integer carrinhoComprasId) {
        return carrinhoComprasService.obterCarrinhoCompras(carrinhoComprasId);
    }

    @GetMapping("/status/{statusCarrinhoCompras}")
    public ResponseEntity<?> obterCarrinhoComprasPorStatus(@PathVariable String statusCarrinhoCompras) {
        try {
            List<CarrinhoCompras> listaCarrinhoCompras =
                    carrinhoComprasService.obterCarrinhoComprasPorStatus(statusCarrinhoCompras);
            return new ResponseEntity<>(listaCarrinhoCompras, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum Carrinho de Compras encontrado para o status fornecido.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao processar a solicitação.");
        }
    }

    @DeleteMapping("/{carrinhoComprasId}")
    public void cancelarcarrinhoCompras(@PathVariable Integer carrinhoComprasId) {
        carrinhoComprasService.cancelarCarrinhoCompras(carrinhoComprasId);
    }

    @PutMapping("/{carrinhoComprasId}")
    public CarrinhoCompras atualizarStatus(@PathVariable Integer carrinhoComprasId, @RequestBody JsonNode status) {
        return carrinhoComprasService.atualizarStatus(carrinhoComprasId, status.findValue("status").asText());
    }

    @GetMapping("/itensCarrinho/{carrinhoComprasId}")
    public ResponseEntity<?> obterItensCarrinho(@PathVariable Integer carrinhoComprasId) {
        try {
            List<ItemCarrinho> itensCarrinho = carrinhoComprasService.obterItensCarrinho(carrinhoComprasId);

            return new ResponseEntity<>(itensCarrinho, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum Carrinho de Compras encontrado com o id do carrinho fornecido.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao processar a solicitação.");
        }
    }

}
