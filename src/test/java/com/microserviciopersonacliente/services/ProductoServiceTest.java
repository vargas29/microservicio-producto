package com.microserviciopersonacliente.services;

import com.microserviciopersonacliente.entities.Producto;
import com.microserviciopersonacliente.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodosLosProductos_DeberiaRetornarListaVaciaCuandoNoHayProductos() {
        // Arrange
        when(productoRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Producto> productos = productoService.obtenerTodosLosProductos();

        // Assert
        assertTrue(productos.isEmpty());
    }

    @Test
    void guardarProducto_DeberiaGuardarProductoCorrectamente() {
        // Arrange
        Producto producto = new Producto();
        when(productoRepository.save(producto)).thenReturn(producto);

        // Act
        Producto productoGuardado = productoService.guardarProducto(producto);

        // Assert
        assertNotNull(productoGuardado);
        assertEquals(producto, productoGuardado);
    }

    @Test
    void eliminarProductoPorCodigo_DeberiaEliminarProductoCorrectamente() {
        // Arrange
        String codigo = "ABC123";
        List<Producto> productosEncontrados = new ArrayList<>();
        productosEncontrados.add(new Producto());

        when(productoRepository.findByCodigo(codigo)).thenReturn(productosEncontrados);

        // Act
        List<Producto> productosEliminados = productoService.eliminarProductoPorCodigo(codigo);

        // Assert
        assertFalse(productosEliminados.isEmpty());
        verify(productoRepository, times(1)).deleteAll(productosEncontrados);
    }

    @Test
    void eliminarProductoPorCodigo_DeberiaLanzarExcepcionCuandoNoHayProductosParaEliminar() {
        // Arrange
        String codigo = "XYZ789";
        when(productoRepository.findByCodigo(codigo)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(EmptyResultDataAccessException.class, () -> {
            productoService.eliminarProductoPorCodigo(codigo);
        });
    }

    // Aquí puedes añadir pruebas para otros métodos según sea necesario...

}
