package py.edu.facitec.pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import py.edu.facitec.pedidos.dto.AuthPayload;
import py.edu.facitec.pedidos.dto.LoginInput;
import py.edu.facitec.pedidos.dto.PaginadorDto;
import py.edu.facitec.pedidos.entity.Cliente;
import py.edu.facitec.pedidos.service.ClienteService;
import py.edu.facitec.pedidos.service.JwtService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private JwtService jwtService;

    // --- Consultas ---
    @QueryMapping
    public List<Cliente> findAllClientes() {
        return clienteService.findAllClientes();
    }

    @QueryMapping
    public Cliente findClienteById(@Argument("clienteId") long clienteId) {
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

    // --- Mutaciones CRUD ---
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

    // --- Login por teléfono ---
    @MutationMapping
    public AuthPayload login(@Argument("input") LoginInput input) {
        return clienteService.login(input.getTelefono())
                .map(cliente -> new AuthPayload(cliente, jwtService.generateToken(cliente), null))
                .orElseGet(() -> new AuthPayload(null, null, "Este número no está registrado"));
    }

    // --- Subscriptions ---
    @SubscriptionMapping
    public Flux<Cliente> findAllClientesFlux() {
        return clienteService.findAllClientesFlux();
    }

    @SubscriptionMapping
    public Mono<Cliente> findOneClienteMono(@Argument("id") long id) {
        return clienteService.findOneMono(id);
    }
}
