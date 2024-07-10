package br.com.fiap.service;

import br.com.fiap.model.Produto;
import br.com.fiap.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository repository) {
        this.produtoRepository = repository;
    }

    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    public Produto salvar(Produto produto) {

        ResponseEntity<Object> response = validaCampos(produto);
        if (!response.getStatusCode().equals(HttpStatus.OK)  ){
            throw new NoSuchElementException("Produto com problemas..." + response);
        }

        produto = produtoRepository.save(produto);
        return produto;
    }

    private ResponseEntity<Object> validaCampos(Produto produto) {

        if (produto.getNome() == null
                || produto.getNome().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome não pode ser vazio.");
        }
        if (produto.getQuantidade_estoque() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantidade em Estoque não pode ser nula");
        }
        if (produto.getQuantidade_estoque().compareTo(0) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantidade em estoque não pode ser igual ou menor que zero.");
        }
        if (produto.getPreco() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Preço não pode ser vazio");
        }
        if (produto.getPreco().compareTo(new BigDecimal(0)) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Preço não pode ser igual ou menor que zero.");
        }

        return ResponseEntity.ok(produto);
    }

    public ResponseEntity<Object> buscarUm(Integer produtoId) {

        Produto produto = produtoRepository.findById(produtoId).orElse(null);

        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto nao encontrado");
        }
    }

    public ResponseEntity<Object> atualizar(Integer id, Produto novo) {

        Produto existente = produtoRepository.findById(id).orElse(null);

        if(existente != null) {
            ResponseEntity<Object> response = validaCampos(novo);
            if (!response.getStatusCode().equals(HttpStatus.OK)  ){
                return response;
            }

            existente.setNome(novo.getNome());
            existente.setDescricao(novo.getDescricao());
            existente.setQuantidade_estoque(novo.getQuantidade_estoque());
            existente.setPreco(novo.getPreco());

            return ResponseEntity.ok(produtoRepository.save(existente));
        } else {
            throw new NoSuchElementException("Produto não encontrado");
        }
    }

    public void excluir(Integer id) {

        Produto produtoExistente = produtoRepository.findById(id).orElse(null);

        if (produtoExistente != null) {
            produtoRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Produto nao encontrado");
        }
    }

    public Produto atualizarEstoque(Integer produtoId, int quantidade, String entradaSaida) {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);

        if (produto != null && entradaSaida.equals("retirar")) {
            produto.setQuantidade_estoque(produto.getQuantidade_estoque() - quantidade);

            return produtoRepository.save(produto);
        }
        if (produto != null && entradaSaida.equals("inserir")) {
            produto.setQuantidade_estoque(produto.getQuantidade_estoque() + quantidade);

            return produtoRepository.save(produto);
        }
        return null;
    }
}