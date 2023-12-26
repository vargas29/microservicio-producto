package com.microserviciopersonacliente.controllers;

import com.microserviciopersonacliente.dtos.ProductoDTO;
import com.microserviciopersonacliente.entities.Producto;
import com.microserviciopersonacliente.services.ProductoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    // Ruta para la caja de búsqueda
    @ApiOperation(value = "Obtener todos los productos")
    @GetMapping("/")
    public ResponseEntity<List<ProductoDTO>> obtenerTodosLosProductos() {
        try {
            List<Producto> todosLosProductos = productoService.obtenerTodosLosProductos();
            List<ProductoDTO> productosDTO = convertirAProductoDTO(todosLosProductos);
            return ResponseEntity.ok(productosDTO);
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
    @ApiOperation(value = "Guardar un nuevo producto")
    @PostMapping
    public ResponseEntity<String> guardarProducto(@RequestBody ProductoDTO nuevoProducto) {
        Producto producto = new Producto();
        producto.setNombre(nuevoProducto.getNombre());
        producto.setPrecio(nuevoProducto.getPrecio());
        producto.setCodigo(nuevoProducto.getCodigo());
        // Puedes setear otros campos del producto según tu lógica

        // Guardar el producto usando el servicio ProductoService
        Producto productoGuardado = productoService.guardarProducto(producto);

        if (productoGuardado != null) {
            return ResponseEntity.ok("Producto guardado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo guardar el producto");
        }
    }

    @ApiOperation(value = "Eliminar un producto por código")
    @DeleteMapping("/{codigo}")
    public ResponseEntity<List<ProductoDTO>> eliminarProductoPorCodigo(@PathVariable String codigo) {
        try {
            List<Producto> productosEliminados = productoService.eliminarProductoPorCodigo(codigo);
            List<ProductoDTO> productosEliminadosDTO = convertirAProductoDTO(productosEliminados);
            return ResponseEntity.ok(productosEliminadosDTO);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    // Método privado para convertir una lista de entidades Producto a una lista de DTOs ProductoDTO
    private List<ProductoDTO> convertirAProductoDTO(List<Producto> productos) {
        return productos.stream()
                .map(this::convertirAProductoDTO)
                .collect(Collectors.toList());
    }

    // Método privado para convertir una entidad Producto a un DTO ProductoDTO
    private ProductoDTO convertirAProductoDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setCodigo(producto.getCodigo());
        // Puedes agregar más campos al DTO según sea necesario
        return productoDTO;
    }
    @ApiOperation(value = "Buscar productos por nombre o código")
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombreOCodigo(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "codigo", required = false) String codigo) {

        try {
            if (nombre != null || codigo != null) {
                // Ambos parámetros proporcionados, buscar por nombre y código
                List<Producto> productosEncontrados = productoService.buscarPorNombreYCodigo(nombre, codigo);
                List<ProductoDTO> productosEncontradosDTO = convertirAProductoDTO(productosEncontrados);
                return ResponseEntity.ok(productosEncontradosDTO);
            } else {
                // Si no se proporciona ningún parámetro, devolver todos los productos
                List<Producto> todosLosProductos = productoService.obtenerTodosLosProductos();
                List<ProductoDTO> todosLosProductosDTO = convertirAProductoDTO(todosLosProductos);
                return ResponseEntity.ok(todosLosProductosDTO);
            }
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
}
