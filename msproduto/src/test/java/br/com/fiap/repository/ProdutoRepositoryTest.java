package br.com.fiap.repository;

import br.com.fiap.Helper.ProdutoHelper;
import br.com.fiap.model.Produto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void devePermitirRegistrarProduto() {
        long totalRegistros = produtoRepository.count();
        assertThat(totalRegistros).isNotNegative();
    }

    @Test
    void testCadastrarProduto() {
        Produto produto = ProdutoHelper.gerarProduto();

        Produto produtoRecebido = produtoRepository.save(produto);

        assertThat(produtoRecebido)
                .isInstanceOf(Produto.class)
                .isNotNull();
        assertEquals(produto.getNome(), produtoRecebido.getNome());
        assertEquals(produto.getDescricao(), produtoRecebido.getDescricao());
        assertEquals(produto.getQuantidade_estoque(), produtoRecebido.getQuantidade_estoque());
        assertEquals(produto.getPreco(), produtoRecebido.getPreco());
    }

    @Test
    void testObterProduto() {
        Produto produto = ProdutoHelper.gerarProduto2();

        Produto produtoRecebido = produtoRepository.findById(produto.getId()).orElse(null);

        assertThat(produtoRecebido)
                .isInstanceOf(Produto.class)
                .isNotNull();
    }

    @Test
    void testObterListaProdutos() {
        List<Produto> produtos = produtoRepository.findAll();

        assertTrue(produtos.size() > 1);
    }

    @Test
    void testAtualizarProduto() {

        Produto produto = ProdutoHelper.gerarProduto();
        produto.setPreco(new BigDecimal(17.50));

        Produto produtoRecebido = produtoRepository.save(produto);

        assertThat(produtoRecebido)
                .isInstanceOf(Produto.class)
                .isNotNull();
        assertEquals(produto.getNome(), produtoRecebido.getNome());
        assertEquals(produto.getDescricao(), produtoRecebido.getDescricao());
        assertEquals(produto.getQuantidade_estoque(), produtoRecebido.getQuantidade_estoque());
        assertEquals(produto.getPreco(), produtoRecebido.getPreco());
    }

    @Test
    void testExcluirProduto() {
        Integer id = 1;

        produtoRepository.deleteById(id);
        Optional<Produto> produtoRecebido = produtoRepository.findById(id);

        assertTrue(produtoRecebido.isEmpty());
    }


}
