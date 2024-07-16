package br.com.fiap.mspagamento.repository;

import br.com.fiap.mspagamento.model.Pagamento;
import br.com.fiap.mspagamento.utils.PagamentoHelper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PagamentoRepositoryTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void EfetuarPagamento(){

        // Arrange
        Pagamento pagamento = PagamentoHelper.gerarPagamento();
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        // Act
        Pagamento pagamentoArmazenado = pagamentoRepository.save(pagamento);

        // Assert
        verify(pagamentoRepository, times(1)).save(pagamento);
        assertThat(pagamentoArmazenado)
                .isInstanceOf(Pagamento.class)
                .isNotNull()
                .isEqualTo(pagamento);
        assertThat(pagamentoArmazenado)
                .extracting(Pagamento::getId)
                .isEqualTo(pagamento.getId());
    }


    @Test
    void DeletarPagamento(){

        // Arrange
        Integer id = new Random().nextInt();
        doNothing().when(pagamentoRepository).deleteById(id);
        // Act
        pagamentoRepository.deleteById(id);
        // Assert
        verify(pagamentoRepository, times(1)).deleteById(id);
    }

    @Test
    void ListarPagamentos(){

        // Arrange
        Pagamento cliente1 = PagamentoHelper.gerarPagamento();
        Pagamento cliente2 = PagamentoHelper.gerarPagamento();
        List<Pagamento> clienteList = Arrays.asList(cliente1, cliente2);

        when(pagamentoRepository.findAll()).thenReturn(clienteList);

        // Act
        List<Pagamento> resultado = pagamentoRepository.findAll();

        // Assert
        verify(pagamentoRepository, times(1)).findAll();
        Assertions.assertThat(resultado)
                .hasSize(2)
                .containsExactlyInAnyOrder(cliente1, cliente2);
    }

    @Test
    void ListarUmPagamento(){
        // Arrange
        Pagamento cliente = PagamentoHelper.gerarPagamento();
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(cliente);

        // Act
        Pagamento clienteArmazenado = pagamentoRepository.save(cliente);

        // Assert
        verify(pagamentoRepository, times(1)).save(cliente);
        Assertions.assertThat(clienteArmazenado)
                .isInstanceOf(Pagamento.class)
                .isNotNull()
                .isEqualTo(cliente);
        Assertions.assertThat(clienteArmazenado)
                .extracting(Pagamento::getId)
                .isEqualTo(cliente.getId());
    }
}
