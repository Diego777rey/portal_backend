package py.edu.facitec.pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.pedidos.dto.PaginadorDto;
import py.edu.facitec.pedidos.entity.Categoria;
import py.edu.facitec.pedidos.service.CategoriaService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // ==== Queries ====
    @QueryMapping
    public List<Categoria> findAllCategorias() {
        return categoriaService.findAllCategorias();
    }

    @QueryMapping
    public Categoria findCategoriaById(@Argument("categoriaId") long id) {
        return categoriaService.findOneCategoria(id);
    }
    @QueryMapping
    public PaginadorDto<Categoria> findCategoriasPaginated(
            @Argument("page") int page,
            @Argument("size") int size,
            @Argument("search") String search
    ) {
        return categoriaService.findCategoriasPaginated(page, size, search);
    }


    // ==== Mutations (adaptados al schema) ====
    @MutationMapping
    public Categoria saveCategoria(@Argument("inputCategoria") Categoria inputCategoria) {
        return categoriaService.saveCategoria(inputCategoria);
    }

    @MutationMapping
    public Categoria updateCategoria(@Argument("id") long id,
                                     @Argument("inputCategoria") Categoria inputCategoria) {
        return categoriaService.updateCategoria(id, inputCategoria);
    }

    @MutationMapping
    public Categoria deleteCategoria(@Argument("id") Long id) {
        return categoriaService.deleteCategoria(id);
    }

    // ==== Subscriptions ====
    @SubscriptionMapping
    public Flux<Categoria> findAllCategoriasFlux() {
        return categoriaService.findAllCategoriasFlux();
    }

    @SubscriptionMapping
    public Mono<Categoria> findOneCategoriaMono(@Argument("id") long id) {
        return categoriaService.findOneMono(id);
    }
}
