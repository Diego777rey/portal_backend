package py.edu.facitec.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import py.edu.facitec.pedidos.entity.Cliente;
@Data
@AllArgsConstructor
public class AuthPayload {
    private Cliente cliente;   // Puede ser null si falla
    private String token;   // Puede ser null si falla
    private String error;
}
