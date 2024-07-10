package br.com.fiap.mscliente.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.mscliente.model.Cliente;
import br.com.fiap.mscliente.utils.ClienteHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClienteRepositoryIT {
    // o Teste de integração é verificado com o banco real, não mockado

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void devePermitirCriarTabela() {
        long totalRegistros = clienteRepository.count();
        assertThat(totalRegistros).isNotNegative();
    }

    @Test
    void RegistrarCliente(){

        // Arrange
        Cliente cliente = ClienteHelper.gerarCliente();

        //act
        Cliente clienteRecebido = clienteRepository.save(cliente);

        //assert
        assertThat(clienteRecebido)
                .isInstanceOf(Cliente.class)
                .isNotNull();
        assertThat(clienteRecebido.getNome()).isEqualTo(cliente.getNome());
    }

    @Test
    void AlterarCliente(){

        // Arrange
        Cliente cliente = ClienteHelper.gerarCliente();
        cliente.setId(200);
        cliente.setNome("testes");

        //act
        Cliente clienteRecebido = clienteRepository.save(cliente);

        //assert
        assertThat(clienteRecebido)
                .isInstanceOf(Cliente.class)
                .isNotNull();
        assertThat(clienteRecebido.getId()).isEqualTo(cliente.getId());
        assertThat(clienteRecebido.getNome()).isEqualTo(cliente.getNome());
    }

    @Test
    void ListarUmCliente(){

        // Arrange
        int id = 200;

        //act
        Optional<Cliente> clienteRecebido = clienteRepository.findById(id);

        //Assert
        assertThat(clienteRecebido)
                .isPresent()
                .isNotNull();
        clienteRecebido.ifPresent(cli -> {
            assertThat(cli.getId()).isEqualTo(id);
        });
    }

    @Test
    void DeletarCliente(){

        // Arrange
        Integer id = 100;

        // Act
        clienteRepository.deleteById(id);
        Optional<Cliente> clienteRecebido = clienteRepository.findById(id);

        // Assert
        assertThat(clienteRecebido).isEmpty();
    }

    @Test
    void ListarCliente(){

        // Arrange
        // ** não precisa

        // Act
        List<Cliente> resultado = clienteRepository.findAll();

        // Assert
        assertThat(resultado).hasSize(3);
    }
}
