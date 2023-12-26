package com.microserviciopersonacliente.repositories;

import com.microserviciopersonacliente.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Puedes agregar métodos personalizados si necesitas consultas específicas
    List<Producto> findByNombre(String nombre);

    List<Producto> findByCodigo(String codigo);

    @Query("SELECT p FROM Producto p WHERE p.codigo = :codigo OR p.nombre = :nombre")
    List<Producto> findByNombreOrCodigo(String nombre, String codigo);
}