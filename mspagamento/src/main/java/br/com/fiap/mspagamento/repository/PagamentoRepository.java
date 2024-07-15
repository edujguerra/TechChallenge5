package br.com.fiap.mspagamento.repository;

import br.com.fiap.mspagamento.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    
}
