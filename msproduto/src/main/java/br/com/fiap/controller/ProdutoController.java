package br.com.fiap.controller;

import br.com.fiap.model.Produto;
import br.com.fiap.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService produtoService) {
        this.service = produtoService;
    }

    @GetMapping
    public List<Produto> buscarTodos() {

        return service.buscarTodos();
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody Produto produto) {

        produto =service.salvar(produto);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarUm(@PathVariable Integer id) {

        return service.buscarUm(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Integer id, @RequestBody Produto novo) {

        return service.atualizar(id, novo);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {

        service.excluir(id);
    }

    @PutMapping("/atualizar/estoque/{id}/{quantidade}/{entradaSaida}")
    public Produto atualizarEstoque(@PathVariable Integer id, @PathVariable int quantidade, @PathVariable String entradaSaida) {

        return service.atualizarEstoque(id, quantidade, entradaSaida);
    }

}