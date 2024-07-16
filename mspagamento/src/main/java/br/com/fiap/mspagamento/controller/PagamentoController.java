package br.com.fiap.mspagamento.controller;

import br.com.fiap.mspagamento.infra.exception.PagamentoDuplicadoException;
import br.com.fiap.mspagamento.model.Pagamento;
import br.com.fiap.mspagamento.model.dto.PagamentoDTO;
import br.com.fiap.mspagamento.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<?> registrarPagamento (@RequestBody Pagamento pagamento) {
        try{
            PagamentoDTO novoPagamento = pagamentoService.realizarPagamento(pagamento.getIdCarrinhoDeCompras(),pagamento.getTipoPagamento());
            return new ResponseEntity<>(novoPagamento, HttpStatus.CREATED);
        } catch(NoSuchElementException e) {
            return new ResponseEntity<>("Carrinho de compras ou tipo de pagamento não estão disponiveis", HttpStatus.BAD_REQUEST);
        } catch (PagamentoDuplicadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Pagamento> listarPagamentos() {
        return pagamentoService.listarPagamentos();
    }

    @GetMapping("/{pagamentoId}")
    public Pagamento obterPagamentoPorId(@PathVariable Integer pagamentoId) {
        return pagamentoService.obterPagamentoPorId(pagamentoId);
    }

}
