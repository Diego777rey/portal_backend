package py.edu.facitec.pedidos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.edu.facitec.pedidos.dto.PaginadorDto;
import py.edu.facitec.pedidos.entity.Cliente;
import py.edu.facitec.pedidos.repository.ClienteRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private PaginadorService paginadorService;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAllClientes() {
        return clienteRepository.findAll();
    }

    public Cliente findOneCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con id " + id + " no existe"));
    }

    public Flux<Cliente> findAllClientesFlux() {
        return Flux.fromIterable(findAllClientes())
                .delayElements(Duration.ofSeconds(1))
                .take(10);
    }

    public Mono<Cliente> findOneMono(Long id) {
        return Mono.justOrEmpty(clienteRepository.findById(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Cliente con id " + id + " no existe")));
    }

    public Cliente saveCliente(Cliente dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Cliente no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Cliente cliente = Cliente.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .documento(dto.getDocumento())
                .build();

        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(Long id, Cliente dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El objeto Cliente no puede ser nulo");
        }
        validarCamposObligatorios(dto);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con id " + id + " no existe"));

        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());
        cliente.setDocumento(dto.getDocumento());

        return clienteRepository.save(cliente);
    }

    public Cliente deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con id " + id + " no existe"));

        clienteRepository.delete(cliente);
        return cliente;
    }

    public PaginadorDto<Cliente> findClientesPaginated(int page, int size, String search) {
        return paginadorService.paginarConFiltro(
                (s, pageable) -> {
                    if (s == null || s.trim().isEmpty()) {
                        return clienteRepository.findAll(pageable);
                    }
                    return clienteRepository.findByNombreContainingIgnoreCase(s, pageable);
                },
                search,
                page,
                size
        );
    }

    // --- Login solo por teléfono ---
    public Optional<Cliente> login(String telefono) {
        return clienteRepository.findByTelefono(telefono);
    }

    private void validarCamposObligatorios(Cliente dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
    }
}
