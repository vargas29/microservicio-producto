package com.microserviciopersonacliente.controllers;

import com.microserviciopersonacliente.controllers.ProductoController;
import com.microserviciopersonacliente.dtos.ProductoDTO;
import com.microserviciopersonacliente.entities.Producto;
import com.microserviciopersonacliente.services.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodosLosProductos_ReturnsListOfProductoDTO() {
        // Arrange
        List<Producto> mockProductos = Collections.singletonList(new Producto());
        when(productoService.obtenerTodosLosProductos()).thenReturn(mockProductos);

        // Act
        ResponseEntity<List<ProductoDTO>> response = productoController.obtenerTodosLosProductos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProductos.size(), response.getBody().size());
        // Add more assertions as needed
    }
    @Test
    void guardarProducto_ReturnsOkResponse() {
        // Datos de prueba
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre("Producto de prueba");
        productoDTO.setPrecio(100.0);
        productoDTO.setCodigo("ABC123");

        // Simulación del servicio para guardar el producto
        when(productoService.guardarProducto(any(Producto.class))).thenReturn(new Producto());

        // Llamada al método del controlador
        ResponseEntity<String> response = productoController.guardarProducto(productoDTO);

        // Verificación de la respuesta esperada
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto guardado con éxito", response.getBody());
    }
    @Test
    void eliminarProductoPorCodigo_ReturnsListOfProductoDTO() {
        // Datos de prueba
        String codigo = "ABC123";
        List<Producto> mockProductos = Collections.singletonList(new Producto());

        // Simulación del servicio para eliminar productos por código
        when(productoService.eliminarProductoPorCodigo(codigo)).thenReturn(mockProductos);

        // Llamada al método del controlador
        ResponseEntity<List<ProductoDTO>> response = productoController.eliminarProductoPorCodigo(codigo);

        // Verificación de la respuesta esperada
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProductos.size(), response.getBody().size());
        // Agregar más aserciones según sea necesario
    }

    // Prueba para buscarPorNombreOCodigo
    @Test
    void buscarPorNombreOCodigo_ReturnsListOfProductoDTO() {
        // Datos de prueba
        String nombre = "NombreProducto";
        String codigo = "ABC123";
        List<Producto> mockProductos = Collections.singletonList(new Producto());

        // Simulación del servicio para buscar productos por nombre y código
        when(productoService.buscarPorNombreYCodigo(nombre, codigo)).thenReturn(mockProductos);

        // Llamada al método del controlador
        ResponseEntity<List<ProductoDTO>> response = productoController.buscarPorNombreOCodigo(nombre, codigo);

        // Verificación de la respuesta esperada
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProductos.size(), response.getBody().size());
        // Agregar más aserciones según sea necesario
    }

    // Write similar test methods for other controller methods like guardarProducto, eliminarProductoPorCodigo, etc.
}

