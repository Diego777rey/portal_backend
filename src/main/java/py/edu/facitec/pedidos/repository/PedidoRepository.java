package py.edu.facitec.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.facitec.pedidos.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
