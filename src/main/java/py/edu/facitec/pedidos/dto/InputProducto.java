package py.edu.facitec.pedidos.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.pedidos.entity.Categoria;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputProducto {
    private String nombre;
    private Double precioVenta;//esto va a ser solo para cargar y vender comidas
    private byte[] imagen;
    private Boolean activo;
    private Long categoriaId;
}
