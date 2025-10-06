package py.edu.facitec.pedidos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "productos")
//@Table(name = "empleado", schema = "funcionario") esta anotacion se usa para mandar a un esquema especifico
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//autoincrement
    private Long id;
    @Column(nullable = false, unique = true)
    private String nombre;
    private Double precioVenta;//esto va a ser solo para cargar y vender comidas
    private byte[] imagen;
    private Boolean activo;
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)//la columna de union de ellos clave for
    private Categoria categoria;
}
