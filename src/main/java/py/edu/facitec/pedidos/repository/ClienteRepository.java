package py.edu.facitec.pedidos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import py.edu.facitec.pedidos.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByTelefono(String telefono);
    Optional<Cliente> findByNombre(String nombre);
    Page<Cliente> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    @Query("SELECT c FROM Cliente c " +
            "WHERE (:nombre IS NULL OR c.nombre LIKE %:nombre%) ")//aca realizamos una consulta y filtramos por nombre
    List<Cliente> findByFiltros(String nombre);
}
