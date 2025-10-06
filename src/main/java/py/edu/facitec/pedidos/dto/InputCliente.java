package py.edu.facitec.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputCliente {
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String email;
    private String documento;
}
