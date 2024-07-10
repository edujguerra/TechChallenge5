package br.com.fiap.mscliente.service;

import java.util.List;
import java.util.NoSuchElementException;

import br.com.fiap.mscliente.model.CepResponse;
import br.com.fiap.mscliente.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.fiap.mscliente.repository.ClienteRepository;
import org.springframework.web.client.RestTemplate;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository repository) {
        this.clienteRepository = repository;
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente salvar(Cliente cliente) {

        ResponseEntity<Object> response = validaCampos(cliente);
        if (!response.getStatusCode().equals(HttpStatus.OK)  ){
            throw new NoSuchElementException("Cliente com problemas..." + response);
        }

        cliente = clienteRepository.save(cliente);
        return cliente;
    }

    private ResponseEntity<Object> validaCampos(Cliente cliente) {

        if (cliente.getNome() == null
                || cliente.getNome().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome não pode ser vazio.");
        }
        if (cliente.getEmail() == null ||
                cliente.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email não pode ser vazio.");
        }
        if (cliente.getCep() == null ||
                cliente.getCep().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cep não pode ser vazio.");
        }
        if (cliente.getCpf() == null ||
                cliente.getCpf().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF não pode ser vazio.");
        }
        if (cliente.getComplemento() == null ||
                cliente.getComplemento().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Complemento não pode ser vazio.");
        }

        try {
            String uriCep = "https://viacep.com.br/ws/" + cliente.getCep() + "/json/";
            RestTemplate restTemplate = new RestTemplate();

            CepResponse cepResponse = restTemplate.getForEntity(uriCep, CepResponse.class).getBody();
            if (cliente.getEndereco() == null ||
                    cliente.getEndereco().isEmpty()) {
                cliente.setEndereco(cepResponse.getLogradouro());
            }
            if (cliente.getBairro() == null ||
                    cliente.getBairro().isEmpty()) {
                cliente.setBairro(cepResponse.getBairro());
            }
            if (cliente.getCidade() == null ||
                    cliente.getCidade().isEmpty()) {
                cliente.setCidade(cepResponse.getLocalidade());
            }
            if (cliente.getUf() == null ||
                    cliente.getUf().isEmpty()) {
                cliente.setUf(cepResponse.getUf());
            }
        } catch (Exception err) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cep não encontrado. ");
        }
        return ResponseEntity.ok(cliente);
    }

    public ResponseEntity<Object> buscarUm(Integer id ) {

        Cliente cliente = clienteRepository.findById(id).orElse(null);

        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Cliente não encontrado.");
        }
    }

    public ResponseEntity<Object> atualizar(Integer id, Cliente novo) {

        Cliente existente = clienteRepository.findById(id).orElse(null);

        if (existente != null) {
            ResponseEntity<Object> response = validaCampos(novo);
            if (!response.getStatusCode().equals(HttpStatus.OK)  ){
                return response;
            }

            existente.setNome(novo.getNome());
            existente.setUf(novo.getUf());
            existente.setBairro(novo.getBairro());
            existente.setCidade(novo.getCidade());
            existente.setEndereco(novo.getEndereco());
            existente.setCep(novo.getCep());
            existente.setComplemento(novo.getComplemento());
            existente.setEmail(novo.getEmail());
            existente.setCpf(novo.getCpf());

            return ResponseEntity.ok(clienteRepository.save(existente));
        } else {
            throw new NoSuchElementException("Cliente não Encontrado.");
        }
    }

    public boolean excluir(Integer id) {

        Cliente existente = clienteRepository.findById(id).orElse(null);

        if (existente != null) {
            clienteRepository.delete(existente);
        } else {
            throw new NoSuchElementException("Cliente não encontrado");
        }
        return true;
    }
}
