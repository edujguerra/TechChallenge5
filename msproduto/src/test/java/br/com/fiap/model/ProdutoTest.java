package br.com.fiap.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoTest {

    @Test
    public void testEmptyConstrutor() {
        Produto produto = new Produto();
        assertNull(produto.getId());
        assertNull(produto.getNome());
        assertNull(produto.getDescricao());
        assertNull(produto.getQuantidade_estoque());
        assertNull(produto.getPreco());
    }

    @Test
    public void testArgsConstrutor() {
        Produto produto = new Produto(1, "Meu produto", "Produto de boa qualidade", 25, new BigDecimal(15.50),null);
        assertNotNull(produto);
    }

    @Test
    public void testGetSetId() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setNome("Produto de Teste");
        produto.setDescricao("Descrição do produto de teste");
        produto.setQuantidade_estoque(10);
        produto.setPreco(new BigDecimal(100.00));

        assertTrue(Integer.valueOf(1).equals(produto.getId()));
        assertTrue("Produto de Teste".equals(produto.getNome()));
        assertTrue("Descrição do produto de teste".equals(produto.getDescricao()));
        assertTrue(Integer.valueOf(10).equals(produto.getQuantidade_estoque()));
        assertEquals(new BigDecimal(100), produto.getPreco());;
    }

}
