package com.hotel.habitacion.repository;

import com.hotel.habitacion.domain.Personas;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonaRepository extends JpaRepository<Personas, Integer>{
 
    @Query("from Personas p where p.nombre = ?1 and p.pass = ?2")
    Personas logear(String u,String p);
}
