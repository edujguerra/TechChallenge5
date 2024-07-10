package br.com.fiap.mscliente.utils;

import br.com.fiap.mscliente.model.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ClienteHelper {
    public static Cliente gerarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Eduardo");
        cliente.setUf("RS");
        cliente.setEndereco("rua");
        cliente.setCidade("Caxias");
        cliente.setBairro("bairro");
        cliente.setCpf("10212");
        cliente.setCep("95020-190");
        cliente.setEmail("email");
        cliente.setComplemento("ap 2");

        return cliente;
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
