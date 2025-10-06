package py.edu.facitec.pedidos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.edu.facitec.pedidos.enums.CategoriaEstado;

@Data
@Entity
@Table(name = "categorias")
//@Table(name = "empleado", schema = "funcionario") esta anotacion se usa para mandar a un esquema especifico
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaEstado categoriaEstado;
}
