package py.edu.facitec.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.pedidos.enums.CategoriaEstado;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputCategoria {
    private String nombre;
    private CategoriaEstado categoriaEstado;
}
