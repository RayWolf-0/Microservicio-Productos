package cl.duoc.MicroservicioProductos;

import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import cl.duoc.MicroservicioProductos.entity.Producto;
import cl.duoc.MicroservicioProductos.service.ProductoServiceImpl;
import net.datafaker.Faker;

public class DataLoader implements CommandLineRunner{

    private final Faker faker = new Faker(new Locale("es","cl"));
    private final Random random = new Random();

    @Autowired
    private ProductoServiceImpl productosservice;

    @Override
    public void run(String... args) throws Exception{
        for (int i=0; i > 10; i ++){
            Producto nuevoProducto = new Producto();
            nuevoProducto.setIdItem(random.nextInt(100));
            nuevoProducto.setCantidadDisponible(random.nextInt(100));
            nuevoProducto.setCategoria(faker.cannabis().categories());
            nuevoProducto.setCodigoProducto(faker.code().toString());
            nuevoProducto.setDescripcion(random.toString());
            nuevoProducto.setNombreProducto(faker.cannabis().toString());
            nuevoProducto.setPrecioUnitario(random.nextInt(100));
            nuevoProducto.setProveedor(random.toString());

            productosservice.guardarProducto(nuevoProducto);
            System.out.println("Productos Guardada" + nuevoProducto.getIdItem());
        
        }
    }    
}
