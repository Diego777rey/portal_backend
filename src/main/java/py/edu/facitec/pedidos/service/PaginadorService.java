package py.edu.facitec.pedidos.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import py.edu.facitec.pedidos.dto.PaginadorDto;

import java.util.function.BiFunction;

@Service
public class PaginadorService {

    /**
     * Paginación genérica sin búsqueda
     */
    public <T> PaginadorDto<T> paginar(PagingAndSortingRepository<T, Integer> repository,
                                       int page, int size) {
        int pageIndex = Math.max(page - 1, 0);
        Pageable pageable = PageRequest.of(pageIndex, size);
        Page<T> resultado = repository.findAll(pageable);
        if (page > resultado.getTotalPages() && resultado.getTotalPages() > 0) {
            page = resultado.getTotalPages();
            pageable = PageRequest.of(page - 1, size);
            resultado = repository.findAll(pageable);
        }

        return construirPaginador(resultado, page);
    }
    public <T> PaginadorDto<T> paginarConFiltro(BiFunction<String, Pageable, Page<T>> searchFunction,
                                                String search, int page, int size) {
        int pageIndex = Math.max(page - 1, 0);
        Pageable pageable = PageRequest.of(pageIndex, size);
        Page<T> resultado;

        if (search != null && !search.trim().isEmpty()) {
            resultado = searchFunction.apply(search, pageable);
        } else {
            resultado = searchFunction.apply("", pageable);
        }

        // Ajuste si la página solicitada excede el total
        if (page > resultado.getTotalPages() && resultado.getTotalPages() > 0) {
            page = resultado.getTotalPages();
            pageable = PageRequest.of(page - 1, size);
            if (search != null && !search.trim().isEmpty()) {
                resultado = searchFunction.apply(search, pageable);
            } else {
                resultado = searchFunction.apply("", pageable);
            }
        }

        return construirPaginador(resultado, page);
    }

    private <T> PaginadorDto<T> construirPaginador(Page<T> pageResult, int page) {
        PaginadorDto<T> dto = new PaginadorDto<>();
        dto.setItems(pageResult.getContent());
        dto.setItemsCount(pageResult.getNumberOfElements());
        dto.setTotalItems((int) pageResult.getTotalElements());
        dto.setTotalPages(pageResult.getTotalPages());
        dto.setCurrentPage(page);
        return dto;
    }
}
