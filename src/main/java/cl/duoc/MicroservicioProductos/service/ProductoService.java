package cl.duoc.MicroservicioProductos.service;

import java.util.List;

import cl.duoc.MicroservicioProductos.entity.Producto;

public interface ProductoService {
    List<Producto> obtenerProductos();
    Producto obtenerPorId(int id);
    Producto guardarProducto(Producto producto);
    void eliminarProducto(int id);
}
