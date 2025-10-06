package py.edu.facitec.pedidos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.pedidos.dto.InputPedido;
import py.edu.facitec.pedidos.entity.Pedido;
import py.edu.facitec.pedidos.service.PedidoService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    // --------- Queries ----------
    @QueryMapping
    public Pedido findPedidoById(@Argument Long id) {
        return pedidoService.findPedidoById(id);
    }

    @QueryMapping
    public List<Pedido> findAllPedidos() {
        return pedidoService.findAllPedidos();
    }

    // --------- Mutations ----------
    @MutationMapping
    public Pedido savePedido(@Argument InputPedido inputPedido) {
        return pedidoService.savePedido(inputPedido);
    }
}