package br.com.fiap.mscliente.controller;

import br.com.fiap.mscliente.model.Cliente;
import br.com.fiap.mscliente.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cliente> buscarTodos() {

        return service.buscarTodos();
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody Cliente cliente){

        cliente = service.salvar(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarUm(@PathVariable Integer id) {

        return service.buscarUm(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Integer id, @RequestBody Cliente novo) {

        return service.atualizar(id,novo);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {

        service.excluir(id);
    }
}
