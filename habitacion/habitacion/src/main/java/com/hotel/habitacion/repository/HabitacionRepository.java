package com.hotel.habitacion.repository;

import com.hotel.habitacion.domain.Habitaciones;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HabitacionRepository extends JpaRepository<Habitaciones, Integer>{
    
    //buscar por estado
    @Query("from Habitaciones h where h.estado = ?1")
    List<Habitaciones> findByEstado(String e);
    
    //buscar por tipo
    @Query("from Habitaciones h where h.tipo = ?1")
    List<Habitaciones> findByTipo(String t);

    
}
