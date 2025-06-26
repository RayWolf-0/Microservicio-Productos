package cl.duoc.MicroservicioProductos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVENTARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @Column(name = "ID_ITEM")
    private int idItem;

    @Column(name = "CODIGO_PRODUCTO", nullable = false)
    private String codigoProducto;

    @Column(name = "NOMBRE_PRODUCTO", nullable = false)
    private String nombreProducto;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "CATEGORIA")
    private String categoria;

    @Column(name = "CANTIDAD_DISPONIBLE")
    private int cantidadDisponible;

    @Column(name = "PRECIO_UNITARIO")
    private int precioUnitario;

    @Column(name = "PROVEEDOR")
    private String proveedor;
}
