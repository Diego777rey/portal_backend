package py.edu.facitec.pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.pedidos.enums.EstadoPedido;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputPedido {
    private String fechaPedido;
    private BigDecimal total;
    private EstadoPedido estado;
    private Long clienteId;
    private Long numeroMesa;
    private String observaciones;
    private String horaEstimadaEntrega;
    private Boolean paraLlevar = false;
    private List<InputPedidoItem> items; // clave para mapear los productos
}
