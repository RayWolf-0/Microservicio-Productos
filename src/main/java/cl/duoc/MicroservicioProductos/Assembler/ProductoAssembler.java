package cl.duoc.MicroservicioProductos.Assembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import cl.duoc.MicroservicioProductos.controller.ProductoController;
import cl.duoc.MicroservicioProductos.entity.Producto;

@Component
public class ProductoAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>>{

    @Override
    public EntityModel<Producto> toModel(Producto pr){
        return EntityModel.of(
            pr,
            linkTo(methodOn(ProductoController.class).obtenerPorId(pr.getIdItem())).withRel("Lista-el-item-buscado"),
            linkTo(methodOn(ProductoController.class).obtenerProductos()).withRel("Todas-los-Items"),
            linkTo(methodOn(ProductoController.class).eliminarProducto(pr.getIdItem())).withRel("Eliminar-un-item"),
            linkTo(methodOn(ProductoController.class).actualizarProducto(pr.getIdItem(), pr)).withRel("Actualizar-un-Item")
        );
    }
}
