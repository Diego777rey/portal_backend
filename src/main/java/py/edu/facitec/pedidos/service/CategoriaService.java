package py.edu.facitec.pedidos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.facitec.pedidos.dto.PaginadorDto;
import py.edu.facitec.pedidos.entity.Categoria;
import py.edu.facitec.pedidos.repository.CategoriaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class CategoriaService {
    @Autowired
    private PaginadorService paginadorService;
    @Autowired
    private CategoriaRepository categoriaRepository;
    public List<Categoria> findAllCategorias() {
        return categoriaRepository.findAll();
    }
    public Categoria findOneCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no existe"));
    }
    public Flux<Categoria> findAllCategoriasFlux() {
        return Flux.fromIterable(findAllCategorias())
                .delayElements(Duration.ofSeconds(1)) // emite uno cada segundo
                .take(10);
    }
    public Mono<Categoria> findOneMono(Long id) {
        return Mono.justOrEmpty(categoriaRepository.findById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Categoria con id " + id + " no existe")));
    }
    public Categoria saveCategoria(Categoria dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Categoria no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .categoriaEstado(dto.getCategoriaEstado())
                .build();

        return categoriaRepository.save(categoria);
    }
    public Categoria updateCategoria(Long id, Categoria dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Categoria no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no existe"));

        categoria.setNombre(dto.getNombre());
               categoria.setCategoriaEstado(dto.getCategoriaEstado());

        return categoriaRepository.save(categoria);
    }
    public Categoria deleteCategoria(Long id) {
        Categoria Categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id " + id + " no existe"));

        categoriaRepository.delete(Categoria);
        return Categoria;
    }
    public PaginadorDto<Categoria> findCategoriasPaginated(int page, int size, String search) {
        return paginadorService.paginarConFiltro(
                (s, pageable) -> {
                    if (s == null || s.trim().isEmpty()) {
                        return categoriaRepository.findAll(pageable);
                    }
                    return categoriaRepository.findByNombreContainingIgnoreCase(s, pageable);
                },
                search,
                page,
                size
        );
    }


    private void validarCamposObligatorios(Categoria dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
    }
}