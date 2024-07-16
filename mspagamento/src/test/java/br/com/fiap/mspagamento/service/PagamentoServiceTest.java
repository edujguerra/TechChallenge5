package br.com.fiap.mspagamento.service;

import br.com.fiap.mspagamento.model.Pagamento;
import br.com.fiap.mspagamento.repository.PagamentoRepository;
import br.com.fiap.mspagamento.utils.PagamentoHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PagamentoServiceTest {

    private PagamentoService pagamentoService;

    @Mock
    private PagamentoRepository pagamentoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {

        openMocks = MockitoAnnotations.openMocks(this);
        pagamentoService= new PagamentoService(pagamentoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void EfetuarPagamento(){

        // Arrange
        Pagamento pagamento = PagamentoHelper.gerarPagamento();
        pagamento.setId(400);
        Mockito.when(pagamentoRepository.save(ArgumentMatchers.any(Pagamento.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Act
        Pagamento pagamentoArmazenado = pagamentoRepository.save(pagamento);

        // Assert
        assertThat(pagamentoArmazenado)
                .isInstanceOf(Pagamento.class)
                .isNotNull()
                .isEqualTo(pagamento);
        assertThat(pagamentoArmazenado.getId())
                .isEqualTo(pagamento.getId());
    }

    @Test
    void ListarUmPagamento(){

        // Arrange
        int id = 100;
        Pagamento pagamento = PagamentoHelper.gerarPagamento();
        pagamento.setId(id);
        Mockito.when(pagamentoRepository.findById(ArgumentMatchers.any(Integer.class)))
                .thenReturn(Optional.of(pagamento));

        // Act
        Pagamento resposta = pagamentoService.obterPagamentoPorId(id);

        // Assert
        Assertions.assertThat(resposta)
                .isInstanceOf(Pagamento.class)
                .isNotNull()
                .isEqualTo(pagamento);
        Assertions.assertThat(resposta)
                .extracting(Pagamento::getId)
                .isEqualTo(pagamento.getId());
    }
    @Test
    void listarPagamentos(){

        // Arrange
        Pagamento pagamento1 = PagamentoHelper.gerarPagamento();
        Pagamento pagamento2 = PagamentoHelper.gerarPagamento();
        List<Pagamento> pagamentoList = Arrays.asList(pagamento1, pagamento2);
        Mockito.when(pagamentoRepository.findAll()).thenReturn(pagamentoList);

        // Act
        List<Pagamento> resultado = pagamentoService.listarPagamentos();

        // Assert
        Assertions.assertThat(resultado)
                .hasSize(2)
                .containsExactlyInAnyOrder(pagamento1, pagamento2);
    }
}
