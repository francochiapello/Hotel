package com.hotel.habitacion.service;

import com.hotel.habitacion.domain.Habitaciones;
import com.hotel.habitacion.repository.HabitacionRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HabitacionService {
    private final HabitacionRepository habitacionRepository;

    public HabitacionService(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }
    
    //solo para Gerente
    public List<Habitaciones>buscarTodos(){
        return habitacionRepository.findAll();
    }
    
    //obtener habitaciones segun el estado
    public List<Habitaciones>buscarXestado(String estado){
        return habitacionRepository.findByEstado(estado);
    }
    //obtener habitaciones segun el tipo
    public List<Habitaciones>buscarXtipo(String tipo){
        return habitacionRepository.findByTipo(tipo);
    }
    //crear habitacion (gerente)
    public Habitaciones guardar(Habitaciones habitacion){
        return habitacionRepository.save(habitacion);
    }
    //borrar habitacion (gerente)
    public boolean borrar(int id){
        habitacionRepository.deleteById(id);
        return true;
    }
    //transicion de estado (recepcioniste)
    public boolean transicion(Habitaciones habitacion){
        habitacionRepository.save(habitacion);
        return true;
    }
    //reservar habitacion (cliente y recepcionista)
    public boolean reservar(Habitaciones habitacion){
        habitacionRepository.save(habitacion);
        return true;
    }
    
    
}
