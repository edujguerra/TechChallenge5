package br.com.fiap.mscliente.repository;

import br.com.fiap.mscliente.model.Cliente;
import br.com.fiap.mscliente.utils.ClienteHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClienteRepositoryTest {

    @Mock
    private ClienteRepository clienteRepository;

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
    void RegistrarCliente(){

        // Arrange
        Cliente cliente = ClienteHelper.gerarCliente();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente clienteArmazenado = clienteRepository.save(cliente);

        // Assert
        verify(clienteRepository, times(1)).save(cliente);
        assertThat(clienteArmazenado)
                .isInstanceOf(Cliente.class)
                .isNotNull()
                .isEqualTo(cliente);
        assertThat(clienteArmazenado)
                .extracting(Cliente::getId)
                .isEqualTo(cliente.getId());
    }


    @Test
    void DeletarCliente(){

        // Arrange
        Integer id = new Random().nextInt();
        doNothing().when(clienteRepository).deleteById(id);
        // Act
        clienteRepository.deleteById(id);
        // Assert
        verify(clienteRepository, times(1)).deleteById(id);
    }

    @Test
    void ListarCliente(){

        // Arrange
        Cliente cliente1 = ClienteHelper.gerarCliente();
        Cliente cliente2 = ClienteHelper.gerarCliente();
        List<Cliente> clienteList = Arrays.asList(cliente1, cliente2);

        when(clienteRepository.findAll()).thenReturn(clienteList);

        // Act
        List<Cliente> resultado = clienteRepository.findAll();

        // Assert
        verify(clienteRepository, times(1)).findAll();
        Assertions.assertThat(resultado)
                .hasSize(2)
                .containsExactlyInAnyOrder(cliente1, cliente2);
    }

    @Test
    void ListarUmCliente(){
        // Arrange
        Cliente cliente = ClienteHelper.gerarCliente();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente clienteArmazenado = clienteRepository.save(cliente);

        // Assert
        verify(clienteRepository, times(1)).save(cliente);
        Assertions.assertThat(clienteArmazenado)
                .isInstanceOf(Cliente.class)
                .isNotNull()
                .isEqualTo(cliente);
        Assertions.assertThat(clienteArmazenado)
                .extracting(Cliente::getId)
                .isEqualTo(cliente.getId());
    }
}
