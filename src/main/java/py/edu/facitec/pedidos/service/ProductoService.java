package py.edu.facitec.pedidos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.facitec.pedidos.dto.InputProducto;
import py.edu.facitec.pedidos.dto.PaginadorDto;
import py.edu.facitec.pedidos.entity.Categoria;
import py.edu.facitec.pedidos.entity.Producto;
import py.edu.facitec.pedidos.repository.CategoriaRepository;
import py.edu.facitec.pedidos.repository.ProductoRepository;

import java.util.List;

@Service
@Slf4j
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PaginadorService paginadorService;

    // ================== CRUD ==================

    public List<Producto> findAllProductos() {
        return productoRepository.findAll(); // ya trae categoría gracias a @EntityGraph
    }

    public Producto findOneProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id + " no existe"));
    }

    public Producto saveProducto(InputProducto dto) {
        validarCamposObligatorios(dto);

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id " + dto.getCategoriaId()));

        Producto producto = Producto.builder()
                .nombre(dto.getNombre())
                .precioVenta(dto.getPrecioVenta())
                .imagen(dto.getImagen())
                .activo(dto.getActivo())
                .categoria(categoria)
                .build();

        Producto saved = productoRepository.save(producto);
        log.info("Producto creado: {}", saved.getNombre());
        return saved;
    }

    public Producto updateProducto(Long id, InputProducto dto) {
        validarCamposObligatorios(dto);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id + " no existe"));

        producto.setNombre(dto.getNombre());
        producto.setPrecioVenta(dto.getPrecioVenta());
        producto.setImagen(dto.getImagen());
        producto.setActivo(dto.getActivo());

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id " + dto.getCategoriaId()));
        producto.setCategoria(categoria);

        Producto updated = productoRepository.save(producto);
        log.info("Producto actualizado: {}", updated.getNombre());
        return updated;
    }

    public Producto deleteProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto con id " + id + " no existe"));
        productoRepository.delete(producto);
        log.info("Producto eliminado: {}", producto.getNombre());
        return producto;
    }

    public PaginadorDto<Producto> findProductosPaginated(int page, int size, String search) {
        return paginadorService.paginarConFiltro(
                (s, pageable) -> {
                    if (s == null || s.trim().isEmpty()) {
                        return productoRepository.findAll(pageable);
                    }
                    return productoRepository.findByNombreContainingIgnoreCase(s, pageable);
                },
                search,
                page,
                size
        );
    }
    private void validarCamposObligatorios(InputProducto dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
    }
}