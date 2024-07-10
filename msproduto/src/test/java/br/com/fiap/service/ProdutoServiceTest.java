package br.com.fiap.service;

import br.com.fiap.Helper.ProdutoHelper;
import br.com.fiap.model.Produto;
import br.com.fiap.repository.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProdutoServiceTest {

    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    AutoCloseable autoMock;

    @BeforeEach
    void setup() {
        autoMock = MockitoAnnotations.openMocks(this);
        produtoService = new ProdutoService(produtoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoMock.close();
    }

    @Test
    void testRegistrarProduto() {
        Produto produto = ProdutoHelper.gerarProduto();
        when(produtoRepository.save(ArgumentMatchers.any(Produto.class))).thenAnswer(i -> i.getArgument(0));

        Produto produtoRegistrado = produtoService.salvar(produto);

        assertThat(produtoRegistrado)
                .isInstanceOf(Produto.class)
                .isNotNull()
                .isEqualTo(produto);
    }

    @Test
    void testObterProduto() {
        Produto produto = ProdutoHelper.gerarProduto();
        when(produtoRepository.findById(ArgumentMatchers.any(Integer.class)))
                .thenReturn(Optional.of(produto));

        ResponseEntity<?> result = produtoService.buscarUm(produto.getId());
        Produto produtoObtido = (Produto) result.getBody();

        assertThat(produtoObtido)
                .isInstanceOf(Produto.class)
                .isNotNull()
                .isEqualTo(produto);
    }

    @Test
    void testObterListaProdutos() {
        Produto produto1 = ProdutoHelper.gerarProduto();
        Produto produto2 = ProdutoHelper.gerarProduto2();
        List<Produto> listaProdutos = Arrays.asList(produto1, produto2);

        when(produtoRepository.findAll()).thenReturn(listaProdutos);

        List<Produto> resultado = produtoService.buscarTodos();

        assertThat(resultado)
                .hasSize(2)
                .containsExactlyInAnyOrder(produto1, produto2);
    }

    @Test
    void testExcluirProduto() {
        Produto produto = ProdutoHelper.gerarProduto();
        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));
//        doNothing().when(produtoRepository.deleteById(produto.getId()));
        produtoService.excluir(produto.getId());

        verify(produtoRepository, times(1)).findById(any(Integer.class));
        verify(produtoRepository, times(1)).deleteById(any(Integer.class));
    }

//    @Test
//    void testDeveRetornarExcecao_ProdutoIdInexistente() {
//        Integer id = 5;
//        Produto produto = ProdutoHelper.gerarProduto();
//        produto.setDescricao("Produto de qualidade");
//
//        when(produtoRepository.findById(id)).thenReturn(null);
//
//        assertThatThrownBy(() -> produtoService.atualizarProduto(id, produto))
//                .isInstanceOf(NoSuchElementException.class)
//                .hasMessage("Produto nao encontrado");
//        verify(produtoRepository, never()).save(any(Produto.class));
//    }

    @Test
    void testAtualizarEstoque() {
        Produto produto = ProdutoHelper.gerarProduto();

        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto produtoEstoqueAtualizado = produtoService.atualizarEstoque(
                produto.getId(), 5, "retirar");

        assertEquals(20, produtoEstoqueAtualizado.getQuantidade_estoque());
    }

}
