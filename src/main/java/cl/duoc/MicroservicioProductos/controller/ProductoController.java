package cl.duoc.MicroservicioProductos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.MicroservicioProductos.Assembler.ProductoAssembler;
import cl.duoc.MicroservicioProductos.entity.Producto;
import cl.duoc.MicroservicioProductos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/producto")
@Tag(name = "productos", description = "Endpoints para trabajar los productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoAssembler assembler;

    //endpoint para listar los productos
    @GetMapping
    @Operation(summary = "Productos", description = "Operación que lista los Productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se listaron correctamente los Productos",
            content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "No se encontro nada en los Productos",
                content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "no se encuentran Datos")))
    })
    public ResponseEntity<?> obtenerProductos() {
        try {
            List<Producto> productos = productoService.obtenerProductos();
            return ResponseEntity.ok(assembler.toCollectionModel(productos));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener productos: " + e.getMessage());
        }
    }

    //endpoint para buscar un producto
    @GetMapping("/{id}")
    @Operation(summary = "Endpoint que busca un Producto", description = "Operación que busca y lista un Producto")
    @Parameters(value = {
        @Parameter(name = "id_Producto", description = "Id del Producto que se va a buscar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se lista correctamente el Producto",
            content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "404", description = "No se encuentra Producto",
                content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "No se encuentran Productos")))
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        try {
            Producto producto = productoService.obtenerPorId(id);
            return producto != null
                    ? ResponseEntity.ok(assembler.toModel(producto))
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al buscar el producto: " + e.getMessage());
        }
    }

    //endpoint para guardar un producto
    @PostMapping
    @Operation(summary = "Endpoint que registra un Producto", description = "Endpoint que registra un Producto", 
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto Producto que se va a registrar", required = true,
    content = @Content(schema = @Schema(implementation = Producto.class))
    ))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Indica que se registro correctamente el Producto", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "500", description = "Indica que no se logro registrar el Producto",
        content = @Content(schema = @Schema(type = "String", example = "No se puede registar el Producto")))
    })
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.guardarProducto(producto);
            return ResponseEntity.ok(assembler.toModel(nuevoProducto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear el producto: " + e.getMessage());
        }
    }

    //endpoint que edita un producto
    @PutMapping("/{id}")
    @Operation(summary = "Endpoint que edita un Producto", description = "Endpoint que edita un Producto", 
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto Producto que se va a editar", required = true,
    content = @Content(schema = @Schema(implementation = Producto.class))
    ))
    @Parameters(value = {
        @Parameter(name = "id_Producto", description = "Id del Producto que se va a editar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "Indica que se registro correctamente el Producto", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
        @ApiResponse(responseCode = "500", description = "Producto no esta registrado",
        content = @Content(schema = @Schema(type = "String", example = "Producto no esta registrado")))
    })
    public ResponseEntity<?> actualizarProducto(@PathVariable int id, @RequestBody Producto productoActualizado) {
        try {
            Producto productoExistente = productoService.obtenerPorId(id);
            if (productoExistente != null) {
                productoActualizado.setIdItem(id);
                Producto actualizado = productoService.guardarProducto(productoActualizado);
                return ResponseEntity.ok(assembler.toModel(productoActualizado));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el producto: " + e.getMessage());
        }
    }

    //endpoint que elimina un producto
    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint que busca y elimina una Producto", description = "Operación que busca y elimina un Producto")
    @Parameters(value = {
        @Parameter(name = "idProducto", description = "Id del Producto que se va a eliminar", in = ParameterIn.PATH, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se elimina Producto",
        content = @Content(mediaType = "application/json",
        schema = @Schema(type = "string", example = "Se elimina Producto"))),
        @ApiResponse(responseCode = "404", description = "Producto no esta registrado",
                content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "Producto no esta registrado")))
    })
    public ResponseEntity<?> eliminarProducto(@PathVariable int id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
