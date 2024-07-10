package br.com.fiap.repository;

import br.com.fiap.model.Enum.StatusPedidoEnum;
import br.com.fiap.model.CarrinhoCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrinhoComprasRepository extends JpaRepository<CarrinhoCompras, Integer> {

    @Query("SELECT p FROM CarrinhoCompras p WHERE p.status = :status")
    List<CarrinhoCompras> findByStatus(StatusPedidoEnum status);
}
