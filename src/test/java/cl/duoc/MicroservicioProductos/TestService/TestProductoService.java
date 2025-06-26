package cl.duoc.MicroservicioProductos.TestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.duoc.MicroservicioProductos.entity.Producto;
import cl.duoc.MicroservicioProductos.repository.ProductoRepository;
import cl.duoc.MicroservicioProductos.service.ProductoServiceImpl;

public class TestProductoService {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoServiceImpl;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarTodo(){

        List<Producto> lista = new ArrayList<>();

        Producto Producto1 = new Producto();
        Producto Producto2 = new Producto();

        Producto1.setIdItem(1);
        Producto1.setCantidadDisponible(2);
        Producto1.setCategoria("reutilizable");
        Producto1.setCodigoProducto("1dos3");
        Producto1.setDescripcion("lavado de manos");
        Producto1.setNombreProducto("jabon");
        Producto1.setPrecioUnitario(1000);
        Producto1.setProveedor("limpieza natural");

        Producto2.setIdItem(2);
        Producto2.setCantidadDisponible(3);
        Producto2.setCategoria("ecofriendly");
        Producto2.setCodigoProducto("4cinco6");
        Producto2.setDescripcion("botella reciclada");
        Producto2.setNombreProducto("Botella reciclada");
        Producto2.setPrecioUnitario(2000);
        Producto2.setProveedor("eco pet");

        lista.add(Producto1);
        lista.add(Producto2);

        when(productoRepository.findAll()).thenReturn(lista);

        List<Producto> resultadoBusqueda = productoServiceImpl.obtenerProductos();

        assertEquals(2, resultadoBusqueda.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarUnProducto(){
        Producto Producto1 = new Producto();
        Producto1.setIdItem(1);
        Producto1.setCantidadDisponible(2);
        Producto1.setCategoria("reutilizable");
        Producto1.setCodigoProducto("1dos3");
        Producto1.setDescripcion("lavado de manos");
        Producto1.setNombreProducto("jabon");
        Producto1.setPrecioUnitario(1000);
        Producto1.setProveedor("limpieza natural");


        when(productoRepository.findById(1)).thenReturn(Optional.of(Producto1));

        Producto Productobuscado = productoServiceImpl.obtenerPorId(1);
        assertEquals(1, Productobuscado.getIdItem());
        verify(productoRepository, times(1)).findById(1);
    }

    @Test
    public void testGuardarProducto(){
        Producto Producto1 = new Producto();
        Producto1.setIdItem(1);
        Producto1.setCantidadDisponible(2);
        Producto1.setCategoria("reutilizable");
        Producto1.setCodigoProducto("1dos3");
        Producto1.setDescripcion("lavado de manos");
        Producto1.setNombreProducto("jabon");
        Producto1.setPrecioUnitario(1000);
        Producto1.setProveedor("limpieza natural");

        when(productoRepository.save(Producto1)).thenReturn(Producto1);

        Producto ProductoGuardado = productoServiceImpl.guardarProducto(Producto1);

        assertEquals(1, ProductoGuardado.getIdItem());
        verify(productoRepository, times(1)).save(Producto1);

    }

    @Test
    public void testeliminarProducto(){
        int Id_Producto = 1;
        doNothing().when(productoRepository).deleteById(Id_Producto);

        productoServiceImpl.eliminarProducto(Id_Producto);

        verify(productoRepository,times(1)).deleteById(Id_Producto);
    }    
}
