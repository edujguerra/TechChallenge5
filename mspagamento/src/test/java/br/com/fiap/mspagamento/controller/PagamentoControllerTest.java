package br.com.fiap.mspagamento.controller;

import br.com.fiap.mspagamento.utils.PagamentoHelper;
import br.com.fiap.mspagamento.model.Pagamento;
import br.com.fiap.mspagamento.model.Enum.TipoPagamentoEnum;
import br.com.fiap.mspagamento.repository.PagamentoRepository;
import br.com.fiap.mspagamento.service.PagamentoService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PagamentoService pagamentoService;

    
    @Mock
    private PagamentoRepository pagamentoRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        PagamentoController pagamentoController = new PagamentoController(pagamentoService);

        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController)
                .build();

    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Test
    void ListarPagamentos() throws Exception {

        mockMvc.perform(get(            "/api/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(pagamentoService, times(1))
                .listarPagamentos();
    }

    @Test
    void ListarUmPagamento() throws Exception {
        Integer id = 301;
        Pagamento pagamento = PagamentoHelper.gerarPagamento();
        pagamento.setId(id);

        mockMvc.perform(get("/api/pagamentos/{pagamentoId}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(pagamentoService, times(1)).obterPagamentoPorId(any(Integer.class));
    }
}
