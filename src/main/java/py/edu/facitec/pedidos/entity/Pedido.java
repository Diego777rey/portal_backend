package py.edu.facitec.pedidos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.pedidos.enums.EstadoPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
//@Table(name = "empleado", schema = "funcionario") esta anotacion se usa para mandar a un esquema especifico
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime fechaPedido;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente; // Opcional, para pedidos de clientes registrados

    // Para pedidos anónimos (clientes que no están registrados)
    private Long numeroMesa;

    private String observaciones;

    // Para controlar el tiempo de preparación
    private LocalDateTime horaEstimadaEntrega;

    @Column(nullable = false)
    private Boolean paraLlevar = false;// FALSE: consumo en local, TRUE: para llevar
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> items;

}

