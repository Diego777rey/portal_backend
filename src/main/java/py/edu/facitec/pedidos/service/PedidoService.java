package py.edu.facitec.pedidos.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import py.edu.facitec.pedidos.dto.InputPedido;
import py.edu.facitec.pedidos.entity.Pedido;
import py.edu.facitec.pedidos.entity.PedidoItem;
import py.edu.facitec.pedidos.entity.Producto;
import py.edu.facitec.pedidos.entity.Cliente;
import py.edu.facitec.pedidos.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public Pedido savePedido(InputPedido input) {

        // Validar que el cliente exista y sea obligatorio
        if (input.getClienteId() == null) {
            throw new RuntimeException("El cliente es obligatorio para crear un pedido");
        }
        Cliente cliente = clienteRepository.findById(input.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Parsear fecha y hora desde String
        LocalDate fecha = LocalDate.parse(input.getFechaPedido());
        LocalTime hora = input.getHoraEstimadaEntrega() != null ?
                LocalTime.parse(input.getHoraEstimadaEntrega()) : LocalTime.MIDNIGHT;

        // Combinar en LocalDateTime
        LocalDateTime fechaPedido = LocalDateTime.of(fecha, hora);
        LocalDateTime horaEstimadaEntrega = LocalDateTime.of(fecha, hora);

        // Construir Pedido
        Pedido pedido = Pedido.builder()
                .fechaPedido(fechaPedido)
                .estado(input.getEstado())
                .numeroMesa(input.getNumeroMesa())
                .observaciones(input.getObservaciones())
                .horaEstimadaEntrega(horaEstimadaEntrega)
                .paraLlevar(input.getParaLlevar() != null ? input.getParaLlevar() : false)
                .total(input.getTotal() != null ? input.getTotal() : BigDecimal.ZERO)
                .cliente(cliente) // ahora siempre existe
                .build();

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Construir y guardar items
        List<PedidoItem> items = input.getItems().stream().map(i -> {
            if (i.getProductoId() == null) {
                throw new RuntimeException("Cada item debe tener un productoID válido");
            }
            Producto producto = productoRepository.findById(i.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            BigDecimal subtotal = i.getSubtotal() != null ? i.getSubtotal()
                    : i.getPrecioUnitario().multiply(BigDecimal.valueOf(i.getCantidad()));

            PedidoItem item = PedidoItem.builder()
                    .pedido(pedidoGuardado)
                    .producto(producto)
                    .cantidad(i.getCantidad())
                    .precioUnitario(i.getPrecioUnitario())
                    .subtotal(subtotal)
                    .observaciones(i.getObservaciones())
                    .build();

            return pedidoItemRepository.save(item);
        }).collect(Collectors.toList());

        pedidoGuardado.setItems(items);
        return pedidoGuardado;
    }

    public List<Pedido> findAllPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido findPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }
}
