package py.edu.facitec.pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.pedidos.dto.PaginadorDto;
import py.edu.facitec.pedidos.entity.Cliente;
import py.edu.facitec.pedidos.service.ClienteService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @QueryMapping
    public List<Cliente> findAllClientes() {
        return clienteService.findAllClientes();
    }

    @QueryMapping
    public Cliente findClienteById(long clienteId) {
        return clienteService.findOneCliente(clienteId);
    }
    @QueryMapping
    public PaginadorDto<Cliente> findClientesPaginated(
            @Argument("page") int page,
            @Argument("size") int size,
            @Argument("search") String search
    ) {
        return clienteService.findClientesPaginated(page, size, search);
    }

    @MutationMapping
    public Cliente saveCliente(@Argument("inputCliente") Cliente inputCliente) {
        return clienteService.saveCliente(inputCliente);
    }

    @MutationMapping
    public Cliente updateCliente(@Argument("id") long id,
                                     @Argument("inputCliente") Cliente inputCliente) {
        return clienteService.updateCliente(id, inputCliente);
    }

    @MutationMapping
    public Cliente deleteCliente(@Argument("id") Long id) {
        return clienteService.deleteCliente(id);
    }
    @SubscriptionMapping
    public Flux<Cliente> findAllClientesFlux() {
        return clienteService.findAllClientesFlux();
    }

    @SubscriptionMapping
    public Mono<Cliente> findOneClienteMono(@Argument("id") long id) {
        return clienteService.findOneMono(id);
    }
}