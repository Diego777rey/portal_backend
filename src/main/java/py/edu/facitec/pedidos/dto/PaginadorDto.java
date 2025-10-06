package py.edu.facitec.pedidos.dto;

import lombok.Data;

import java.util.List;
@Data
public class PaginadorDto<T> {
    private List<T> items;        // Lista de la página actual
    private long totalItems;       // Total de elementos
    private int totalPages;       // Total de páginas
    private int currentPage;      // Página actual (1-indexada)
    private int itemsCount;       // Cantidad de items en la página actual
}
