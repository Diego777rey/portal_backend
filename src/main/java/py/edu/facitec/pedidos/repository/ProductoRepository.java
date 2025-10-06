package py.edu.facitec.pedidos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import py.edu.facitec.pedidos.entity.Producto;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

    @EntityGraph(attributePaths = "categoria")
    Page<Producto> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "categoria")
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    @EntityGraph(attributePaths = "categoria")
    Optional<Producto> findById(Integer id);
}