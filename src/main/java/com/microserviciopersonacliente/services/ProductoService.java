package com.microserviciopersonacliente.services;

import com.microserviciopersonacliente.entities.Producto;
import com.microserviciopersonacliente.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }


    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> eliminarProductoPorCodigo(String codigo) {
        List<Producto> productosEliminados = new ArrayList<>();
        List<Producto> productosEncontrados = productoRepository.findByCodigo(codigo);

        if (!productosEncontrados.isEmpty()) {
            productoRepository.deleteAll(productosEncontrados);
            productosEliminados.addAll(productosEncontrados);
        } else {
            throw new EmptyResultDataAccessException("Productos no encontrados con el código proporcionado", 1);
        }

        return productosEliminados;
    }



    public List<Producto> buscarPorNombreYCodigo(String nombre, String codigo) {
        return productoRepository.findByNombreOrCodigo(nombre, codigo);
    }

}