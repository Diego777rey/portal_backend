package py.edu.facitec.pedidos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import py.edu.facitec.pedidos.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    Optional<Categoria> findByNombre(String descripcion);
    Page<Categoria> findByNombreContainingIgnoreCase(String descripcion, Pageable pageable);
    @Query("SELECT c FROM Categoria c " +
            "WHERE (:nombre IS NULL OR c.nombre LIKE %:nombre%) ")//aca realizamos una consulta y filtramos por nombre
    List<Categoria> findByFiltros(String descripcion);
}
